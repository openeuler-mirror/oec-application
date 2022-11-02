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
import com.openeuler.southbound.mapper.factory.ChipFactoryMapper;
import com.openeuler.southbound.mapper.plan.VersionPlanMapper;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.overall.BoardOverall;
import com.openeuler.southbound.model.factory.ChipFactory;
import com.openeuler.southbound.service.factory.ChipFactoryService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.HashMap;
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
 * 芯片厂商service 实现类
 *
 * @since 2022-8-29
 */
@Slf4j
@Service
public class ChipFactoryServiceImpl implements ChipFactoryService {
    @Autowired
    private ChipFactoryMapper chipFactoryMapper;

    @Autowired
    private VersionPlanMapper versionPlanMapper;

    @Override
    public PageInfo queryAll(ChipFactory chipFactory) {
        Page page = PageHelper.startPage(chipFactory.getPageIndex(), chipFactory.getPageSize());
        List<ChipFactory> chipFactoryList = chipFactoryMapper.queryAll(chipFactory);
        PageInfo pageInfo = new PageInfo(chipFactoryList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    @Override
    @Transactional
    public int add(ChipFactory chipFactory) {
        chipFactoryMapper.add(chipFactory);
        return chipFactoryMapper.addChipVersion(chipFactory.getChipId(), chipFactory.getVersionIds());
    }

    @Override
    @Transactional
    public int update(ChipFactory chipFactory) {
        // 更新芯片厂商表
        int count = chipFactoryMapper.update(chipFactory);
        if (count > 0) {
            // 先删除原有关系中间表
            chipFactoryMapper.deleteChipVersion(String.valueOf(chipFactory.getChipId()));
        }
        // 删除原有关系表后再重新添加
        return chipFactoryMapper.addChipVersion(chipFactory.getChipId(), chipFactory.getVersionIds());
    }

    @Override
    @Transactional
    public int deleteByIds(String ids) {
        // 删除关联关系表
        chipFactoryMapper.deleteChipVersion(ids);
        // 删除芯片厂商
        return chipFactoryMapper.deleteByIds(ids);
    }


    @Override
    public List<String> queryNameList() {
        return chipFactoryMapper.queryNameList();
    }

    @Override
    public List<String> queryModelList() {
        return chipFactoryMapper.queryModelList();
    }

    @Override
    public List<String> queryChipModelName(String chipFactory) {
        return chipFactoryMapper.queryChipModelName(chipFactory);
    }

    @Override
    public boolean queryVersionSupport(BoardOverall boardOverall) {
        int count = chipFactoryMapper.queryVersionSupport(boardOverall);
        return count >= 1;
    }

    @Override
    @Transactional
    public ResponseBean uploadChipFactoryExcel(MultipartFile file) {
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            log.error("Upload file failed because of file format or type error.");
        }
        // 获取整机厂商工作表
        XSSFSheet sheet = book.getSheet(MessageContent.FORM_SHEET_NAME_CHIP);
        // add some validation here
        if (sheet == null) {
            return ResponseBean.error(MessageContent.FORM_NO_CURRENT_WORKSHEET_EXC);
        }
        // parse data
        int addCount = 0;
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i + 2); // 去掉表头和举例
            int cols = 0;
            // 当第一列为空时，则结束导入
            if (StringUtils.isEmpty(getCellStringValue(row.getCell(cols)))) {
                break;
            }
            ChipFactory chipFactory = ChipFactory.builder()
                    .chipFactory(getCellStringValue(row.getCell(cols++)))
                    .chipModel(getCellStringValue(row.getCell(cols++)))
                    .versionNames(getCellStringValue(row.getCell(cols)))
                    .build();
            HashMap<String, String> map = versionPlanMapper.queryIdsByVersionName(chipFactory.getVersionNames());
            if (map == null) {
                return ResponseBean.error(MessageContent.FORM_DATA_IS_INCORRECT_EXC);
            }
            chipFactory.setVersionIds(map.get("versionId"));
            // 新增芯片厂商
            chipFactoryMapper.add(chipFactory);
            // 新增芯片厂商关联版本关系
            addCount = chipFactoryMapper.addChipVersion(chipFactory.getChipId(), chipFactory.getVersionIds());
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
    public List<ChipFactory> exportAllData() {
        return chipFactoryMapper.exportAllData();
    }

    private String getCellStringValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return String.valueOf(cell.getStringCellValue());
    }
}
