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

package com.openeuler.southbound.service;

import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.model.plan.BoardPlan;
import com.openeuler.southbound.model.plan.WholePlan;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 首页service
 *
 * @since 2022-09-01
 */
public interface MainHomeService {
    /**
     * 查询首页卡片数据
     *
     * @return 首页卡片数据
     */
    JSONObject queryCardList();

    /**
     * 查询整机对应机型
     *
     * @return 整机机型
     */
    List<WholeFactory> queryWholeModels();

    /**
     * 查询已适配机型数量
     *
     * @return 已适配机型列表
     */
    Map<String, JSONObject> queryWholeModelSupportCount();

    /**
     * 查询已适配机型列表
     *
     * @return 已适配机型列表
     */
    Map<String, JSONObject> queryWholeModelSupports();

    /**
     * 查询芯片厂商对应板卡型号适配数量
     *
     * @return 已适配机型列表
     */
    Map<String, JSONObject> queryBoardModelSupportCount();

    /**
     * 查询芯片厂商对应板卡型号
     *
     * @return 芯片厂商对应板卡型号
     */
    List<BoardFactory> queryBoardModels();

    /**
     * 查询已适配板卡型号
     *
     * @return 已适配板卡型号
     */
    Map<String, JSONObject> queryBoardModelSupports();

    /**
     * 根据版本返回整机适配情况
     *
     * @param wholePlan 整机计划实体类
     * @return 整机机型
     */
    JSONObject queryVersionWholeDetail(WholePlan wholePlan);

    /**
     * 根据版本返回板卡适配情况
     *
     * @param boardPlan 板卡计划实体类
     * @return 板卡机型
     */
    JSONObject queryVersionBoardDetail(BoardPlan boardPlan);
}
