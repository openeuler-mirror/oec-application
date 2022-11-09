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
import com.openeuler.southbound.model.factory.ChipFactory;
import com.openeuler.southbound.service.factory.ChipFactoryService;

import java.util.List;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 芯片厂商 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class ChipFactoryControllerTest {
    @Autowired
    private ChipFactoryService chipFactoryService;

    @Test
    void queryAll() {
        ChipFactory chipFactory = new ChipFactory();
        chipFactory.setPageIndex(1);
        chipFactory.setPageSize(100);
        PageInfo pageInfo = chipFactoryService.queryAll(chipFactory);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    void add() {
        ChipFactory chipFactory = new ChipFactory();
        chipFactory.setChipFactory("芯片1");
        chipFactory.setChipModel("bbb");
        chipFactory.setVersionIds("4");

        // 插入测试
        int add = chipFactoryService.add(chipFactory);
        Asserts.check(add == 1, "insert failed");
        Integer chipId = chipFactory.getChipId();

        // 修改
        chipFactory.setChipModel("aaaa");
        int update = chipFactoryService.update(chipFactory);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = chipFactoryService.deleteByIds(chipId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    void queryNameList() {
        List<String> nameList = chipFactoryService.queryNameList();
        Asserts.check(nameList != null, "query error");
    }

    @Test
    void queryModelList() {
        List<String> modelList = chipFactoryService.queryModelList();
        Asserts.check(modelList != null, "query error");
    }

    @Test
    void queryChipModelName() {
        List<String> modelList = chipFactoryService.queryChipModelName(null);
        Asserts.check(modelList != null, "query error");
    }

    @Test
    void queryIsSupport() {
        boolean isSupport = chipFactoryService.queryVersionSupport(null);
        Asserts.check(isSupport, "query error");
    }

    @Test
    void exportAllData() {
        List<ChipFactory> allDateList = chipFactoryService.exportAllData();
        Asserts.check(allDateList != null, "query error");
    }
}