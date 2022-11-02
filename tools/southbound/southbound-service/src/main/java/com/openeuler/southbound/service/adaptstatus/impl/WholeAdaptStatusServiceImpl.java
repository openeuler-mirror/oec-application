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
import com.openeuler.southbound.mapper.adaptstatus.WholeAdaptStatusMapper;
import com.openeuler.southbound.mapper.factory.CpuFactoryMapper;
import com.openeuler.southbound.model.adaptstatus.WholeAdaptStatusResp;
import com.openeuler.southbound.model.factory.WholeFactory;
import com.openeuler.southbound.service.adaptstatus.WholeAdaptStatusService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 整机适配状态 Service 实现类
 *
 * @since 2022-8-29
 */
@Service
public class WholeAdaptStatusServiceImpl implements WholeAdaptStatusService {
    /**
     * 整机适配状态 mapper
     */
    @Autowired
    private WholeAdaptStatusMapper adaptStatusMapper;

    /**
     * CPU厂商 mapper
     */
    @Autowired
    private CpuFactoryMapper cpuFactoryMapper;

    /**
     * 获取整机适配状态
     *
     * @param versionName 版本名称
     * @return 适配状态集合
     */
    public List<WholeAdaptStatusResp> getStatisticFromWholeFactory(String versionName) {
        List<WholeAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有整机厂商列表
        List<String> factoryNames = adaptStatusMapper.queryWholeFactoryNames();
        // 根据厂商筛选，获得统计数据
        factoryNames.forEach(wholeFactoryNameItem -> {
            WholeAdaptStatusResp resp = new WholeAdaptStatusResp(wholeFactoryNameItem);
            // 所有整机厂商为 wholeFactoryNameItem 的整机
            List<WholeFactory> oneWholeFactoryList =
                    adaptStatusMapper.queryWholeByFactory(wholeFactoryNameItem, versionName);
            statisticHandle(resp, oneWholeFactoryList, versionName);
            resultList.add(resp);
        });
        return resultList;
    }

    private void statisticHandle(WholeAdaptStatusResp resp, List<WholeFactory> wholeFactoryList, String versionName) {
        // 典型机型 适配列表
        List<String> hardwareModelAdaptList = new ArrayList<>();
        // 典型机型 适配中列表
        List<String> hardwareModelPlanAdaptList = new ArrayList<>();
        // 典型机型 未适配列表
        List<String> hardwareModelNotAdaptList = new ArrayList<>();
        // 典型机型 不支持列表
        List<String> hardwareModelNotSupList = new ArrayList<>();

        // 扩展机型 适配列表
        List<String> extendModelAdaptList = new ArrayList<>();
        // 扩展机型 适配中列表
        List<String> extendModelPlanAdaptList = new ArrayList<>();
        // 扩展机型 未适配列表
        List<String> extendModelNotAdaptList = new ArrayList<>();

        wholeFactoryList.forEach(wholeItem -> {
            int supCount = adaptStatusMapper.queryCpuSupportOfCpuFactory(wholeItem.getCpuFactory(),
                    wholeItem.getCpuModel(), versionName);
            if (supCount < 1) {
                hardwareModelNotSupList.add(getWholeShowInfoStrOfTypical(wholeItem));
                hardwareModelNotSupList.addAll(getWholeShowInfoStrOfExtends(wholeItem));
                return;
            }
            // 在社区中查询匹配数据-典型机型
            statisticTypicalModel(hardwareModelAdaptList, hardwareModelPlanAdaptList, hardwareModelNotAdaptList,
                    wholeItem, versionName);

            // 在社区中查询匹配数据-当前典型机型对应的全部扩展机型
            statisticExtendModel(extendModelAdaptList, extendModelPlanAdaptList, extendModelNotAdaptList, wholeItem,
                    versionName);
        });
        // 该整机厂商典型机型已适配
        resp.setTypicalModelAdaptList(hardwareModelAdaptList);
        // 该整机厂商典型机型适配中
        resp.setTypicalModelPlanAdaptList(hardwareModelPlanAdaptList);
        // 该整机厂商典型机型未适配
        resp.setTypicalModelNotAdaptList(hardwareModelNotAdaptList);
        // 版本不支持
        resp.setModelNotSupList(hardwareModelNotSupList);
        // 该整机厂商扩展机型已适配
        resp.setExtendModelAdaptList(extendModelAdaptList);
        // 该整机厂商扩展机型适配中
        resp.setExtendModelPlanAdaptList(extendModelPlanAdaptList);
        // 该整机厂商扩展机型未适配
        resp.setExtendModelNotAdaptList(extendModelNotAdaptList);
    }

