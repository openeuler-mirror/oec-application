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

package com.openeuler.southbound.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.openeuler.southbound.common.utils.StringFormatUtil;
import com.openeuler.southbound.mapper.factory.BoardFactoryMapper;
import com.openeuler.southbound.mapper.factory.ChipFactoryMapper;
import com.openeuler.southbound.mapper.factory.CpuFactoryMapper;
import com.openeuler.southbound.mapper.factory.WholeFactoryMapper;
import com.openeuler.southbound.mapper.plan.BoardPlanMapper;
import com.openeuler.southbound.mapper.plan.WholePlanMapper;
import com.openeuler.southbound.model.community.CommunityBoardBean;
import com.openeuler.southbound.model.community.CommunityWholeMachineBean;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.model.plan.BoardPlan;
import com.openeuler.southbound.model.plan.WholePlan;
import com.openeuler.southbound.service.MainHomeService;
import com.openeuler.southbound.service.factory.impl.BoardFactoryServiceImpl;
import com.openeuler.southbound.service.factory.impl.WholeFactoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 首页serviceImp
 *
 * @since 2022-09-01
 */
@Service
public class MainHomeServiceImpl implements MainHomeService {
    @Autowired
    private WholeFactoryMapper wholeFactoryMapper;
    @Autowired
    private BoardFactoryMapper boardFactoryMapper;

    @Autowired
    private WholeFactoryServiceImpl wholeFactoryServiceImpl;
    @Autowired
    private BoardFactoryServiceImpl boardFactoryServiceImpl;

    @Autowired
    private ChipFactoryMapper chipFactoryMapper;
    @Autowired
    private CpuFactoryMapper cpuFactoryMapper;

    @Autowired
    private WholePlanMapper wholePlanMapper;

    @Autowired
    private BoardPlanMapper boardPlanMapper;

    @Override
    public JSONObject queryCardList() {
        JSONObject jsonObject = new JSONObject();
        List<String> wholeFactoryList = wholeFactoryMapper.queryNameList();
        jsonObject.put("wholeFactoryCount", wholeFactoryList.size());
        List<String> chipFactoryList = chipFactoryMapper.queryNameList();
        jsonObject.put("chipFactoryCount", chipFactoryList.size());
        Map<String, List<String>> wholeModelListMap = wholeFactoryServiceImpl.queryModelList(null);
        Set<String> wholeFactoryModelList = new HashSet<>();
        if (wholeModelListMap.size() > 0) {
            wholeFactoryModelList.addAll(wholeModelListMap.get("hardwareModelList"));
            wholeFactoryModelList.addAll(wholeModelListMap.get("extendModelList"));
        }
        int wholeModelCount = wholeFactoryModelList.contains("") ? wholeFactoryModelList.size() - 1
                : wholeFactoryModelList.size();
        jsonObject.put("wholeModelCount", wholeModelCount);
        Map<String, List<String>> boardModelListMap = boardFactoryServiceImpl.queryModelList(null);
        Set<String> boardFactoryModelList = new HashSet<>();
        if (boardModelListMap.size() > 0) {
            boardFactoryModelList.addAll(boardModelListMap.get("typicalModelList"));
            boardFactoryModelList.addAll(boardModelListMap.get("extendBoardModelList"));
        }
        int boardModelCount = boardFactoryModelList.contains("") ? boardFactoryModelList.size() - 1
                : boardFactoryModelList.size();
        jsonObject.put("boardModelCount", boardModelCount);
        List<String> cpuModelList = cpuFactoryMapper.queryModelList();
        jsonObject.put("cpuModelCount", cpuModelList.size());
        List<String> chipModelList = chipFactoryMapper.queryModelList();
        jsonObject.put("chipModelCount", chipModelList.size());
        return jsonObject;
    }

    @Override
    public List<WholeFactory> queryWholeModels() {
        return wholeFactoryMapper.queryWholeModelList();
    }

