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

import static com.openeuler.southbound.common.utils.ExcelExportUtil.getCellStringValue;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.mapper.factory.CpuFactoryMapper;
import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.CpuFactory;
import com.openeuler.southbound.model.overall.WholeOverall;
import com.openeuler.southbound.service.factory.CpuFactoryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * CPU厂商service 实现类
 *
 * @since 2022-8-29
 */
@Slf4j
@Service
public class CpuFactoryServiceImpl implements CpuFactoryService {
    @Autowired
    private CpuFactoryMapper cpuFactoryMapper;

    @Autowired
    private VersionPlanMapper versionPlanMapper;

    @Override
    public PageInfo queryAll(CpuFactory cpuFactory) {
        Page page = PageHelper.startPage(cpuFactory.getPageIndex(), cpuFactory.getPageSize());
        List<CpuFactory> cpuFactoryList = cpuFactoryMapper.queryAll(cpuFactory);
        PageInfo pageInfo = new PageInfo(cpuFactoryList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    @Override
    @Transactional
    public int add(CpuFactory cpuFactory) {
        cpuFactoryMapper.add(cpuFactory);
        return cpuFactoryMapper.addCpuVersion(cpuFactory);
    }

    @Override
    @Transactional
    public int update(CpuFactory cpuFactory) {
        // 更新CPU厂商表
        int updateCpuFactoryCount = cpuFactoryMapper.update(cpuFactory);
        if (updateCpuFactoryCount > 0) {
            // 先删除原有关系中间表
            cpuFactoryMapper.deleteCpuVersion(String.valueOf(cpuFactory.getCpuId()));
        }
        return cpuFactoryMapper.addCpuVersion(cpuFactory);
    }

    @Override
    @Transactional
    public int deleteByIds(String ids) {
        cpuFactoryMapper.deleteCpuVersion(ids);
        return cpuFactoryMapper.deleteByIds(ids);
    }

    @Override
    public List<String> queryNameList() {
        return cpuFactoryMapper.queryNameList();
    }

    @Override
    public List<String> queryModelList() {
        return cpuFactoryMapper.queryModelList();
    }

    @Override
    public List<String> queryCpuModelNames(String cpuFactory) {
        return cpuFactoryMapper.queryCpuModelNames(cpuFactory);
    }

    @Override
    public List<String> queryArchitectureList() {
        return cpuFactoryMapper.queryArchitectureList();
    }


    @Override
    public boolean isSupport(WholeOverall wholeOverall) {
        // 在CPU厂商中查询支持版本
        int count = cpuFactoryMapper.selectSupportVersion(wholeOverall);
        return count > 0;
    }

    @Override
    @Transactional
    public ResponseBean uploadCpuFactoryExcel(MultipartFile file) {
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            log.error("Create XSSFWorkbook failed.Detail: ", e.getMessage());
        }
        // 获取CPU厂商工作表
        XSSFSheet sheet = book.getSheet(MessageContent.FORM_SHEET_NAME_CPU);
        // add some validation here
        if (sheet == null) {
            return ResponseBean.error(MessageContent.FORM_NO_CURRENT_WORKSHEET_EXC);
        }
        // parse data
        int cpuCount = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i + 2); // 去掉表头和举例
            int cols = 0;
            // 当第一列为空时，则结束导入
            if (StringUtils.isEmpty(getCellStringValue(row.getCell(cols)))) {
                break;
            }
            CpuFactory cpuFactory = CpuFactory.builder()
                    .cpuFactory(getCellStringValue(row.getCell(cols++)))
                    .cpuModel(getCellStringValue(row.getCell(cols++)))
                    .architecture(getCellStringValue(row.getCell(cols++)))
                    .versionNames(getCellStringValue(row.getCell(cols++)))
                    .releaseTime(getCellStringValue(row.getCell(cols)))
                    .build();
            HashMap<String, String> map = versionPlanMapper.queryIdsByVersionName(cpuFactory.getVersionNames());
            if (map == null) {
                return ResponseBean.error(MessageContent.FORM_DATA_IS_INCORRECT_EXC);
            }
            cpuFactory.setVersionIds(map.get("versionId"));
            // 新增cpu厂商
            cpuFactoryMapper.add(cpuFactory);
            // 新增cpu厂商关联版本关系
            cpuCount = cpuFactoryMapper.addCpuVersion(cpuFactory);
        }
        try {
            book.close();
        } catch (IOException e) {
            log.error("Close book failed.Detail: " + e.getMessage());
        }
        if (cpuCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    @Override
    public List<CpuFactory> exportAllData() {
        return cpuFactoryMapper.exportAllData();
    }
}