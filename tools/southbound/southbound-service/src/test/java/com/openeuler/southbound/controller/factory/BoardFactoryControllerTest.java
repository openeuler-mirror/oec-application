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
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.service.factory.BoardFactoryService;

import java.util.List;
import java.util.Map;

import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 板卡厂商 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class BoardFactoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BoardFactoryService boardFactoryService;

    @Test
    void queryAll() {
        BoardFactory boardFactory = new BoardFactory();
        boardFactory.setPageIndex(1);
        boardFactory.setPageSize(100);
        PageInfo pageInfo = boardFactoryService.queryAll(boardFactory);
        Asserts.check(pageInfo != null, "result is null");
    }

    @Test
    void add() {
        BoardFactory boardFactory = new BoardFactory();
        boardFactory.setArmPriority("1");
        boardFactory.setBoardType("bbb");
        boardFactory.setChipFactory("芯片1");
        boardFactory.setChipModel("aaa");
        boardFactory.setDemandSource("项目需求");
        boardFactory.setTypicalBoardModel("aaa");
        boardFactory.setX86Priority("1");

        // 插入测试
        int add = boardFactoryService.add(boardFactory);
        Asserts.check(add == 1, "insert failed");
        Integer boardId = boardFactory.getBoardId();

        // 修改
        boardFactory.setTypicalBoardModel("bbbb");
        int update = boardFactoryService.update(boardFactory);
        Asserts.check(update == 1, "update failed");

        // 删除
        int deleteCountRow = boardFactoryService.deleteByIds(boardId + "");
        Asserts.check(deleteCountRow == 1, "delete failed");
    }

    @Test
    void queryModelList() {
        BoardFactory boardFactory = new BoardFactory();
        boardFactory.setChipFactory("芯片1");
        boardFactory.setVersionId("1");
        Map<String, List<String>> stringListMap = boardFactoryService.queryModelList(boardFactory);
        Asserts.check(stringListMap != null, "query error");
    }

    @Test
    void queryChipFactory() {
        List<String> chipList = boardFactoryService.queryChipFactory();
        Asserts.check(chipList != null, "query error");
    }

    @Test
    void queryChipModel() {
        List<String> stringList = boardFactoryService.queryChipModel(null, null);
        Asserts.check(stringList != null, "query error");
    }

    @Test
    void queryBoardType() {
        List<String> boardType = boardFactoryService.queryBoardType(null);
        Asserts.check(boardType != null, "query error");
    }

    @Test
    void exportAllData() {
        List<BoardFactory> dataList = boardFactoryService.exportAllData();
        Asserts.check(dataList != null, "query error");
    }
}