    @Override
    public Map<String, JSONObject> queryWholeModelSupportCount() {
        Map<String, JSONObject> map = new HashMap<>();
        // 查询整机下所有机型
        List<WholeFactory> wList = wholeFactoryMapper.queryWholeModelList();
        wList.forEach(item -> {
            AtomicInteger modelCount = new AtomicInteger();
            AtomicInteger sumModel = new AtomicInteger();
            List<String> modelList = StringFormatUtil.str2ListUtil(item.getHardwareModel());
            modelList.forEach(model -> {
                if (!StringUtils.isEmpty(model)) {
                    List<CommunityWholeMachineBean> communityWholeMachineBeanList =
                            wholeFactoryMapper.queryModelIsSupport(item.getWholeFactory(), null, model);
                    if (communityWholeMachineBeanList.size() > 0) {
                        modelCount.getAndIncrement();
                    }
                    sumModel.getAndIncrement();
                }
            });
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", modelCount);
            jsonObject.put("sum", sumModel);
            map.put(item.getWholeFactory(), jsonObject);
        });
        return map;
    }

    @Override
    public Map<String, JSONObject> queryWholeModelSupports() {
        Map<String, JSONObject> map = new HashMap<>();
        // 查询整机下所有机型
        List<WholeFactory> wList = wholeFactoryMapper.queryWholeModelList();
        wList.forEach(item -> {
            JSONObject jsonObject = new JSONObject();
            StringFormatUtil.str2ListUtil(item.getHardwareModel()).forEach(model -> {
                if (!StringUtils.isEmpty(model)) {
                    List<CommunityWholeMachineBean> communityWholeMachineBeanList =
                            wholeFactoryMapper.queryModelIsSupport(item.getWholeFactory(), null, model);
                    jsonObject.put(model, communityWholeMachineBeanList);
                }
            });
            map.put(item.getWholeFactory(), jsonObject);
        });
        return map;
    }

    @Override
    public Map<String, JSONObject> queryBoardModelSupportCount() {
        Map<String, JSONObject> map = new HashMap<>();
        // 查询整机下所有机型
        List<BoardFactory> wList = boardFactoryMapper.queryBoardModelList();
        wList.forEach(item -> {
            AtomicInteger sumModel = new AtomicInteger();
            AtomicInteger supportCount = new AtomicInteger();
            List<String> modelList = StringFormatUtil.str2ListUtil(item.getTypicalBoardModel());
            modelList.forEach(model -> {
                if (!StringUtils.isEmpty(model)) {
                    List<CommunityBoardBean> communityBoardBeanList =
                            boardFactoryMapper.queryModelIsSupport(item.getChipFactory(), null, model);
                    if (communityBoardBeanList.size() > 0) {
                        supportCount.getAndIncrement();
                    }
                    sumModel.getAndIncrement();
                }
            });
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", supportCount);
            jsonObject.put("sum", sumModel);
            map.put(item.getChipFactory(), jsonObject);
        });
        return map;
    }

    @Override
    public List<BoardFactory> queryBoardModels() {
        return boardFactoryMapper.queryBoardModelList();
    }

    @Override
    public Map<String, JSONObject> queryBoardModelSupports() {
        Map<String, JSONObject> map = new HashMap<>();
        // 查询整机下所有机型
        List<BoardFactory> bList = boardFactoryMapper.queryBoardModelList();
        bList.forEach(item -> {
            JSONObject jsonObject = new JSONObject();
            StringFormatUtil.str2ListUtil(item.getTypicalBoardModel()).forEach(model -> {
                if (!StringUtils.isEmpty(model)) {
                    List<CommunityBoardBean> communityBoardBeanList =
                            boardFactoryMapper.queryModelIsSupport(item.getChipFactory(), null, model);
                    jsonObject.put(model, communityBoardBeanList);
                }
            });
            map.put(item.getChipFactory(), jsonObject);
        });
        return map;
    }

