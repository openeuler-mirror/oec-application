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

import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.model.plan.VersionPlan;
import com.openeuler.southbound.service.plan.VersionPlanService;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版本计划service 实现类
 *
 * @since 2022-8-29
 */
@Service
public class VersionPlanServiceImpl implements VersionPlanService {
    @Autowired
    private VersionPlanMapper releaseMapper;

    @Override
    public List<VersionPlan> queryAll(VersionPlan releasePlan) {
        return releaseMapper.queryAll(releasePlan);
    }

    @Override
    public int add(VersionPlan releasePlan) {
        return releaseMapper.insert(releasePlan);
    }

    @Override
    public int update(VersionPlan releasePlan) {
        return releaseMapper.update(releasePlan);
    }

    @Override
    public int deleteById(@Param("versionId") String versionId) {
        return releaseMapper.deleteById(versionId);
    }

    @Override
    public List<Map<Integer, String>> queryVersionList() {
        return releaseMapper.queryVersionList();
    }
}
