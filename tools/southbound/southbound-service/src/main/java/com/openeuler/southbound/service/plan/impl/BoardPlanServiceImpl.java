/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 * southbound-service is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package com.openeuler.southbound.service.plan.impl;

import com.openeuler.southbound.common.utils.StringFormatUtil;
import com.openeuler.southbound.mapper.plan.BoardPlanMapper;
import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.model.BoardQueryBean;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.model.plan.BoardPlan;
import com.openeuler.southbound.model.plan.VersionPlan;
import com.openeuler.southbound.service.plan.BoardPlanService;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 板卡计划service 实现类
 *
 * @since 2022-8-29
 */
@Service
@Slf4j
public class BoardPlanServiceImpl implements BoardPlanService {
    @Autowired
    private BoardPlanMapper boardPlanMapper;

    @Autowired
    private VersionPlanMapper releasePlanMapper;

    @Override
    public PageInfo queryAll(BoardPlan boardPlan) {
        Page page = PageHelper.startPage(boardPlan.getPageIndex(), boardPlan.getPageSize());
        List<BoardPlan> boardPlanList = boardPlanMapper.queryAll(boardPlan);
        handleBoardData(boardPlanList);
        PageInfo pageInfo = new PageInfo(boardPlanList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    private void handleBoardData(List<BoardPlan> boardPlanList) {
        boardPlanList.forEach(item -> {
            // 定义BoardQueryBean
            BoardQueryBean boardQueryBean = new BoardQueryBean();
            boardQueryBean.setChipFactory(item.getChipFactory());
            boardQueryBean.setVersionName(item.getVersionName());
            if (!StringUtils.isEmpty(item.getBetaList())) {
                boardQueryBean.setModelList(item.getBetaList());
                JSONObject bJson = this.queryModelTables(boardQueryBean);
                item.setJsonBetaList(bJson);
            }
            if (!StringUtils.isEmpty(item.getReleaseList())) {
                boardQueryBean.setModelList(item.getReleaseList());
                JSONObject rJson = this.queryModelTables(boardQueryBean);
                item.setJsonReleaseList(rJson);
            }
        });
    }

    @Override
    public int add(BoardPlan boardPlan) {
        return boardPlanMapper.insert(boardPlan);
    }

    @Override
    public int update(BoardPlan boardPlan) {
        return boardPlanMapper.update(boardPlan);
    }

    @Override
    public int deleteByIds(String ids) {
        return boardPlanMapper.deleteByIds(ids);
    }

    @Override
    public JSONObject queryModelTables(BoardQueryBean boardQueryBean) {
        JSONObject object = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        List<String> modelList = StringFormatUtil.str2ListUtil(boardQueryBean.getModelList());
        AtomicInteger fitCount = new AtomicInteger();
        modelList.forEach(item -> {
            boardQueryBean.setModel(item);
            List<BoardFactory> bfList = boardPlanMapper.queryModelTables(boardQueryBean);
            bfList.forEach(bfItem -> {
                JSONObject json = new JSONObject();
                json.put("model", item);
                json.put("chipModel", bfItem.getChipModel());
                json.put("boardType", bfItem.getBoardType());
                List<Integer> betaX86Count = boardPlanMapper.queryModelListCountByX86(
                        boardQueryBean.getChipFactory(), bfItem.getChipModel(), item, boardQueryBean.getVersionName());
                if (betaX86Count.size() > 0) {
                    json.put("X86Status", 1);
                    fitCount.getAndIncrement();
                } else {
                    json.put("X86Status", 0);
                }
                List<Integer> betaArm64Count = boardPlanMapper.queryModelListCountByArm64(
                        boardQueryBean.getChipFactory(), bfItem.getChipModel(), item, boardQueryBean.getVersionName());
                if (betaArm64Count.size() > 0) {
                    json.put("aarch64", 1);
                    fitCount.getAndIncrement();
                } else {
                    json.put("aarch64", 0);
                }
                jsonObjectList.add(json);
            });
            object.put("jsonObjectList", jsonObjectList);
            object.put("fitCount", fitCount);
            object.put("sum", jsonObjectList.size() * 2);
        });
        return object;
    }

    @Override
    public JSONObject queryModelByVersion(String versionName) {
        JSONObject respJson = new JSONObject();
        // 获取计划详情
        VersionPlan releasePlan = releasePlanMapper.queryByReleaseName(versionName);
        if (releasePlan == null) {
            return respJson;
        }
        respJson.put("alphaTables", releasePlan.getAlphaDetail());
        Map<String, List<String>> modelListObject = queryModelList(versionName);
        // 处理Beta阶段数据
        List<String> betaModelList = modelListObject.get("betaModelList");
        String betaEndDate = releasePlan.getBetaEndDate();
        JSONObject betaObject = getModelByVersionData(betaModelList, versionName, betaEndDate);
        respJson.put("betaTables", betaObject.get("stageTableObject"));
        respJson.put("betaTableStatus", betaObject.get("stageNotSupportCount"));
        // 处理Release阶段数据
        List<String> releaseModelList = modelListObject.get("releaseModelList");
        String releaseEndDate = releasePlan.getReleaseEndDate();
        JSONObject releaseObject = getModelByVersionData(releaseModelList, versionName, releaseEndDate);
        respJson.put("releaseTables", releaseObject.get("stageTableObject"));
        respJson.put("releaseTableStatus", releaseObject.get("stageNotSupportCount"));
        return respJson;
    }

    private JSONObject getModelByVersionData(List<String> modelList, String versionName, String endDate) {
        // 当前日期
        Date date = new Date();
        // 时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 定义release阶段返回数据
        List<JSONObject> stageTableObject = new ArrayList<>();
        AtomicInteger stageNotSupportCount = new AtomicInteger();
        try {
            Date releaseEndTime = format.parse(endDate);
            modelList.forEach(item -> {
                List<Integer> releaseX86Count = boardPlanMapper.queryModelListCountByX86(
                        null, null, item, versionName);
                List<Integer> releaseArm64Count = boardPlanMapper.queryModelListCountByArm64(
                        null, null, item, versionName);
                JSONObject json = new JSONObject();
                json.put("model", item);
                json.put("x86Count", releaseX86Count.size());
                json.put("arm64Count", releaseArm64Count.size());
                if (date.getTime() > releaseEndTime.getTime() && (releaseX86Count.size() == 0
                        || releaseArm64Count.size() == 0)) {
                    stageNotSupportCount.getAndIncrement();
                }
                stageTableObject.add(json);
            });
        } catch (ParseException pe) {
            log.error("Date parse Error : ", pe.getMessage());
        }
        JSONObject respJson = new JSONObject();
        respJson.put("stageTableObject", stageTableObject);
        respJson.put("stageNotSupportCount", stageNotSupportCount);
        return respJson;
    }

    /**
     * 查询机型列表
     *
     * @param versionName versionName
     * @return 数组
     */
    Map<String, List<String>> queryModelList(String versionName) {
        Map<String, List<String>> modelMap = new HashMap<>();
        List<String> betaModelList = boardPlanMapper.queryBetaModelList(versionName);
        List<String> betaModels = new ArrayList<>();
        betaModelList.forEach(betaItem -> Collections.addAll(betaModels, betaItem.split(",")));
        List<String> releaseModelList = boardPlanMapper.queryReleaseModelList(versionName);
        List<String> releaseModels = new ArrayList<>();
        releaseModelList.forEach(releaseItem -> Collections.addAll(releaseModels, releaseItem.split(",")));
        modelMap.put("betaModelList", betaModels);
        modelMap.put("releaseModelList", releaseModels);
        return modelMap;
    }

    @Override
    public List<Map<Integer, String>> queryVersionListByChipFactory(String chipFactory) {
        return releasePlanMapper.queryVersionListByChipFactory(chipFactory);
    }

    @Override
    public List<BoardPlan> exportAllData() {
        List<BoardPlan> boardPlanList = boardPlanMapper.exportAllData();
        handleBoardData(boardPlanList);
        return boardPlanList;
    }
}
