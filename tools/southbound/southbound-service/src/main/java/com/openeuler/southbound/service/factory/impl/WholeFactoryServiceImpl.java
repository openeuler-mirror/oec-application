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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.mapper.factory.WholeFactoryMapper;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.service.factory.WholeFactoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * 整机厂商service 实现类
 *
 * @since 2022-8-29
 */
@Slf4j
@Service
public class WholeFactoryServiceImpl implements WholeFactoryService {
    @Autowired
    private WholeFactoryMapper wholeFactoryMapper;

    @Override
    public PageInfo queryAll(WholeFactory wholeFactory) {
        Page page = PageHelper.startPage(wholeFactory.getPageIndex(), wholeFactory.getPageSize());
        List<WholeFactory> wholeFactoryList = wholeFactoryMapper.queryAll(wholeFactory);
        PageInfo pageInfo = new PageInfo(wholeFactoryList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    @Override
    public int add(WholeFactory wholeFactory) {
        return wholeFactoryMapper.insert(wholeFactory);
    }

    @Override
    public int update(WholeFactory wholeFactory) {
        return wholeFactoryMapper.update(wholeFactory);
    }

    @Override
    public int deleteByIds(String ids) {
        return wholeFactoryMapper.deleteByIds(ids);
    }

    @Override
    public List<String> queryNameList() {
        return wholeFactoryMapper.queryNameList();
    }

    @Override
    public Map<String, List<String>> queryModelList(WholeFactory wholeFactory) {
        List<String> typicalModelList = wholeFactoryMapper.queryTypicalModelList(wholeFactory);
        List<String> extendBoardModelList = wholeFactoryMapper.queryExtendModelList(wholeFactory);
        List<String> extendModels = new ArrayList<>();
        extendBoardModelList.forEach(item -> Collections.addAll(extendModels, item.split(",")));
        Map<String, List<String>> respMap = new HashMap<>();
        respMap.put("hardwareModelList", typicalModelList);
        respMap.put("extendModelList", extendModels);
        return respMap;
    }

    @Override
    public List<String> queryCpuFactory(String wholeFactory) {
        return wholeFactoryMapper.queryCpuFactory(wholeFactory);
    }

    @Override
    public List<String> queryCpuModel(String wholeFactory, String cpuFactory) {
        return wholeFactoryMapper.queryCpuModel(wholeFactory, cpuFactory);
    }

    @Override
    @Transactional
    public ResponseBean uploadWholeFactoryExcel(MultipartFile file) {
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            log.error("Upload file failed because of file format or type error.");
        }
        // 获取整机厂商工作表
        XSSFSheet sheet = book.getSheet(MessageContent.FORM_SHEET_NAME_WHOLE);
        // add some validation here
        if (sheet == null) {
            return ResponseBean.error(MessageContent.FORM_NO_CURRENT_WORKSHEET_EXC);
        }
        int addCount = 0;
        // parse data
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i + 2); // 去掉表头和举例
            int cols = 0;
            // 当第一列为空时，则结束导入
            if (StringUtils.isEmpty(getCellStringValue(row.getCell(cols)))) {
                break;
            }
            WholeFactory wholeFactory = WholeFactory.builder()
                    .wholeFactory(getCellStringValue(row.getCell(cols++)))
                    .cpuFactory(getCellStringValue(row.getCell(cols++)))
                    .cpuModel(getCellStringValue(row.getCell(cols++)))
                    .hardwareModel(getCellStringValue(row.getCell(cols++)))
                    .extendModel(getCellStringValue(row.getCell(cols++)))
                    .interfacePerson(getCellStringValue(row.getCell(cols++)))
                    .contact(getCellStringValue(row.getCell(cols++)))
                    .middleman(getCellStringValue(row.getCell(cols)))
                    .build();
            wholeFactory.setExtendModel(wholeFactory.getExtendModel().replaceAll("\n", ","));
            String cpuId = wholeFactoryMapper.queryCpuId(wholeFactory);
            if (StringUtils.isEmpty(cpuId)) {
                return ResponseBean.error(MessageContent.FORM_DATA_NOT_EXIST_CPU_EXC);
            }
            addCount = wholeFactoryMapper.insert(wholeFactory);
        }
        try {
            book.close();
        } catch (IOException e) {
            log.error("Upload file failed because of opening file.");
        }
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    @Override
    public List<WholeFactory> exportAllData() {
        return wholeFactoryMapper.exportAllData();
    }

    private String getCellStringValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return String.valueOf(cell.getStringCellValue());
    }
}
