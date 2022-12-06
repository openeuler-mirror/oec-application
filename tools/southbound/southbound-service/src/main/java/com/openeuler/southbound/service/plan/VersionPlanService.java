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

import com.openeuler.southbound.model.plan.VersionPlan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 版本计划service
 *
 * @since 2022-8-29
 */
@Service
public interface VersionPlanService {
    /**
     * 查询搜友版本计划
     *
     * @param releasePlan releasePlan
     * @return list
     */
    List<VersionPlan> queryAll(VersionPlan releasePlan);

    /**
     * 新增版本计划
     *
     * @param releasePlan releasePlan
     * @return int
     */
    int add(VersionPlan releasePlan);

    /**
     * 修改版本计划
     *
     * @param releasePlan releasePlan
     * @return int
     */
    int update(VersionPlan releasePlan);

    /**
     * 删除版本计划
     *
     * @param versionId versionId
     * @return int
     */
    int deleteById(String versionId);

    /**
     * 查询版本计划列表
     *
     * @return list
     */
    List<Map<Integer, String>> queryVersionList();
}