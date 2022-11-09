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

package com.openeuler.southbound.controller.factory;

import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.service.factory.WholeFactoryService;

import java.util.List;
import java.util.Map;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 整机厂商 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class WholeFactoryControllerTest {
    @Autowired
    private WholeFactoryService wholeFactoryService;

    @Test
    void queryAll() {
        WholeFactory wholeFactory = new WholeFactory();
        wholeFactory.setPageIndex(1);
        wholeFactory.setPageSize(100);
        PageInfo pageInfo = wholeFactoryService.queryAll(wholeFactory);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    void add() {
        WholeFactory wholeFactory = new WholeFactory();
        wholeFactory.setVersionId("4");
        wholeFactory.setCpuFactory("CPU厂商1");
        wholeFactory.setCpuModel("aaa");
        wholeFactory.setWholeFactory("bbb");
        wholeFactory.setHardwareModel("bbb");

        // 插入测试
        int add = wholeFactoryService.add(wholeFactory);
        Asserts.check(add == 1, "insert failed");
        Integer planId = wholeFactory.getWholeId();

        // 修改
        wholeFactory.setHardwareModel("bbbb");
        int update = wholeFactoryService.update(wholeFactory);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = wholeFactoryService.deleteByIds(planId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    void queryNameList() {
        List<String> nameList = wholeFactoryService.queryNameList();
        Asserts.check(nameList != null, "query error");
    }

    @Test
    void queryModelList() {
        WholeFactory wholeFactory = new WholeFactory();
        wholeFactory.setCpuFactory("CPU厂商1");
        wholeFactory.setVersionId("1");
        Map<String, List<String>> modelList = wholeFactoryService.queryModelList(wholeFactory);
        Asserts.check(modelList != null, "query error");
    }

    @Test
    void queryCpuFactory() {
        List<String> cpuList = wholeFactoryService.queryCpuFactory(null);
        Asserts.check(cpuList != null, "query error");
    }

    @Test
    void queryCpuModel() {
        List<String> cpuModelList = wholeFactoryService.queryCpuModel(null, null);
        Asserts.check(cpuModelList != null, "query error");
    }

    @Test
    void exportAllData() {
        List<WholeFactory> exportAllData = wholeFactoryService.exportAllData();
        Asserts.check(exportAllData != null, "query error");
    }
}