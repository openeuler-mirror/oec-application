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
import com.openeuler.southbound.model.adaptstatus.BoardAdaptStatusResp;

import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 板卡适配状态
 *
 * @since 2022-08-25
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class BoardAdaptStatusServiceImplTest {
    @Autowired
    BoardAdaptStatusServiceImpl boardAdaptStatusService;

    String versionName = "openEuler 20.03 LTS SP1";

    @Test
    void getStatisticFromChipFactory() {
        List<BoardAdaptStatusResp> statisticFromChipFactory =
                boardAdaptStatusService.getStatisticFromChipFactory(versionName);
        Assert.assertNotNull(statisticFromChipFactory);
    }

    @Test
    void getStatisticFromChipModel() {
        List<BoardAdaptStatusResp> statisticFromChipModel =
                boardAdaptStatusService.getStatisticFromChipModel(versionName);
        Assert.assertNotNull(statisticFromChipModel);
    }

    @Test
    void getStatisticFromBoardType() {
        List<BoardAdaptStatusResp> statisticFromBoardType =
                boardAdaptStatusService.getStatisticFromBoardType(versionName);
        Assert.assertNotNull(statisticFromBoardType);
    }

    @Test
    void getStatisticFromOsVersion() {
        List<BoardAdaptStatusResp> statisticFromOsVersion =
                boardAdaptStatusService.getStatisticFromOsVersion();
        Assert.assertNotNull(statisticFromOsVersion);
    }

    @Test
    void getStatisticFromOsArch() {
        List<BoardAdaptStatusResp> statisticFromOsArch =
                boardAdaptStatusService.getStatisticFromOsArch(versionName);
        Assert.assertNotNull(statisticFromOsArch);
    }
}