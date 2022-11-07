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
import com.openeuler.southbound.model.BoardQueryBean;
import com.openeuler.southbound.model.plan.BoardPlan;

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
 * 板卡计划 UT
 *
 * @since 2020-10-10
 */
@RunWith(SpringRunner.class) // 以Spring启动类的方式运行测试类
@SpringBootTest(classes = SouthBoundApplication.class) // 测试启动类入口
public class BoardPlanServiceImplTest {
    @Autowired
    private BoardPlanServiceImpl service;

    @Test
    public void queryAll() {
        BoardPlan plan = new BoardPlan();
        plan.setPageIndex(1);
        plan.setPageSize(100);
        PageInfo pageInfo = service.queryAll(plan);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    @Transactional
    public void curdTest() {
        BoardPlan plan = new BoardPlan();
        plan.setVersionId("1");
        plan.setChipFactory("chipFactoryStr");
        plan.setRemark("test data");

        // 插入测试
        int add = service.add(plan);
        Asserts.check(add == 1, "insert failed");
        Integer planId = plan.getId();

        // 修改
        plan.setRemark("update test data");
        int update = service.update(plan);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = service.deleteByIds(planId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    public void queryModelTables() {
        BoardQueryBean queryBean = new BoardQueryBean();
        queryBean.setModelList("02311DPA,02312DPA,02313DPA");
        queryBean.setChipFactory("Mellanox");
        queryBean.setVersionName("openEuler 20.03 LTS SP3");
        JSONObject jsonObject = service.queryModelTables(queryBean);
        Asserts.check(jsonObject != null, "data error");
    }

    @Test
    public void queryModelByVersion() {
        JSONObject jsonObject = service.queryModelByVersion("openEuler 20.03 LTS SP3");
        Asserts.check(jsonObject != null, "query error");
    }

    @Test
    public void queryVersionListByChipFactory() {
        List<Map<Integer, String>> mellanox = service.queryVersionListByChipFactory("Mellanox");
        Asserts.check(mellanox != null, "query error");
    }

    @Test
    public void exportAllData() {
        List<BoardPlan> boardPlans = service.exportAllData();
        Asserts.check(boardPlans != null, "export error");
    }
}