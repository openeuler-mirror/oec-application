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
import com.openeuler.southbound.mapper.factory.BoardFactoryMapper;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.service.factory.BoardFactoryService;

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
 * 板卡厂商service 实现类
 *
 * @since 2022-8-29
 */
@Slf4j
@Service
public class BoardFactoryServiceImpl implements BoardFactoryService {
    @Autowired
    private BoardFactoryMapper boardFactoryMapper;

    @Override
    public PageInfo queryAll(BoardFactory boardFactory) {
        Page page = PageHelper.startPage(boardFactory.getPageIndex(), boardFactory.getPageSize());
        List<BoardFactory> boardFactoryList = boardFactoryMapper.queryAll(boardFactory);
        PageInfo pageInfo = new PageInfo(boardFactoryList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    @Override
    public int add(BoardFactory boardFactory) {
        return boardFactoryMapper.add(boardFactory);
    }

    @Override
    public int update(BoardFactory boardFactory) {
        return boardFactoryMapper.update(boardFactory);
    }

    @Override
    public int deleteByIds(String ids) {
        return boardFactoryMapper.deleteByIds(ids);
    }

    @Override
    public Map<String, List<String>> queryModelList(BoardFactory boardFactory) {
        List<String> typicalModelList = boardFactoryMapper.queryTypicalModelList(boardFactory);
        List<String> extendBoardModelList = boardFactoryMapper.queryExtendModelList(boardFactory);
        List<String> extendModels = new ArrayList<>();
        extendBoardModelList.forEach(item -> Collections.addAll(extendModels, item.split(",")));
        Map<String, List<String>> respMap = new HashMap<>();
        respMap.put("typicalModelList", typicalModelList);
        respMap.put("extendBoardModelList", extendModels);
        return respMap;
    }

    @Override
    public List<String> queryChipFactory() {
        return boardFactoryMapper.queryChipFactory();
    }

    @Override
    public List<String> queryChipModel(String chipFactory, String boardType) {
        return boardFactoryMapper.queryChipModel(chipFactory, boardType);
    }

    @Override
    public List<String> queryBoardType(BoardFactory boardFactory) {
        return boardFactoryMapper.queryBoardType(boardFactory);
    }

    @Override
    @Transactional
    public ResponseBean uploadBoardFactoryExcel(MultipartFile file) {
        XSSFWorkbook book = null;
        try {
            book = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            log.error("Upload file failed because of file format or type error.");
        }
        // 获取整机厂商工作表
        XSSFSheet sheet = book.getSheet(MessageContent.FORM_SHEET_NAME_BOARD);
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
            BoardFactory boardFactory = BoardFactory.builder()
                    .chipFactory(getCellStringValue(row.getCell(cols++)))
                    .boardType(getCellStringValue(row.getCell(cols++)))
                    .chipModel(getCellStringValue(row.getCell(cols++)))
                    .typicalBoardModel(getCellStringValue(row.getCell(cols++)))
                    .boardItem(getCellStringValue(row.getCell(cols++)))
                    .extendBoardModelItem(getCellStringValue(row.getCell(cols++)))
                    .x86Priority(getCellStringValue(row.getCell(cols++)))
                    .armPriority(getCellStringValue(row.getCell(cols++)))
                    .demandSource(getCellStringValue(row.getCell(cols++)))
                    .interfacePerson(getCellStringValue(row.getCell(cols++)))
                    .contact(getCellStringValue(row.getCell(cols++)))
                    .middleman(getCellStringValue(row.getCell(cols)))
                    .build();
            handleData(boardFactory);
            String chipId = boardFactoryMapper.queryChipId(boardFactory);
            if (StringUtils.isEmpty(chipId)) {
                return ResponseBean.error(MessageContent.FORM_DATA_NOT_EXIST_CHIP_EXC);
            }
            addCount = boardFactoryMapper.add(boardFactory);
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

    private void handleData(BoardFactory boardFactory) {
        String extendBoardModelItem = boardFactory.getExtendBoardModelItem();
        boardFactory.setExtendBoardModelItem(extendBoardModelItem.replaceAll("\n", ","));
        String[] extendBoardModelItemArray = extendBoardModelItem.split("\n");
        StringBuilder extendBoardModel = new StringBuilder();
        for (String modelAndItem : extendBoardModelItemArray) {
            String[] modelAndItemArray = modelAndItem.split("/");
            extendBoardModel.append(modelAndItemArray[0])
                    .append(",");
        }
        extendBoardModel.deleteCharAt(extendBoardModel.length() - 1);
        boardFactory.setExtendBoardModel(extendBoardModel.toString());
    }

    @Override
    public List<BoardFactory> exportAllData() {
        return boardFactoryMapper.exportAllData();
    }

    private String getCellStringValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return String.valueOf(cell.getStringCellValue());
    }
}