    /**
     * 根据版本查询整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return 整机计划实体类JSON数据
     */
    @Override
    public JSONObject queryVersionWholeDetail(WholePlan wholePlan) {
        JSONObject resp = new JSONObject();
        List<WholePlan> wholePlanList = wholePlanMapper.queryVersionModelList(wholePlan);
        wholePlanList.forEach(item -> {
            JSONObject jsonItem = new JSONObject();
            List<String> betaList = StringFormatUtil.str2ListUtil(item.getBetaList());
            JSONObject betaListJson = getVersionWholeDetailJSON(item, betaList);

            List<String> releaseList = StringFormatUtil.str2ListUtil(item.getReleaseList());
            JSONObject releaseListJson = getVersionWholeDetailJSON(item, releaseList);

            jsonItem.put("beatListCount", betaListJson.get("listCount"));
            jsonItem.put("beatListSupportCount", betaListJson.get("supportCount"));
            jsonItem.put("releaseListCount", releaseListJson.get("listCount"));
            jsonItem.put("releaseListSupportCount", releaseListJson.get("supportCount"));
            resp.put(item.getVersionName(), jsonItem);
        });
        return resp;
    }


    /**
     * 根据版本查询板卡计划
     *
     * @param boardPlan 板卡计划实体类
     * @return 板卡计划实体类JSON数据
     */
    @Override
    public JSONObject queryVersionBoardDetail(BoardPlan boardPlan) {
        JSONObject resp = new JSONObject();
        List<BoardPlan> boardPlanList = boardPlanMapper.queryVersionModelList(boardPlan);
        boardPlanList.forEach(item -> {
            List<String> betaList = StringFormatUtil.str2ListUtil(item.getBetaList());
            JSONObject betaListJson = getVersionBoardDetailJSON(item, betaList);
            List<String> releaseList = StringFormatUtil.str2ListUtil(item.getReleaseList());
            JSONObject releaseListJson = getVersionBoardDetailJSON(item, releaseList);
            JSONObject jsonItem = new JSONObject();
            jsonItem.put("beatListCount", betaListJson.get("listCount"));
            jsonItem.put("beatListSupportCount", betaListJson.get("supportCount"));
            jsonItem.put("releaseListCount", releaseListJson.get("listCount"));
            jsonItem.put("releaseListSupportCount", releaseListJson.get("supportCount"));
            resp.put(item.getVersionName(), jsonItem);
        });
        return resp;
    }

    /**
     * 获取版本-整机数据JSON
     *
     * @param bean      实体bean
     * @param modelList modelList
     * @return 版本数据JSON
     */
    private JSONObject getVersionWholeDetailJSON(WholePlan bean, List<String> modelList) {
        AtomicInteger wholeSupportCount = new AtomicInteger();
        AtomicInteger wholeListCount = new AtomicInteger();
        modelList.forEach(model -> {
            if (!StringUtils.isEmpty(model)) {
                List<CommunityWholeMachineBean> betaWholeList = wholeFactoryMapper
                        .queryModelIsSupport(null, bean.getVersionName(), model);
                if (betaWholeList.size() > 0) {
                    wholeSupportCount.getAndIncrement();
                }
                wholeListCount.getAndIncrement();
            }
        });
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("listCount", wholeListCount);
        jsonItem.put("supportCount", wholeSupportCount);
        return jsonItem;
    }

    /**
     * 获取版本-板卡数据JSON
     *
     * @param bean      实体bean
     * @param modelList modelList
     * @return 版本数据JSON
     */
    private JSONObject getVersionBoardDetailJSON(BoardPlan bean, List<String> modelList) {
        AtomicInteger boardSupportCount = new AtomicInteger();
        AtomicInteger boardListCount = new AtomicInteger();
        modelList.forEach(model -> {
            if (!StringUtils.isEmpty(model)) {
                List<CommunityBoardBean> boardList = boardFactoryMapper
                        .queryModelIsSupport(null, bean.getVersionName(), model);
                if (boardList.size() > 0) {
                    boardSupportCount.getAndIncrement();
                }
                boardListCount.getAndIncrement();
            }
        });
        JSONObject jsonItem = new JSONObject();
        jsonItem.put("listCount", boardListCount);
        jsonItem.put("supportCount", boardSupportCount);
        return jsonItem;
    }
}