    /**
     * 统计典型机型数据
     *
     * @param hardwareModelAdaptList     已适配列表
     * @param hardwareModelPlanAdaptList 适配中列表
     * @param hardwareModelNotAdaptList  未适配列表
     * @param wholeItem                  整机信息
     * @param versionName                版本名称
     */
    private void statisticTypicalModel(
            List<String> hardwareModelAdaptList, List<String> hardwareModelPlanAdaptList,
            List<String> hardwareModelNotAdaptList, WholeFactory wholeItem, String versionName) {
        // 在社区中查询匹配数据-典型机型
        int communityMatchCountTypical = adaptStatusMapper.queryWholeMachineModelStatusFromCommunity(
                wholeItem.getWholeFactory(), wholeItem.getHardwareModel(), wholeItem.getArchitecture(),
                wholeItem.getCpuModel(), versionName);
        if (communityMatchCountTypical > 0) {
            // 在社区中查到,已适配
            hardwareModelAdaptList.add(getWholeShowInfoStrOfTypical(wholeItem));
        } else {
            // 社区中没有查到,去计划表中查询
            int wholePlans = adaptStatusMapper.queryWholeMachineModelStatusFromPlan(wholeItem.getWholeFactory(),
                    wholeItem.getHardwareModel(), wholeItem.getCpuFactory(), wholeItem.getCpuModel(), versionName);
            if (wholePlans > 0) {
                // 计划表中有-适配中
                hardwareModelPlanAdaptList.add(getWholeShowInfoStrOfTypical(wholeItem));
            } else {
                // 计划表中也没有-未适配
                hardwareModelNotAdaptList.add(getWholeShowInfoStrOfTypical(wholeItem));
            }
        }
    }

    /**
     * 统计扩展机型
     *
     * @param extendModelAdaptList     已适配列表
     * @param extendModelPlanAdaptList 适配中列表
     * @param extendModelNotAdaptList  未适配列表
     * @param wholeItem                整机信息
     * @param versionName              版本名称
     */
    private void statisticExtendModel(
            List<String> extendModelAdaptList, List<String> extendModelPlanAdaptList,
            List<String> extendModelNotAdaptList, WholeFactory wholeItem, String versionName) {
        // 获取扩展机型
        List<String> extendModelList = StringFormatUtil.str2ListUtil((wholeItem.getExtendModel()));
        // 在社区中查询
        List<String> communityNotMatchListExtend = new ArrayList<>();
        extendModelList.forEach(extendItem -> {
            wholeItem.setHardwareModel(extendItem);
            // 计划表中不区分典型&扩展机型
            int hardwareModelStatus = adaptStatusMapper.queryWholeMachineModelStatusFromCommunity(
                    wholeItem.getWholeFactory(), wholeItem.getHardwareModel(), wholeItem.getArchitecture(),
                    wholeItem.getCpuModel(), versionName);
            if (hardwareModelStatus > 0) {
                // 社区表中有,已适配
                extendModelAdaptList.add(getWholeShowInfoStrOfExtend(wholeItem, extendItem));
            } else {
                // 社区表中没有,待定未为适配或适配中
                communityNotMatchListExtend.add(extendItem);
            }
        });
        // 社区中没有查到,去计划表中查询
        communityNotMatchListExtend.forEach(extendItem -> {
            wholeItem.setHardwareModel(extendItem);
            // 计划表中不区分典型&扩展机型
            int extendMatchPlan = adaptStatusMapper.queryWholeMachineModelStatusFromPlan(wholeItem.getWholeFactory(),
                    wholeItem.getHardwareModel(), wholeItem.getCpuFactory(), wholeItem.getCpuModel(), versionName);
            if (extendMatchPlan > 0) {
                // 计划表中有,适配中
                extendModelPlanAdaptList.add(getWholeShowInfoStrOfExtend(wholeItem, extendItem));
            } else {
                // 计划表中也没有,未适配
                extendModelNotAdaptList.add(getWholeShowInfoStrOfExtend(wholeItem, extendItem));
            }
        });
    }

