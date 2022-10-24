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

package com.openeuler.southbound.service.plan;

import com.openeuler.southbound.model.plan.WholePlan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 整机计划service
 *
 * @since 2022-8-29
 */
@Service
public interface WholePlanService {
    /**
     * 查询所有整机计划
     *
     * @param wholePlan wholePlan
     * @return PageInfo
     */
    PageInfo queryAll(WholePlan wholePlan);

    /**
     * 添加整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return int
     */
    int add(WholePlan wholePlan);

    /**
     * 修改整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return int
     */
    int update(WholePlan wholePlan);

    /**
     * 删除整机计划
     *
     * @param ids id数组字符串
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 查询机型适配情况列表
     *
     * @param wholeFactory 整机厂商
     * @param modelList    机型字符串数组
     * @param versionName  版本名称
     * @return 机型适配情况
     */
    JSONObject queryModelTables(String wholeFactory, String modelList, String versionName);

    /**
     * 根据版本查询整机计划适配情况
     *
     * @param versionName 版本名称
     * @return 版本整机计划适配情况
     */
    JSONObject queryModelByVersion(String versionName);

    /**
     * 根据整机厂商名称查询支持的版本
     *
     * @param wholeFactory 整机厂商
     * @return 版本下整机厂商
     */
    List<Map<Integer, String>> queryVersionListByWholeFactory(String wholeFactory);

    /**
     * 导出所有整机计划数据
     *
     * @return ResponseBean
     */
    List<WholePlan> exportAllData();
}
