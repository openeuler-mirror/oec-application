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
import com.openeuler.southbound.model.factory.DriverManage;
import com.openeuler.southbound.service.factory.DriverManageService;

import java.util.List;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 驱动管理 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class DriverManageControllerTest {
    @Autowired
    private DriverManageService driverManageService;

    @Test
    void queryAll() {
        DriverManage driverManage = new DriverManage();
        driverManage.setPageIndex(1);
        driverManage.setPageSize(100);
        PageInfo pageInfo = driverManageService.queryAll(driverManage);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    void add() {
        DriverManage driverManage = new DriverManage();
        driverManage.setChipFactory("芯片1");
        driverManage.setDriverName("bbb");
        driverManage.setExteriorDriverPublish("未发布");
        driverManage.setKernelDriverPublish("未发布");
        driverManage.setVersionId("4");

        // 插入测试
        int add = driverManageService.add(driverManage);
        Asserts.check(add == 1, "insert failed");
        Integer driverId = driverManage.getDriverId();

        // 修改
        driverManage.setDriverName("bbbb");
        int update = driverManageService.update(driverManage);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = driverManageService.deleteByIds(driverId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    void queryNameList() {
        List<String> nameList = driverManageService.queryDriverName(null);
        Asserts.check(nameList != null, "query error");
    }

    @Test
    void queryModelList() {
        List<String> modelList = driverManageService.queryModelList(null);
        Asserts.check(modelList != null, "query error");
    }

    @Test
    void queryDriverName() {
        List<String> driverList = driverManageService.queryDriverName(null);
        Asserts.check(driverList != null, "query error");
    }

    @Test
    void exportAllData() {
        List<DriverManage> dataList = driverManageService.exportAllData();
        Asserts.check(dataList != null, "query error");
    }
}