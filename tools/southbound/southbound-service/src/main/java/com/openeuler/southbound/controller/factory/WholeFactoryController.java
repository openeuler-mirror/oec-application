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
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.service.factory.WholeFactoryService;

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
 * 整机厂商控制层
 *
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/whole-factory")
@Slf4j
public class WholeFactoryController {
    @Autowired
    private WholeFactoryService wholeFactoryService;

    /**
     * 查询
     *
     * @param wholeFactory 整机厂商
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    public ResponseBean queryAll(WholeFactory wholeFactory) {
        return ResponseBean.success(wholeFactoryService.queryAll(wholeFactory));
    }

    /**
     * 新增
     *
     * @param wholeFactory 整机厂商
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseBean add(@RequestBody WholeFactory wholeFactory) {
        int count = wholeFactoryService.add(wholeFactory);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改
     *
     * @param wholeFactory 整机厂商
     * @return 修改结果
     */
    @PutMapping("/update")
    public ResponseBean update(@RequestBody WholeFactory wholeFactory) {
        int count = wholeFactoryService.update(wholeFactory);
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
    public ResponseBean batchDeleteByIds(String ids) {
        int count = wholeFactoryService.deleteByIds(ids);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询名字
     *
     * @return 查询结果
     */
    @GetMapping("/queryNames")
    public ResponseBean queryNameList() {
        return ResponseBean.success(wholeFactoryService.queryNameList());
    }

    /**
     * 查询整机型号
     *
     * @param wholeFactory 整机厂商对象
     * @return ResponseBean
     */
    @GetMapping("/queryModels")
    public ResponseBean queryModelList(WholeFactory wholeFactory) {
        return ResponseBean.success(wholeFactoryService.queryModelList(wholeFactory));
    }

    /**
     * 新增整机厂商时获取筛选列表
     *
     * @param wholeFactory 整机厂商
     * @return ResponseBean
     */
    @GetMapping("/queryCpuFactory")
    public ResponseBean queryCpuFactory(String wholeFactory) {
        return ResponseBean.success(wholeFactoryService.queryCpuFactory(wholeFactory));
    }

    /**
     * 新增整机厂商时获取筛选列表
     *
     * @param wholeFactory 整机厂商
     * @param cpuFactory   CPU厂商
     * @return ResponseBean
     */
    @GetMapping("/queryCpuModel")
    public ResponseBean queryCpuModel(String wholeFactory, String cpuFactory) {
        return ResponseBean.success(wholeFactoryService.queryCpuModel(wholeFactory, cpuFactory));
    }

    /**
     * 芯片厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    @PostMapping("/excel/upload")
    public ResponseBean upload(@RequestParam(value = "file") MultipartFile file) {
        return wholeFactoryService.uploadWholeFactoryExcel(file);
    }

    /**
     * 导出所有整机厂商数据
     *
     * @return ResponseBean
     */
    @Log(operation = "export wholeFactory", detail = "export all whole factory data")
    @GetMapping("/export")
    public ResponseBean exportAllData() {
        return ResponseBean.success(wholeFactoryService.exportAllData());
    }
}