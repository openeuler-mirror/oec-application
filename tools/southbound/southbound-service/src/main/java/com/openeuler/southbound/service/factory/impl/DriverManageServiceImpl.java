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

package com.openeuler.southbound.service.factory.impl;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.mapper.factory.DriverManageMapper;
import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.DriverManage;
import com.openeuler.southbound.model.plan.VersionPlan;
import com.openeuler.southbound.service.factory.DriverManageService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 驱动管理service 实现类
 *
 * @since 2022-8-29
 */
@Slf4j
@Service
public class DriverManageServiceImpl implements DriverManageService {
    @Autowired
    private DriverManageMapper driverManageMapper;

    @Autowired
    private VersionPlanMapper versionPlanMapper;

    @Override
    public PageInfo queryAll(DriverManage driverManage) {
        Page page = PageHelper.startPage(driverManage.getPageIndex(), driverManage.getPageSize());
        List<DriverManage> driverListManage = driverManageMapper.queryAll(driverManage);
        PageInfo pageInfo = new PageInfo(driverListManage);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    @Override
    public int add(DriverManage driverManage) {
        return driverManageMapper.add(driverManage);
    }

    @Override
    public int update(DriverManage driverManage) {
        return driverManageMapper.update(driverManage);
    }

    @Override
    public int deleteByIds(String ids) {
        return driverManageMapper.deleteByIds(ids);
    }

    @Override
    public List<String> queryChipNameList() {
        return driverManageMapper.queryChipNameList();
    }

    @Override
    public List<String> queryModelList(String chipFactory) {
        return driverManageMapper.queryModelList(chipFactory);
    }

    @Override
    public List<String> queryDriverName(String chipFactory) {
        return driverManageMapper.queryDriverName(chipFactory);
    }

    @Override
    @Transactional
    public ResponseBean uploadDriverFactoryExcel(MultipartFile file) {
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            log.error("Upload file failed because of file format or type error.");
        }
        List<VersionPlan> versionPlanList = versionPlanMapper.queryAll(null);
        int addCount = 0;
        boolean hasSheetName = true;
        for (VersionPlan versionPlan : versionPlanList) {
            // 获取整机厂商工作表
            XSSFSheet sheet = book.getSheet(MessageContent.FORM_SHEET_NAME_DRIVER + versionPlan.getVersionName());
            // add some validation here
            if (sheet == null) {
                continue;
            }
            // parse data
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                hasSheetName = false;
                XSSFRow row = sheet.getRow(i + 2); // 去掉表头和举例
                int cols = 0;
                // 当第一列为空时，则结束导入
                if (StringUtils.isEmpty(getCellStringValue(row.getCell(cols)))) {
                    break;
                }
                DriverManage driverManage = DriverManage.builder()
                        .chipFactory(getCellStringValue(row.getCell(cols++)))
                        .driverName(getCellStringValue(row.getCell(cols++)))
                        .kernelDriverPublish(getCellStringValue(row.getCell(cols++)))
                        .kernelDriverVersion(getCellStringValue(row.getCell(cols++)))
                        .exteriorDriverPublish(getCellStringValue(row.getCell(cols++)))
                        .exteriorDriverPublishTime(getCellStringValue(row.getCell(cols++)))
                        .exteriorDriverVersion(getCellStringValue(row.getCell(cols++)))
                        .webDriverUrl(getCellStringValue(row.getCell(cols++)))
                        .allDriverUrl(getCellStringValue(row.getCell(cols)))
                        .versionId(String.valueOf(versionPlan.getVersionId()))
                        .build();
                // 新增驱动数据
                addCount = driverManageMapper.add(driverManage);
            }
        }
        try {
            book.close();
        } catch (IOException e) {
            log.error("Upload file failed because of opening file.");
        }
        if (hasSheetName) {
            return ResponseBean.error(MessageContent.FORM_NO_CURRENT_WORKSHEET_EXC);
        }
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    @Override
    public List<DriverManage> exportAllData() {
        return driverManageMapper.exportAllData();
    }

    private String getCellStringValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return String.valueOf(cell.getStringCellValue());
    }
}
