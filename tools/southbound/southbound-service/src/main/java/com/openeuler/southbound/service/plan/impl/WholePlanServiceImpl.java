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

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.common.utils.StringFormatUtil;
import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.mapper.plan.WholePlanMapper;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.model.plan.VersionPlan;
import com.openeuler.southbound.model.plan.WholePlan;
import com.openeuler.southbound.service.plan.WholePlanService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * 整机计划service 实现类
 *
 * @since 2022-8-29
 */
@Service
@Slf4j
public class WholePlanServiceImpl implements WholePlanService {
    @Autowired
    private WholePlanMapper wholePlanMapper;

    @Autowired
    private VersionPlanMapper releasePlanMapper;

    /**
     * 查询整机计划列表
     *
     * @param wholePlan 整机计划实体类
     * @return ResponseBean
     */
    @Override
    public PageInfo queryAll(WholePlan wholePlan) {
        Page page = PageHelper.startPage(wholePlan.getPageIndex(), wholePlan.getPageSize());
        List<WholePlan> wholePlanList = wholePlanMapper.queryAll(wholePlan);
        handleWholeData(wholePlanList);
        PageInfo pageInfo = new PageInfo(wholePlanList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    private void handleWholeData(List<WholePlan> wholePlanList) {
        wholePlanList.forEach(item -> {
            if (!StringUtils.isEmpty(item.getBetaList())) {
                JSONObject bJson = this.queryModelTables(
                        item.getWholeFactory(), item.getBetaList(), item.getVersionName());
                item.setJsonBetaList(bJson);
            }
            if (!StringUtils.isEmpty(item.getReleaseList())) {
                JSONObject rJson = this.queryModelTables(
                        item.getWholeFactory(), item.getReleaseList(), item.getVersionName());
                item.setJsonReleaseList(rJson);
            }
        });
    }

    /**
     * 添加整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return int
     */
    @Override
    public int add(WholePlan wholePlan) {
        return wholePlanMapper.insert(wholePlan);
    }

    /**
     * 修改整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return int
     */
    @Override
    public int update(WholePlan wholePlan) {
        return wholePlanMapper.update(wholePlan);
    }

    /**
     * 删除整机计划
     *
     * @param ids id数组字符串
     * @return int
     */
    @Override
    public int deleteByIds(String ids) {
        return wholePlanMapper.deleteByIds(ids);
    }

    /**
     * 查询机型适配情况列表
     *
     * @param wholeFactory 整机厂商
     * @param modelList    机型字符串数组
     * @param versionName  版本名称
     * @return 机型适配情况
     */
    @Override
    public JSONObject queryModelTables(String wholeFactory, String modelList, String versionName) {
        JSONObject object = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        List<String> models = StringFormatUtil.str2ListUtil(modelList);
        AtomicInteger fitCount = new AtomicInteger();
        AtomicInteger sum = new AtomicInteger();
        models.forEach(item -> {
            JSONObject json = new JSONObject();
            List<WholeFactory> wfList = wholePlanMapper.queryModelTables(wholeFactory, item, versionName);
            if (wfList.size() == 0) {
                return;
            }
            wfList.forEach(wf -> {
                json.put("model", item);
                json.put("cpuFactory", wf.getCpuFactory());
                json.put("cpuModel", wf.getCpuModel());
                List<Integer> count =
                        wholePlanMapper.queryModelIsSupport(wholeFactory, item, versionName, wf.getCpuModel());
                if (count.size() > 0) {
                    json.put("status", 1);
                    fitCount.getAndIncrement();
                } else {
                    json.put("status", 0);
                }
                sum.getAndIncrement();
                jsonObjectList.add(json);
            });
            object.put("jsonObjectList", jsonObjectList);
            object.put("fitCount", fitCount);
            object.put("sum", sum);
        });
        return object;
    }

    /**
     * 根据版本查询整机计划适配情况
     *
     * @param versionName 版本名称
     * @return 版本整机计划适配情况
     */
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
        // 定义阶段返回数据
        List<JSONObject> stageTableObject = new ArrayList<>();
        AtomicInteger stageNotSupportCount = new AtomicInteger();
        try {
            Date endTime = format.parse(endDate);
            modelList.forEach(releaseModel -> {
                if (!releaseModel.isEmpty()) {
                    List<WholeFactory> wfList = wholePlanMapper.queryModelStatus(releaseModel, versionName);
                    wfList.forEach(wf -> {
                        JSONObject json = new JSONObject();
                        json.put("model", releaseModel);
                        json.put("status", wf.getModelStatus());
                        json.put("wholeFactory", wf.getWholeFactory());
                        if (date.getTime() > endTime.getTime() && wf.getModelStatus().equals("0")) {
                            stageNotSupportCount.getAndIncrement();
                        }
                        stageTableObject.add(json);
                    });
                }
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
        List betaModelList = wholePlanMapper.queryBetaModelList(versionName);
        List releaseModelList = wholePlanMapper.queryReleaseModelList(versionName);
        modelMap.put("betaModelList", betaModelList);
        modelMap.put("releaseModelList", releaseModelList);
        return modelMap;
    }

    /**
     * 根据整机厂商名称查询支持的版本
     *
     * @param wholeFactory 整机厂商
     * @return 版本下整机厂商
     */
    @Override
    public List<Map<Integer, String>> queryVersionListByWholeFactory(String wholeFactory) {
        return releasePlanMapper.queryVersionListByWholeFactory(wholeFactory);
    }

    /**
     * 导出所有整机计划数据
     *
     * @return ResponseBean
     */
    @Override
    public List<WholePlan> exportAllData() {
        List<WholePlan> wholePlanList = wholePlanMapper.exportAllData();
        handleWholeData(wholePlanList);
        return wholePlanList;
    }
}
