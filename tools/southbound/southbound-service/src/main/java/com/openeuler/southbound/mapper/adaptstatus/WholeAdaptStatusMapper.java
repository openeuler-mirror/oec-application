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

package com.openeuler.southbound.mapper.adaptstatus;

import com.openeuler.southbound.model.factory.WholeFactory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 整机适配状态 Mapper
 *
 * @since 2022-8-29
 */
@Mapper
public interface WholeAdaptStatusMapper {
    /**
     * 查询整机厂商列表
     *
     * @return 整机厂商列表
     */
    List<String> queryWholeFactoryNames();

    /**
     * 查询整机厂商列表
     *
     * @param wholeFactory 整机厂商
     * @param versionName  versionName
     * @return 整机厂商列表
     */
    List<WholeFactory> queryWholeByFactory(String wholeFactory, String versionName);

    /**
     * 查询指定的整机【典型机型】在计划表中匹配的对象
     *
     * @param hardwareFactory 整机厂商
     * @param hardwareModel   整机型号（不区分典型，扩展）
     * @param cpuFactory      cpu厂商
     * @param cpuModel        cpu型号
     * @param versionName     版本名称
     * @return 匹配的整机厂商列表
     */
    int queryWholeMachineModelStatusFromPlan(
            @Param("hardwareFactory") String hardwareFactory, @Param("hardwareModel") String hardwareModel,
            @Param("cpuFactory") String cpuFactory, @Param("cpuModel") String cpuModel,
            @Param("versionName") String versionName);

    /**
     * 根据传来的整机数据，在社区数据库中查询匹配的整机的数量
     * 不区分 典型机型/扩展机型
     *
     * @param hardwareFactory 整机厂商
     * @param hardwareModel   整机型号（不区分典型，扩展）
     * @param architecture    架构
     * @param cpuModel        cpu型号
     * @param versionName     版本名称
     * @return 匹配数量
     */
    int queryWholeMachineModelStatusFromCommunity(
            @Param("hardwareFactory") String hardwareFactory, @Param("hardwareModel") String hardwareModel,
            @Param("architecture") String architecture, @Param("cpuModel") String cpuModel,
            @Param("versionName") String versionName);

    /**
     * 查询整机版本是否支持
     *
     * @param cpuFactory  cpu 厂商
     * @param cpuModel    cpu 型号
     * @param versionName 版本名称
     * @return 匹配的行数
     */
    int queryCpuSupportOfCpuFactory(
            @Param("cpuFactory") String cpuFactory, @Param("cpuModel") String cpuModel,
            @Param("versionName") String versionName);

    /**
     * 查询所有CPU厂商
     *
     * @return CPU厂商列表
     */
    List<String> queryCpuFactoryNames();

    /**
     * 查询 cpu厂商对应的整机
     *
     * @param cpuFactory  cpu厂商
     * @param versionName versionName
     * @return 整机列表
     */
    List<WholeFactory> queryWholeByCpuFactory(String cpuFactory, String versionName);

    /**
     * 查询所有CPU型号
     *
     * @return CPU型号列表
     */
    List<String> queryCpuModelNames();

    /**
     * 查询 CPU型号对应的整机
     *
     * @param cpuModel    CPU型号
     * @param versionName versionName
     * @return 整机列表
     */
    List<WholeFactory> queryWholeByCpuModel(String cpuModel, String versionName);

    /**
     * 查询所有 操作系统名称（操作系统版本）
     *
     * @return 操作系统列表
     */
    List<String> queryVersionName();

    /**
     * 查询所有的整机
     *
     * @param architecture architecture
     * @param versionName  versionName
     * @return 整机列表
     */
    List<WholeFactory> queryAllWholeMachineInWholeFactory(String architecture, String versionName);
}
