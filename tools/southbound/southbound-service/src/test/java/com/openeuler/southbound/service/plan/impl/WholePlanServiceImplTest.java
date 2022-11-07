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
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.plan.WholePlan;

import java.util.Date;
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
 * 整机计划 UT
 *
 * @since 2020-10-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
public class WholePlanServiceImplTest {
    @Autowired
    private WholePlanServiceImpl service;

    @Test
    @Transactional
    public void curdTest() {
        WholePlan wholePlan = new WholePlan();
        wholePlan.setPageIndex(1);
        wholePlan.setPageSize(100);
        wholePlan.setWholeFactory("huawei");
        wholePlan.setVersionId("1");
        wholePlan.setBetaList("1,2,3");
        wholePlan.setReleaseList("4,5,6");
        wholePlan.setRemark("test data");
        wholePlan.setCreateTime(new Date());
        // add
        int add = service.add(wholePlan);
        Asserts.check(add == 1, "insert failed");
        // update
        int update = service.update(wholePlan);
        Asserts.check(update == 1, "update failed");
        PageInfo pageInfo = service.queryAll(wholePlan);
        Asserts.check(pageInfo.getList().size() >= 1, "query failed");
        // delete
        int delete = service.deleteByIds(wholePlan.getId() + "");
        Asserts.check(delete == 1, "delete failed");
    }

    @Test
    public void queryModelTables() {
        String wholeFactory = "华为";
        String modelList = "02311DPA,02312DPA,02313DPA";
        String versionName = "openEuler 20.03 LTS SP3";
        JSONObject jsonObject = service.queryModelTables(wholeFactory, modelList, versionName);
        Asserts.check(jsonObject != null, "data error");
    }

    @Test
    public void queryModelByVersion() {
        JSONObject jsonObject = service.queryModelByVersion("openEuler 20.03 LTS SP3");
        Asserts.check(jsonObject != null, "query error");
    }

    @Test
    public void queryVersionListByWholeFactory() {
        List<Map<Integer, String>> huawei = service.queryVersionListByWholeFactory("华为");
        Asserts.check(huawei != null, "query error");
    }

    @Test
    public void exportAllData() {
        List<WholePlan> wholePlans = service.exportAllData();
        Asserts.check(wholePlans != null, "export error");
    }
}