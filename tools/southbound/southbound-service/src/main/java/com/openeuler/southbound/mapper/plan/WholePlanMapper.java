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

package com.openeuler.southbound.mapper.plan;

import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.model.plan.WholePlan;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 整机计划-mapper
 *
 * @since 2022-09-01
 */
@Mapper
public interface WholePlanMapper {
    /**
     * 查询所有整机计划
     *
     * @param wholePlan wholePlan
     * @return list
     */
    List<WholePlan> queryAll(WholePlan wholePlan);

    /**
     * 新增整机计划
     *
     * @param wholePlan wholePlan
     * @return int
     */
    int insert(WholePlan wholePlan);

    /**
     * 修改整机计划
     *
     * @param wholePlan wholePlan
     * @return int
     */
    int update(WholePlan wholePlan);

    /**
     * 删除整机计划
     *
     * @param ids ids
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 查询betaListCount
     *
     * @param wholePlan wholePlan
     * @return int
     */
    int queryBetaListCount(WholePlan wholePlan);

    /**
     * 查询releaseListCount
     *
     * @param wholePlan wholePlan
     * @return int
     */
    int queryReleaseListCount(WholePlan wholePlan);

    /**
     * 查询机型适配情况列表
     *
     * @param wholeFactory  wholeFactory
     * @param hardwareModel hardwareModel
     * @param versionName   versionName
     * @return List
     */
    List<WholeFactory> queryModelTables(String wholeFactory, String hardwareModel, String versionName);

    /**
     * 根据版本名查询Beta阶段机型
     *
     * @param versionName versionName
     * @return 机型List
     */
    List<String> queryBetaModelList(String versionName);

    /**
     * 根据版本名查询Release阶段机型
     *
     * @param versionName versionName
     * @return 机型List
     */
    List<String> queryReleaseModelList(String versionName);

    /**
     * 查询状态
     *
     * @param hardwareModel hardwareModel
     * @param versionName   versionName
     * @return List
     */
    List<WholeFactory> queryModelStatus(String hardwareModel, String versionName);

    /**
     * 查询版本
     *
     * @param wholePlan wholePlan
     * @return list
     */
    List<WholePlan> queryVersionModelList(WholePlan wholePlan);

    /**
     * 查询版本是否支持
     *
     * @param wholeFactory  wholeFactory
     * @param hardwareModel hardwareModel
     * @param versionName   versionName
     * @param cpuModel      cpuModel
     * @return List
     */
    List<Integer> queryModelIsSupport(String wholeFactory, String hardwareModel, String versionName, String cpuModel);

    /**
     * 导出所有整机计划数据
     *
     * @return List
     */
    List<WholePlan> exportAllData();
}
