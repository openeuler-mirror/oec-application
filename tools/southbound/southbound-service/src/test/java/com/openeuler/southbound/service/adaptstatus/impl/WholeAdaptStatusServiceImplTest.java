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

package com.openeuler.southbound.service.adaptstatus.impl;

import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.adaptstatus.WholeAdaptStatusResp;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 整机适配状态 controller UT类
 *
 * @since 2022-08-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class WholeAdaptStatusServiceImplTest {
    @Autowired
    WholeAdaptStatusServiceImpl wholeAdaptStatusService;
    String versionName = "openEuler 22.03 LTS";

    @Test
    void getStatisticFromWholeFactory() {
        List<WholeAdaptStatusResp> statisticFromWholeFactory = wholeAdaptStatusService.getStatisticFromWholeFactory(
                versionName);
        Assert.assertNotNull(statisticFromWholeFactory);
    }

    @Test
    void getStatisticFromCpuFactory() {
        List<WholeAdaptStatusResp> statisticFromWholeFactory =
                wholeAdaptStatusService.getStatisticFromCpuFactory(versionName);
        Assert.assertNotNull(statisticFromWholeFactory);
    }

    @Test
    void getStatisticFromCpuModel() {
        List<WholeAdaptStatusResp> statisticFromWholeFactory =
                wholeAdaptStatusService.getStatisticFromCpuModel(versionName);
        Assert.assertNotNull(statisticFromWholeFactory);
    }

    @Test
    void getStatisticFromOsVersion() {
        List<WholeAdaptStatusResp> statisticFromWholeFactory =
                wholeAdaptStatusService.getStatisticFromOsVersion();
        Assert.assertNotNull(statisticFromWholeFactory);
    }

    @Test
    void getStatisticFromOsArch() {
        List<WholeAdaptStatusResp> statisticFromWholeFactory =
                wholeAdaptStatusService.getStatisticFromOsArch(versionName);
        Assert.assertNotNull(statisticFromWholeFactory);
    }
}