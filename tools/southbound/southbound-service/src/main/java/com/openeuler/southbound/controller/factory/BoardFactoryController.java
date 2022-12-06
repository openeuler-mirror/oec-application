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

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.service.factory.BoardFactoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 板卡厂商控制层
 *
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/board-factory")
@Slf4j
public class BoardFactoryController {
    @Autowired
    private BoardFactoryService boardFactoryService;

    /**
     * 查询
     *
     * @param boardFactory 板卡厂商
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    @Log(operation = "QueryAll BoardFactory", detail = "QueryAll data of board-factory.")
    public ResponseBean queryAll(BoardFactory boardFactory) {
        return ResponseBean.success(boardFactoryService.queryAll(boardFactory));
    }

    /**
     * 新增
     *
     * @param boardFactory 板卡厂商
     * @return 新增结果
     */
    @PostMapping("/add")
    @Log(operation = "Add BoardFactory", detail = "Add one item of board-factory.")
    public ResponseBean add(@RequestBody BoardFactory boardFactory) {
        int count = boardFactoryService.add(boardFactory);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改
     *
     * @param boardFactory 板卡厂商
     * @return 修改结果
     */
    @PutMapping("/update")
    @Log(operation = "Update BoardFactory", detail = "Update one item of board-factory.")
    public ResponseBean update(@RequestBody BoardFactory boardFactory) {
        int count = boardFactoryService.update(boardFactory);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 批量删除
     *
     * @param ids id 数组字符串，用‘,’隔开，前后没有中括号
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Log(operation = "Delete BoardFactory", detail = "Delete items of board-factory by id arr.")
    public ResponseBean deleteByIds(String ids) {
        int count = boardFactoryService.deleteByIds(ids);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询典型和扩展model
     *
     * @param boardFactory 板卡厂商
     * @return 查询结果
     */
    @GetMapping("/queryModels")
    @Log(operation = "Query In BoardFactory", detail = "Query typical and extend models of board-factory.")
    public ResponseBean queryModelList(BoardFactory boardFactory) {
        return ResponseBean.success(boardFactoryService.queryModelList(boardFactory));
    }

    /**
     * 查询板卡厂商表中芯片厂商列表
     *
     * @return ResponseBean
     */
    @GetMapping("/queryChipFactory")
    @Log(operation = "Query In BoardFactory", detail = "Query chip factory of board-factory.")
    public ResponseBean queryChipFactory() {
        return ResponseBean.success(boardFactoryService.queryChipFactory());
    }

    /**
     * 根绝芯片厂商、板卡类型查询芯片型号
     *
     * @param chipFactory 芯片厂商
     * @param boardType   板卡类型
     * @return ResponseBean
     */
    @GetMapping("/queryChipModel")
    @Log(operation = "Query In BoardFactory", detail = "Query chip model of board-factory.")
    public ResponseBean queryChipModel(String chipFactory, String boardType) {
        return ResponseBean.success(boardFactoryService.queryChipModel(chipFactory, boardType));
    }

    /**
     * 查询板卡类型列表
     *
     * @param boardFactory 板卡厂商
     * @return ResponseBean
     */
    @GetMapping("/queryBoardType")
    @Log(operation = "Query In BoardFactory", detail = "Query board type of board-factory.")
    public ResponseBean queryBoardType(BoardFactory boardFactory) {
        return ResponseBean.success(boardFactoryService.queryBoardType(boardFactory));
    }

    /**
     * 板卡厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    @PostMapping("/excel/upload")
    @Log(operation = "Import BoardFactory", detail = "Upload file and import data to board-factory.")
    public ResponseBean upload(@RequestParam(value = "file") MultipartFile file) {
        return boardFactoryService.uploadBoardFactoryExcel(file);
    }

    /**
     * 导出所有板卡厂商数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export BoardFactory", detail = "Export all board-factory data.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(boardFactoryService.exportAllData());
    }
}
