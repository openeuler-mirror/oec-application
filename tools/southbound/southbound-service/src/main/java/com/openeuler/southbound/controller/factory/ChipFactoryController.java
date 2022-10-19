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
import com.openeuler.southbound.model.factory.ChipFactory;
import com.openeuler.southbound.model.overall.BoardOverall;
import com.openeuler.southbound.service.factory.ChipFactoryService;
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
 * 芯片厂商控制层
 *
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/chip-factory")
@Slf4j
public class ChipFactoryController {
    @Autowired
    private ChipFactoryService chipFactoryService;

    /**
     * 查询
     *
     * @param chipFactory 芯片厂商
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    public ResponseBean queryAll(ChipFactory chipFactory) {
        return ResponseBean.success(chipFactoryService.queryAll(chipFactory));
    }

    /**
     * 新增
     *
     * @param chipFactory 芯片厂商
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseBean add(@RequestBody ChipFactory chipFactory) {
        int count = chipFactoryService.add(chipFactory);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改
     *
     * @param chipFactory 芯片厂商
     * @return 修改结果
     */
    @PutMapping("/update")
    public ResponseBean update(@RequestBody ChipFactory chipFactory) {
        int count = chipFactoryService.update(chipFactory);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 批量删除
     *
     * @param ids id 数组字符串，用‘，’隔开，前后没有中括号
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public ResponseBean deleteByIds(String ids) {
        int count = chipFactoryService.deleteByIds(ids);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询name
     *
     * @return 查询结果
     */
    @GetMapping("/queryNames")
    public ResponseBean queryNameList() {
        return ResponseBean.success(chipFactoryService.queryNameList());
    }

    /**
     * 查询model
     *
     * @return ResponseBean
     */
    @GetMapping("/queryModels")
    public ResponseBean queryModelList() {
        return ResponseBean.success(chipFactoryService.queryModelList());
    }

    /**
     * 查询芯片厂商名称
     *
     * @param chipFactory 芯片厂商
     * @return ResponseBean
     */
    @GetMapping("/queryChipModelName")
    public ResponseBean queryChipModelName(String chipFactory) {
        return ResponseBean.success(chipFactoryService.queryChipModelName(chipFactory));
    }

    /**
     * 查询版本是否支持
     *
     * @param boardOverall boardOverall
     * @return ResponseBean
     */
    @GetMapping("/isSupport")
    public ResponseBean queryIsSupport(BoardOverall boardOverall) {
        return ResponseBean.success(chipFactoryService.queryVersionSupport(boardOverall));
    }

    /**
     * 芯片厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    @PostMapping("/excel/upload")
    public ResponseBean upload(@RequestParam(value = "file") MultipartFile file) {
        return chipFactoryService.uploadChipFactoryExcel(file);
    }

    /**
     * 导出所有芯片厂商数据
     *
     * @return ResponseBean
     */
    @Log(operation = "export chipFactory", detail = "export all chip factory data")
    @GetMapping("/export")
    public ResponseBean exportAllData() {
        return ResponseBean.success(chipFactoryService.exportAllData());
    }
}