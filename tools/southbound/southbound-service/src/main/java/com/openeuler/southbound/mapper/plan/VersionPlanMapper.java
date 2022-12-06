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

import com.openeuler.southbound.model.plan.VersionPlan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作系统版本mapper
 *
 * @since 2022-09-08
 */
@Mapper
public interface VersionPlanMapper {
    /**
     * 查询全部
     *
     * @param releasePlan 发布计划
     * @return 查询结果
     */
    List<VersionPlan> queryAll(VersionPlan releasePlan);

    /**
     * 插入
     *
     * @param releasePlan 发布计划
     * @return 增加结果
     */
    int insert(VersionPlan releasePlan);

    /**
     * 更新
     *
     * @param releasePlan 发布计划
     * @return 修改结果
     */
    int update(VersionPlan releasePlan);

    /**
     * 删除
     *
     * @param versionId 版本ID
     * @return 删除结果
     */
    int deleteById(String versionId);

    /**
     * 查询
     *
     * @return 查询结果
     */
    List<Map<Integer, String>> queryVersionList();

    /**
     * 查询
     *
     * @param releaseName 发布名称
     * @return 查询结果
     */
    VersionPlan queryByReleaseName(String releaseName);

    /**
     * 查询
     *
     * @param versionNames 版本名称
     * @return 查询结果
     */
    HashMap<String, String> queryIdsByVersionName(String versionNames);

    /**
     * 查询
     *
     * @param wholeFactory 整机厂商
     * @return 查询结果
     */
    List<Map<Integer, String>> queryVersionListByWholeFactory(String wholeFactory);

    /**
     * 查询
     *
     * @param chipFactory 芯片厂商
     * @return 查询结果
     */
    List<Map<Integer, String>> queryVersionListByChipFactory(String chipFactory);
}