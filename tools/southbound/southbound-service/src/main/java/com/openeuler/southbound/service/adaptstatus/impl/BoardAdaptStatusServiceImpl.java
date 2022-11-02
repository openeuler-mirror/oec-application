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

package com.openeuler.southbound.service.adaptstatus.impl;

import com.openeuler.southbound.common.utils.StringFormatUtil;
import com.openeuler.southbound.mapper.adaptstatus.BoardAdaptStatusMapper;
import com.openeuler.southbound.mapper.factory.CpuFactoryMapper;
import com.openeuler.southbound.model.adaptstatus.BoardAdaptStatusResp;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.service.adaptstatus.BoardAdaptStatusService;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 板卡适配状态 Service 实现类
 *
 * @since 2022-8-29
 */
@Service
@Slf4j
public class BoardAdaptStatusServiceImpl implements BoardAdaptStatusService {
    /**
     * 板卡适配状态 mapper
     */
    @Autowired
    private BoardAdaptStatusMapper adaptStatusMapper;

    /**
     * CPU厂商 mapper
     */
    @Autowired
    private CpuFactoryMapper cpuFactoryMapper;

    /**
     * 获取板卡适配状态
     *
     * @param versionName 版本名称
     * @return 适配状态集合
     */
    public List<BoardAdaptStatusResp> getStatisticFromChipFactory(String versionName) {
        List<BoardAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有芯片厂商列表
        List<String> factoryNames = adaptStatusMapper.selectAllChipFactory();
        // 根据厂商筛选，获得统计数据
        factoryNames.forEach(factoryNameItem -> {
            // 所有芯片厂商为 factoryNameItem 的板卡
            List<BoardFactory> boards = adaptStatusMapper.selectByChipFactory(factoryNameItem, versionName);
            BoardAdaptStatusResp resp = statisticHandle(boards);
            resp.setChipFactory(factoryNameItem);
            resultList.add(resp);
        });
        return resultList;
    }

    /**
     * 遍历不同属性查询出的板卡
     *
     * @param boardList 板卡列表
     * @return BoardAdaptStatusResp
     */
    private BoardAdaptStatusResp statisticHandle(List<BoardFactory> boardList) {
        // 典型板卡-适配列表
        List<String> typicalModelAdaptList = new ArrayList<>();
        // 典型板卡-适配中列表
        List<String> typicalModelPlanAdaptList = new ArrayList<>();
        // 典型板卡-未适配列表
        List<String> typicalModelNotAdaptList = new ArrayList<>();

        // 扩展板卡-适配列表
        List<String> extendModelAdaptList = new ArrayList<>();
        // 扩展板卡-已适配中列表
        List<String> extendModelPlanAdaptList = new ArrayList<>();
        // 扩展板卡-未适配列表
        List<String> extendModelNotAdaptList = new ArrayList<>();
        // 版本不支持列表
        List<String> osNotSupList = new ArrayList<>();
        for (BoardFactory boardItem : boardList) {
            //  判断当前版本是否支持
            int supportOfChip = adaptStatusMapper.selectOsSupportOfChip(boardItem.getChipFactory(),
                    boardItem.getChipModel(), boardItem.getVersionName());
            if (supportOfChip <= 0) {
                // 版本不支持
                osNotSupList.add(getBoardShowInfoStrOfTypical(boardItem));
                osNotSupList.addAll(getBoardShowInfoStrOfExtends(boardItem));
                continue;
            }
            // 在社区中查询匹配数据-典型板卡
            statisticTypicalModel(typicalModelAdaptList, typicalModelPlanAdaptList, typicalModelNotAdaptList,
                    boardItem);
            // 在社区中查询匹配数据-当前典型板卡对应的全部扩展板卡
            statisticExtendModel(extendModelAdaptList, extendModelPlanAdaptList, extendModelNotAdaptList, boardItem);
        }
        BoardAdaptStatusResp resp = new BoardAdaptStatusResp();
        // 典型板卡已适配
        resp.setTypicalModelAdaptList(typicalModelAdaptList);
        // 典型板卡适配中
        resp.setTypicalModelPlanAdaptList(typicalModelPlanAdaptList);
        // 典型机板卡适配
        resp.setTypicalModelNotAdaptList(typicalModelNotAdaptList);
        resp.setModelNotSupList(osNotSupList);

        // 该扩展板卡已适配
        resp.setExtendModelAdaptList(extendModelAdaptList);
        // 扩展板卡适配中
        resp.setExtendModelPlanAdaptList(extendModelPlanAdaptList);
        // 扩展板卡未适配
        resp.setExtendModelNotAdaptList(extendModelNotAdaptList);
        return resp;
    }

    /**
     * 统计典型板卡数据
     *
     * @param hardwareModelAdaptList     已适配列表
     * @param hardwareModelPlanAdaptList 适配中列表
     * @param hardwareModelNotAdaptList  未适配列表
     * @param board                      板卡信息
     */
    private void statisticTypicalModel(
            List<String> hardwareModelAdaptList, List<String> hardwareModelPlanAdaptList,
            List<String> hardwareModelNotAdaptList, BoardFactory board) {
        // 在社区中查询匹配数据-典型板卡
        int communityMatchCountTypical = adaptStatusMapper.selectBoardStatusFromCommunity(board);
        if (communityMatchCountTypical > 0) {
            // 在社区中查到-已适配
            hardwareModelAdaptList.add(getBoardShowInfoStrOfTypical(board));
        } else {
            // 社区中没有查到,去计划表中查询
            String typicalBoardModel = board.getTypicalBoardModel();
            if (typicalBoardModel == null) {
                log.warn("this board don't have typical BoardModel!");
            }
            int planCount = adaptStatusMapper.selectBoardStatusFromPlan(board);
            if (planCount > 0) {
                // 计划表中有,适配中
                hardwareModelPlanAdaptList.add(getBoardShowInfoStrOfTypical(board));
            } else {
                // 计划表中也没有,未适配
                hardwareModelNotAdaptList.add(getBoardShowInfoStrOfTypical(board));
            }
        }
    }

