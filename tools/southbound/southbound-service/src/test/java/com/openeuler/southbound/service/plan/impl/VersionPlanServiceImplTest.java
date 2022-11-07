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

import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.plan.VersionPlan;

import java.util.List;
import java.util.Map;
import org.apache.http.util.Asserts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 版本计划 UT
 *
 * @since 2020-10-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
public class VersionPlanServiceImplTest {
    @Autowired
    private VersionPlanServiceImpl versionPlanService;

    @Test
    @Transactional
    public void curdTest() {
        VersionPlan releasePlan = new VersionPlan();
        String versionNameTest = "versionNameTestqweazsd";
        releasePlan.setVersionName(versionNameTest);
        releasePlan.setAlphaStartDate("2021-01-22 11:32:22");
        releasePlan.setAlphaEndDate("2022-02-22 11:32:22");
        releasePlan.setAlphaDetail("alphadetail");
        releasePlan.setBetaStartDate("2021-03-22 11:32:22");
        releasePlan.setBetaEndDate("2021-04-22 11:32:22");
        releasePlan.setReleaseStartDate("2021-05-22 11:32:22");
        releasePlan.setReleaseEndDate("2021-06-22 11:32:22");
        // 插入UT
        int add = versionPlanService.add(releasePlan);
        Asserts.check(add == 1, "insert error");
        // 查询UT
        VersionPlan queryBean = new VersionPlan();
        queryBean.setVersionName(versionNameTest);
        List<VersionPlan> versionPlans = versionPlanService.queryAll(queryBean);
        Asserts.check(versionPlans.size() == 1, "query error");
        // 修改UT
        releasePlan.setVersionName("newVersionNameTest");
        int update = versionPlanService.update(releasePlan);
        Asserts.check(update == 1, "update error");
        // 删除UT
        int versionId = releasePlan.getVersionId();
        int delete = versionPlanService.deleteById(String.valueOf(versionId));
        Asserts.check(delete == 1, "delete error");
    }

    @Test
    public void queryVersionList() {
        List<Map<Integer, String>> maps = versionPlanService.queryVersionList();
        Asserts.check(maps != null, "query error");
    }
}