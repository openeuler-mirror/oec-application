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
import com.openeuler.southbound.model.factory.DriverManage;
import com.openeuler.southbound.service.factory.DriverManageService;

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
 * 驱动管理控制层
 *
 * @since 2022-08-22
 */
@RestController
@RequestMapping("/driver-manage")
@Slf4j
public class DriverManageController {
    @Autowired
    private DriverManageService driverManageService;

    /**
     * 查询
     *
     * @param driverManage 驱动
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    @Log(operation = "QueryAll DriverManage", detail = "QueryAll data of driver-manage.")
    public ResponseBean queryAll(DriverManage driverManage) {
        return ResponseBean.success(driverManageService.queryAll(driverManage));
    }

    /**
     * 新增
     *
     * @param driverManage 驱动
     * @return 新增结果
     */
    @PostMapping("/add")
    @Log(operation = "Add DriverManage", detail = "Add one item of driver-manage.")
    public ResponseBean add(@RequestBody DriverManage driverManage) {
        int count = driverManageService.add(driverManage);
        if (count > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改
     *
     * @param driverManage 驱动
     * @return 修改结果
     */
    @PutMapping("/update")
    @Log(operation = "Update DriverManage", detail = "Update one item of driver-manage.")
    public ResponseBean update(@RequestBody DriverManage driverManage) {
        int count = driverManageService.update(driverManage);
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
    @Log(operation = "Delete DriverManage", detail = "Delete items of driver-manage by id arr.")
    public ResponseBean deleteByIds(String ids) {
        int count = driverManageService.deleteByIds(ids);
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
        return ResponseBean.success(driverManageService.queryChipNameList());
    }

    /**
     * 查询model
     *
     * @param driverList 驱动
     * @return 查询结果
     */
    @GetMapping("/queryModels")
    public ResponseBean queryModelList(String driverList) {
        return ResponseBean.success(driverManageService.queryModelList(driverList));
    }

    /**
     * 查询驱动
     *
     * @param chipFactory 芯片厂商
     * @return ResponseBean
     */
    @GetMapping("/queryDriverName")
    public ResponseBean queryDriverName(String chipFactory) {
        return ResponseBean.success(driverManageService.queryDriverName(chipFactory));
    }

    /**
     * 芯片厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    @PostMapping("/excel/upload")
    @Log(operation = "Import DriverManage", detail = "Upload file and import data to driver-manage.")
    public ResponseBean upload(@RequestParam(value = "file") MultipartFile file) {
        return driverManageService.uploadDriverFactoryExcel(file);
    }

    /**
     * 导出所有驱动数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export DriverManage", detail = "Export all driver-manage data.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(driverManageService.exportAllData());
    }
}

