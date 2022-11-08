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

package com.openeuler.southbound.service.log.impl;

import static com.openeuler.southbound.common.utils.ExcelExportUtil.addCellWithStyle;
import static com.openeuler.southbound.common.utils.ExcelExportUtil.getBodyCellStyle;
import static com.openeuler.southbound.common.utils.ExcelExportUtil.getHeadCellStyle;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.common.utils.I18NServer;
import com.openeuler.southbound.mapper.log.BusinessLogMapper;
import com.openeuler.southbound.model.BaseRequestBean;
import com.openeuler.southbound.model.log.BusinessLog;
import com.openeuler.southbound.service.log.SystemLogService;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 操作日志-service 实现类
 *
 * @since 2022-8-26
 */
@Service
@Slf4j
public class BusinessLogServiceImpl implements SystemLogService {
    private static final int POSITION_ROW = 0;
    private static final int POSITION_COL = 0;

    @Autowired
    private BusinessLogMapper businessLogMapper;

    /**
     * 插入日志对象
     *
     * @param businessLog 日志对象
     */
    @Override
    public void insert(BusinessLog businessLog) {
        int rowCount = businessLogMapper.insert(businessLog);
        Assert.state(rowCount > 0, "Error saving log to database.");
    }

    /**
     * 获取用户的业务日志（操作日志）
     *
     * @param requestBean 查询参数，关键字和分页
     * @return 系统统一返回类
     */
    @Override
    public PageInfo queryAllLog(BaseRequestBean requestBean) {
        Page page = PageHelper.startPage(requestBean.getPageIndex(), requestBean.getPageSize());
        List<BusinessLog> logs = businessLogMapper.queryAll(requestBean);
        PageInfo pageInfo = new PageInfo(logs);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    /**
     * 导出用户业务日志（操作日志）
     *
     * @param requestBean 筛选条件对象
     * @return SXSSFWorkbook
     */
    @Override
    public SXSSFWorkbook
    exportExcel(BaseRequestBean requestBean) {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        CellStyle headStyle = getHeadCellStyle(workbook);
        Sheet sheet = workbook.createSheet();
        // 指定开始的行数和列数
        int rows = POSITION_ROW;
        // 指定单元格的宽度
        int[] colWidths = new int[]{3000, 3000, 8000, 2000, 6000, 8000};
        // 表头
        Row head = sheet.createRow(rows++);
        int cols = POSITION_COL;
        String[] columns = new String[]{"用户名", "操作IP", "操作名称", "操作结果", "操作时间", "操作详情"};
        for (int i = 0; i < columns.length; ++i) {
            sheet.setColumnWidth(cols, colWidths[i]);
            addCellWithStyle(head, cols++, headStyle).setCellValue(columns[i]);
        }

        // 表内容
        CellStyle bodyStyle = getBodyCellStyle(workbook);
        for (BusinessLog businessLog : businessLogMapper.queryAll(requestBean)) {
            log.info(businessLog.toString());
            cols = POSITION_COL;
            Row row = sheet.createRow(rows++);
            addCellWithStyle(row, cols++, bodyStyle).setCellValue(businessLog.getUserName());
            addCellWithStyle(row, cols++, bodyStyle).setCellValue(businessLog.getHost());
            addCellWithStyle(row, cols++, bodyStyle).setCellValue(businessLog.getOperation());
            addCellWithStyle(row, cols++, bodyStyle).setCellValue(
                    "1".equals(businessLog.getResult())
                            ? I18NServer.get("southbound_excel_export_file_log_column_result_success")
                            : I18NServer.get("southbound_excel_export_file_log_column_result_fail")
            );
            addCellWithStyle(row, cols++, bodyStyle).setCellValue(businessLog.getDateTime());
            addCellWithStyle(row, cols, bodyStyle).setCellValue(businessLog.getDetail());
        }
        return workbook;
    }
}
