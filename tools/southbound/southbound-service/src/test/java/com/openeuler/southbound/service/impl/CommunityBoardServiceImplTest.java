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

package com.openeuler.southbound.service.impl;

import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.community.CommunityBoardBean;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 社区兼容性数据包装类-板卡-UT
 *
 * @since 2022-8-26
 */
@RunWith(SpringRunner.class) // 以Spring启动类的方式运行测试类
@SpringBootTest(classes = SouthBoundApplication.class) // 测试启动类入口
class CommunityBoardServiceImplTest {
    @Autowired
    private CommunityBoardDataServiceImpl commBoardService;

    @Test
    void queryAllDataFromDataBase() {
        List<CommunityBoardBean> communityBoardBeans = commBoardService.queryAllDataFromDataBase();
    }

    @Test
    void syncDataFromCommunity() {
        commBoardService.syncDataFromCommunity();
    }

    @Test
    void getDataFromCommunity() {
        List<CommunityBoardBean> driver = CommunityBoardDataServiceImpl.getDriverFromCommunity();
        Assertions.assertNotEquals(driver, new ArrayList<>());
    }
}