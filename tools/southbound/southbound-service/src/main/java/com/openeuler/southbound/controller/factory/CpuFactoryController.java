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
import com.openeuler.southbound.model.factory.CpuFactory;
import com.openeuler.southbound.model.overall.WholeOverall;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.service.factory.CpuFactoryService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * CPU厂商控制层
 *
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/cpu-factory")
@Slf4j
public class CpuFactoryController {
    @Autowired
    private CpuFactoryService cpuFactoryService;

    /**
     * 查询
     *
     * @param cpuFactory CPU厂商
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    @Log(operation = "QueryAll CpuFactory", detail = "QueryAll data of cpu-factory.")
    public ResponseBean queryAll(CpuFactory cpuFactory) {
        return ResponseBean.success(cpuFactoryService.queryAll(cpuFactory));
    }

    /**
     * 新增
     *
     * @param cpuFactory CPU厂商
     * @return 新增结果
     */
    @PostMapping("/add")
    @Log(operation = "Add CpuFactory", detail = "Add one item of cpu-factory.")
    public ResponseBean add(@RequestBody CpuFactory cpuFactory) {
        int addCount = cpuFactoryService.add(cpuFactory);
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改
     *
     * @param cpuFactory CPU厂商
     * @return 修改结果
     */
    @PutMapping("/update")
    @Log(operation = "Update CpuFactory", detail = "Update one item of cpu-factory.")
    public ResponseBean update(@RequestBody CpuFactory cpuFactory) {
        if (StringUtils.isEmpty(cpuFactory.getVersionIds())) {
            return ResponseBean.error("versionIds not be null");
        }
        int updateCount = cpuFactoryService.update(cpuFactory);
        if (updateCount > 0) {
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
    @Log(operation = "Delete CpuFactory", detail = "Delete items of cpu-factory by id arr.")
    public ResponseBean deleteByIds(String ids) {
        int deleteCount = cpuFactoryService.deleteByIds(ids);
        if (deleteCount > 0) {
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
        return ResponseBean.success(cpuFactoryService.queryNameList());
    }

    /**
     * 查询model
     *
     * @return 查询结果
     */
    @GetMapping("/queryModels")
    public ResponseBean queryModelList() {
        return ResponseBean.success(cpuFactoryService.queryModelList());
    }

    /**
     * 在architecture查询model
     *
     * @return 查询结果
     */
    @GetMapping("/queryArchitectures")
    public ResponseBean queryArchitectureList() {
        return ResponseBean.success(cpuFactoryService.queryArchitectureList());
    }

    /**
     * 根据CPU厂商查询CPU型号
     *
     * @param cpuFactory CPU厂商
     * @return ResponseBean
     */
    @GetMapping("/queryCpuModelNames")
    public ResponseBean queryCpuModelNames(String cpuFactory) {
        return ResponseBean.success(cpuFactoryService.queryCpuModelNames(cpuFactory));
    }

    /**
     * 查看CPU代次版本是否支持
     *
     * @param wholeOverall 全景
     * @return ResponseBean
     */
    @GetMapping("/isSupport")
    public ResponseBean isSupport(WholeOverall wholeOverall) {
        return ResponseBean.success(cpuFactoryService.isSupport(wholeOverall));
    }

    /**
     * CPU厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    @PostMapping("/excel/upload")
    @Log(operation = "Import CpuFactory", detail = "Upload file and import data to cpu-factory.")
    public ResponseBean upload(@RequestParam(value = "file") MultipartFile file) {
        return cpuFactoryService.uploadCpuFactoryExcel(file);
    }

    /**
     * 导出所有CPU厂商数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export CpuFactory", detail = "Export all cpu factory data.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(cpuFactoryService.exportAllData());
    }
}


