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

import com.openeuler.southbound.common.enums.ExcelTemplateEnum;
import com.openeuler.southbound.common.utils.I18NServer;
import com.openeuler.southbound.config.aop.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * excel
 *
 * @since 2022/09/27
 */
@Slf4j
@RestController
@RequestMapping("/template")
public class ExcelController {
    /**
     * 批量导入模板下载
     *
     * @param response response
     * @param templateType 模板类型
     */
    @GetMapping("/downloadExcel")
    @Log(operation = "Download Excel", detail = "Download excel.")
    public void downloadExcel(HttpServletResponse response, String templateType) {
        String path = "";
        String fileName = "";
        switch (Objects.requireNonNull(ExcelTemplateEnum.getExcelTemplateByKey(templateType))) {
            case CPU_TEMPLATE:
                path = ExcelTemplateEnum.CPU_TEMPLATE.value();
                fileName = I18NServer.get("southbound_excel_filename_cpu");
                break;
            case WHOLE_TEMPLATE:
                path = ExcelTemplateEnum.WHOLE_TEMPLATE.value();
                fileName = I18NServer.get("southbound_excel_filename_whole");
                break;
            case CHIP_TEMPLATE:
                path = ExcelTemplateEnum.CHIP_TEMPLATE.value();
                fileName = I18NServer.get("southbound_excel_filename_chip");
                break;
            case DRIVER_TEMPLATE:
                path = ExcelTemplateEnum.DRIVER_TEMPLATE.value();
                fileName = I18NServer.get("southbound_excel_filename_driver");
                break;
            case BOARD_TEMPLATE:
                path = ExcelTemplateEnum.BOARD_TEMPLATE.value();
                fileName = I18NServer.get("southbound_excel_filename_board");
                break;
            default:
                log.warn("the current template file does not exist.");
        }
        InputStream fis = null;
        try {
            fis = this.getClass().getClassLoader().getResourceAsStream(path);
            // 设置编码格式UTF-8解决中文乱码
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + java.net.URLEncoder.encode(fileName, "UTF-8"));
            byte[] bt = new byte[50];
            int length;
            while ((length = fis.read(bt)) > 0) {
                response.getOutputStream().write(bt, 0, length);
            }
        } catch (IOException ioException) {
            log.error("create file error,", ioException.getMessage());
        } finally {
            try {
                response.getOutputStream().flush();
                response.getOutputStream().close();
                assert fis != null;
                fis.close();
            } catch (IOException ioException) {
                log.error("flush or close steam error,", ioException.getMessage());
            }
        }
    }
}
