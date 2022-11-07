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
import com.openeuler.southbound.model.factory.CpuFactory;
import com.openeuler.southbound.service.factory.CpuFactoryService;

import java.util.List;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * CPU厂商 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class CpuFactoryControllerTest {
    @Autowired
    private CpuFactoryService cpuFactoryService;

    @Test
    void queryAll() {
        CpuFactory cpuFactory = new CpuFactory();
        cpuFactory.setPageIndex(1);
        cpuFactory.setPageSize(100);
        PageInfo pageInfo = cpuFactoryService.queryAll(cpuFactory);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    void add() {
        CpuFactory cpuFactory = new CpuFactory();
        cpuFactory.setCpuFactory("CPU厂商1");
        cpuFactory.setCpuModel("bbb");
        cpuFactory.setArchitecture("X86");
        cpuFactory.setReleaseTime("2022-11-01");
        cpuFactory.setVersionIds("4");

        // 插入测试
        int add = cpuFactoryService.add(cpuFactory);
        Asserts.check(add == 1, "insert failed");
        Integer cpuId = cpuFactory.getCpuId();

        // 修改
        cpuFactory.setCpuModel("bbbb");
        int update = cpuFactoryService.update(cpuFactory);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = cpuFactoryService.deleteByIds(cpuId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    void queryNameList() {
        List<String> nameList = cpuFactoryService.queryNameList();
        Asserts.check(nameList != null, "query error");
    }

    @Test
    void queryModelList() {
        List<String> modelList = cpuFactoryService.queryModelList();
        Asserts.check(modelList != null, "query error");
    }

    @Test
    void queryArchitectureList() {
        List<String> architectureList = cpuFactoryService.queryArchitectureList();
        Asserts.check(architectureList != null, "query error");
    }

    @Test
    void queryCpuModelNames() {
        List<String> architectureList = cpuFactoryService.queryCpuModelNames(null);
        Asserts.check(architectureList != null, "query error");
    }


    @Test
    void exportAllData() {
        List<CpuFactory> exportAllData = cpuFactoryService.exportAllData();
        Asserts.check(exportAllData != null, "query error");
    }
}