    /**
     * 统计扩展板卡
     *
     * @param extendModelAdaptList     已适配列表
     * @param extendModelPlanAdaptList 适配中列表
     * @param extendModelNotAdaptList  未适配列表
     * @param board                    板卡信息
     */
    private void statisticExtendModel(
            List<String> extendModelAdaptList, List<String> extendModelPlanAdaptList,
            List<String> extendModelNotAdaptList, BoardFactory board) {
        // 板卡型号列表
        List<String> extendModelList = StringFormatUtil.str2ListUtil(board.getExtendBoardModel());
        extendModelList.stream().filter(extendItem -> {
            board.setTypicalBoardModel(extendItem);
            // 计划表中不区分典型/扩展板卡
            int hardwareModelStatus = adaptStatusMapper.selectBoardStatusFromCommunity(board);
            if (hardwareModelStatus > 0) {
                // 社区表中有，已适配
                extendModelAdaptList.add(getBoardShowInfoStrOfExtend(board, extendItem));
                return false;
            }
            return true;
        }).forEach(extendItem2 -> {
            board.setTypicalBoardModel(extendItem2);
            // 计划表中不区分典型/扩展板卡
            int planCount = adaptStatusMapper.selectBoardStatusFromPlan(board);
            if (planCount > 0) {
                // 计划表中有,适配中
                extendModelPlanAdaptList.add(getBoardShowInfoStrOfExtend(board, extendItem2));
            } else {
                // 计划表中也没有,未适配
                extendModelNotAdaptList.add(getBoardShowInfoStrOfExtend(board, extendItem2));
            }
        });
    }

    @Override
    public List<BoardAdaptStatusResp> getStatisticFromChipModel(String versionName) {
        List<BoardAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有芯片型号列表
        List<String> chipModels = adaptStatusMapper.selectAllChipModels();
        // 根据芯片型号筛选，获得统计数据
        chipModels.forEach(chipModelItem -> {
            // 所有芯片型号为chipModelItem的板卡
            List<BoardFactory> boards = adaptStatusMapper.selectByChipModel(chipModelItem, versionName);
            BoardAdaptStatusResp resp = statisticHandle(boards);
            resp.setChipModel(chipModelItem);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<BoardAdaptStatusResp> getStatisticFromBoardType(String versionName) {
        List<BoardAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有板卡类型列表
        List<String> allBoardType = adaptStatusMapper.selectAllBoardType();
        // 根据板卡类型筛选,获得统计数据
        allBoardType.forEach(boardTypeItem -> {
            // 所有板卡类型为boardTypeItem的板卡
            List<BoardFactory> boards = adaptStatusMapper.selectByBoardType(boardTypeItem, versionName);
            BoardAdaptStatusResp resp = statisticHandle(boards);
            resp.setBoardType(boardTypeItem);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<BoardAdaptStatusResp> getStatisticFromOsVersion() {
        List<BoardAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有系统版本列表
        List<String> allVersionName = adaptStatusMapper.selectAllVersionName();
        // 根据系统版本筛选，获得统计数据
        allVersionName.forEach(versionItem -> {
            // 所有系统版本为 versionItem 的板卡
            List<BoardFactory> boards = adaptStatusMapper.selectAllBoardFromBoardFactory(versionItem);
            BoardAdaptStatusResp resp = statisticHandle(boards);
            resp.setOsVersion(versionItem);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<BoardAdaptStatusResp> getStatisticFromOsArch(String versionName) {
        List<BoardAdaptStatusResp> resultList = new ArrayList<>();
        // 获取架构列表
        List<String> allArch = cpuFactoryMapper.queryArchitectureList();
        // 根据架构筛选，获得统计数据
        allArch.forEach(architecture -> {
            List<BoardFactory> boards = adaptStatusMapper.selectAllBoardFromBoardFactory(versionName);
            boards.forEach(item -> {
                item.setArchitecture(architecture);
            });
            BoardAdaptStatusResp resp = statisticHandle(boards);
            resp.setOsArch(architecture);
            resultList.add(resp);
        });
        return resultList;
    }


    private String getBoardPrefixStr(BoardFactory boardItem) {
        return boardItem.getChipFactory() + " - " + boardItem.getBoardType()
                + " - " + boardItem.getChipModel() + " - ";
    }

    private String getBoardShowInfoStrOfTypical(BoardFactory boardItem) {
        return getBoardPrefixStr(boardItem) + boardItem.getTypicalBoardModel();
    }


    private String getBoardShowInfoStrOfExtend(BoardFactory boardItem, String extendModel) {
        return getBoardPrefixStr(boardItem) + extendModel;
    }

    private List<String> getBoardShowInfoStrOfExtends(BoardFactory boardItem) {
        String prefixStr = getBoardPrefixStr(boardItem);
        List<String> resultList = new ArrayList<>();
        StringFormatUtil.str2ListUtil(boardItem.getExtendBoardModel()).forEach(
                item -> resultList.add(prefixStr + item));
        return resultList;
    }
}
