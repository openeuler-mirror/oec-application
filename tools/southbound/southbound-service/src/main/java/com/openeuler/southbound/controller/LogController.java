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

package com.openeuler.southbound.controller;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.BaseRequestBean;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.service.log.impl.BusinessLogServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 所有用户的业务（操作日志）
 * controller对外接口（API）
 *
 * @since 2022-9-27
 */
@RestController
@RequestMapping("/log")
@Slf4j
public class LogController {
    @Autowired
    private BusinessLogServiceImpl businessLogService;

    /**
     * 获取用户的业务日志（操作日志）
     *
     * @param requestBean 查询参数，关键字和分页
     * @return 系统统一返回类
     */
    @GetMapping("/operate")
    @Log(operation = "QueryAll Operate Log", detail = "QueryAll data of operate-log.")
    public ResponseBean queryOperationLog(BaseRequestBean requestBean) {
        if (requestBean.getPageIndex() == null || requestBean.getPageSize() == null) {
            return ResponseBean.error(MessageContent.MISSING_PARAMETER_EXC + "pageSize or pageIndex");
        }
        return ResponseBean.success(businessLogService.queryAllLog(requestBean));
    }

    /**
     * 导出用户业务日志（操作日志）
     *
     * @param requestBean 筛选条件对象
     * @param response    请求响应对象
     */
    @GetMapping("/excel/export")
    @Log(operation = "Export operate Log", detail = "Export all operate-log data.")
    public void export(BaseRequestBean requestBean, HttpServletResponse response) {
        try {
            SXSSFWorkbook workbook = businessLogService.exportExcel(requestBean);
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            String fileName = "southbound_user_operate_log_" + System.currentTimeMillis() + ".xlsx";
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.addHeader("Access-Control-Allow-Origin", "*");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            workbook.dispose();
        } catch (IOException ex) {
            log.error("Method executed failed: ", ex.getMessage());
        }
    }
}