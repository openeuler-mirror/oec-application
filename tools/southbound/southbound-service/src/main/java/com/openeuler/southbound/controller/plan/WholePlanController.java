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

package com.openeuler.southbound.controller.plan;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.plan.WholePlan;
import com.openeuler.southbound.service.plan.WholePlanService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 整机计划_API
 *
 * @since 2022-08-25
 */
@RestController
@RequestMapping("/whole-plan")
@Slf4j
public class WholePlanController {
    @Autowired
    private WholePlanService wholePlanService;

    /**
     * 查询整机计划列表
     *
     * @param wholePlan 整机计划实体类
     * @return ResponseBean
     */
    @GetMapping("/queryAll")
    @Log(operation = "Query WholePlan", detail = "Query all data of whole-plan.")
    public ResponseBean queryAll(WholePlan wholePlan) {
        return ResponseBean.success(wholePlanService.queryAll(wholePlan));
    }

    /**
     * 添加整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return ResponseBean
     */
    @PostMapping("/add")
    @Log(operation = "Add WholePlan", detail = "Add data of whole-plan.")
    public ResponseBean add(@RequestBody WholePlan wholePlan) {
        int addCount = wholePlanService.add(wholePlan);
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改整机计划
     *
     * @param wholePlan 整机计划实体类
     * @return ResponseBean
     */
    @PutMapping("/update")
    @Log(operation = "Update WholePlan", detail = "Update data of whole-plan.")
    public ResponseBean update(@RequestBody WholePlan wholePlan) {
        int updateCount = wholePlanService.update(wholePlan);
        if (updateCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 删除整机计划
     *
     * @param ids id数组字符串
     * @return ResponseBean
     */
    @DeleteMapping("/delete")
    @Log(operation = "Delete WholePlan", detail = "Delete data of whole-plan.")
    public ResponseBean batchDeleteByIds(String ids) {
        int deleteCount = wholePlanService.deleteByIds(ids);
        if (deleteCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询机型适配情况列表
     *
     * @param wholeFactory 整机厂商
     * @param modelList    机型字符串数组
     * @param versionName  版本名称
     * @return 机型适配情况
     */
    @GetMapping("/queryModelTables")
    public ResponseBean queryModelTables(String wholeFactory, String modelList, String versionName) {
        return ResponseBean.success(wholePlanService.queryModelTables(wholeFactory, modelList, versionName));
    }

    /**
     * 根据版本查询整机计划适配情况
     *
     * @param versionName 版本名称
     * @return 版本整机计划适配情况
     */
    @GetMapping("/queryVersionModel")
    public ResponseBean queryModelByVersion(String versionName) {
        return ResponseBean.success(wholePlanService.queryModelByVersion(versionName));
    }

    /**
     * 根据整机厂商名称查询支持的版本
     *
     * @param wholeFactory 整机厂商
     * @return 版本下整机厂商
     */
    @GetMapping("/queryVersionListByWholeFactory")
    public ResponseBean queryVersionListByWholeFactory(String wholeFactory) {
        return ResponseBean.success(wholePlanService.queryVersionListByWholeFactory(wholeFactory));
    }

    /**
     * 导出所有整机计划数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export WholePlan", detail = "Export all data of whole-plan.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(wholePlanService.exportAllData());
    }
}
