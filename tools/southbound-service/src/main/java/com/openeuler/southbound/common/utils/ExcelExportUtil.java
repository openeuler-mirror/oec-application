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

package com.openeuler.southbound.common.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 * 导出到excel工具类
 *
 * @since 2022-09-22
 */
public class ExcelExportUtil {
    /**
     * 获得excel body样式
     *
     * @param workbook 工作簿
     * @return 样式
     */
    public static CellStyle getBodyCellStyle(Workbook workbook) {
        return getBaseCellStyle(workbook);
    }

    /**
     * 添加样式
     *
     * @param row         行数
     * @param colPosition 列数
     * @param cellStyle   样式
     * @return 样式
     */
    public static Cell addCellWithStyle(Row row, int colPosition, CellStyle cellStyle) {
        Cell cell = row.createCell(colPosition);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    /**
     * 获取表头样式
     *
     * @param workbook 工作薄
     * @return 样式
     */
    public static CellStyle getHeadCellStyle(Workbook workbook) {
        CellStyle style = getBaseCellStyle(workbook);

        // fill
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    /**
     * 获取基础单元格表格样式
     *
     * @param workbook 工作薄
     * @return 样式
     */
    public static CellStyle getBaseCellStyle(Workbook workbook) {
        CellStyle workbookCellStyle = workbook.createCellStyle();
        // font
        Font font = workbook.createFont();
        font.setBold(true);
        workbookCellStyle.setFont(font);

        // align
        workbookCellStyle.setAlignment(HorizontalAlignment.CENTER);
        workbookCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        return workbookCellStyle;
    }


    /**
     * 获取样单元格的值
     *
     * @param cell 目标单元格
     * @return 值
     */
    public static String getCellStringValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return String.valueOf(cell.getStringCellValue());
    }
}