    @Override
    public List<WholeAdaptStatusResp> getStatisticFromCpuFactory(String versionName) {
        List<WholeAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有CPU厂商列表
        List<String> factoryNames = adaptStatusMapper.queryCpuFactoryNames();
        // 根据厂商筛选，获得统计数据
        factoryNames.forEach(factoryNameItem -> {
            WholeAdaptStatusResp resp = new WholeAdaptStatusResp();
            resp.setCpuFactory(factoryNameItem);
            // 所有CPU厂商为 factoryNameItem 的整机
            List<WholeFactory> oneWholeFactoryList =
                    adaptStatusMapper.queryWholeByCpuFactory(factoryNameItem, versionName);
            statisticHandle(resp, oneWholeFactoryList, versionName);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<WholeAdaptStatusResp> getStatisticFromCpuModel(String versionName) {
        List<WholeAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有 CPU型号 列表
        List<String> modelNames = adaptStatusMapper.queryCpuModelNames();
        // 2、根据 CPU型号 筛选，获得统计数据
        modelNames.forEach(cpuModelItem -> {
            WholeAdaptStatusResp resp = new WholeAdaptStatusResp();
            resp.setCpuModel(cpuModelItem);
            // 所有 CPU型号 为 cpuModelItem 的整机
            List<WholeFactory> wholeFactories = adaptStatusMapper.queryWholeByCpuModel(cpuModelItem, versionName);
            statisticHandle(resp, wholeFactories, versionName);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<WholeAdaptStatusResp> getStatisticFromOsVersion() {
        List<WholeAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有操作系统名称（版本）列表
        List<String> versionNames = adaptStatusMapper.queryVersionName();
        // 根据操作系统名称筛选，获得统计数据
        versionNames.forEach(versionNameItem -> {
            WholeAdaptStatusResp resp = new WholeAdaptStatusResp();
            resp.setOsVersion(versionNameItem);
            // 所有操作系统名称为 versionNameItem 的整机
            List<WholeFactory> oneWholeFactoryList =
                    adaptStatusMapper.queryAllWholeMachineInWholeFactory(null, versionNameItem);
            statisticHandle(resp, oneWholeFactoryList, versionNameItem);
            resultList.add(resp);
        });
        return resultList;
    }

    @Override
    public List<WholeAdaptStatusResp> getStatisticFromOsArch(String versionName) {
        List<WholeAdaptStatusResp> resultList = new ArrayList<>();
        // 获取所有架构列表
        List<String> architectures = cpuFactoryMapper.queryArchitectureList();
        // 根据架构筛选，获得统计数据
        architectures.forEach(archItem -> {
            WholeAdaptStatusResp resp = new WholeAdaptStatusResp();
            resp.setArchitecture(archItem);
            // 所有架构为 cpuModelItem 的整机
            List<WholeFactory> oneWholeFactoryList =
                    adaptStatusMapper.queryAllWholeMachineInWholeFactory(archItem, versionName);
            statisticHandle(resp, oneWholeFactoryList, versionName);
            resultList.add(resp);
        });
        return resultList;
    }

    private String getWholePrefixStr(WholeFactory wholeItem) {
        return wholeItem.getWholeFactory() + " - " + wholeItem.getCpuFactory()
                + " - " + wholeItem.getCpuModel() + " - ";
    }

    private String getWholeShowInfoStrOfTypical(WholeFactory wholeItem) {
        return getWholePrefixStr(wholeItem) + wholeItem.getHardwareModel();
    }

    private String getWholeShowInfoStrOfExtend(WholeFactory wholeItem, String extendModel) {
        return getWholePrefixStr(wholeItem) + extendModel;
    }

    private List<String> getWholeShowInfoStrOfExtends(WholeFactory wholeItem) {
        String prefixStr = getWholePrefixStr(wholeItem);
        List<String> resultList = new ArrayList<>();
        StringFormatUtil.str2ListUtil(wholeItem.getExtendModel()).forEach(item -> resultList.add(prefixStr + item));
        return resultList;
    }
}
