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

import com.openeuler.southbound.model.factory.BoardFactory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 板卡适配状态 Mapper
 *
 * @since 2022-8-29
 */
@Mapper
public interface BoardAdaptStatusMapper {
    /**
     * 查询所有芯片厂商
     *
     * @return 芯片厂商列表
     */
    List<String> selectAllChipFactory();

    /**
     * 通过芯片厂商查询板卡厂商
     *
     * @param chipFactory 芯片厂商
     * @param versionName versionName
     * @return 整机厂商列表
     */
    List<BoardFactory> selectByChipFactory(String chipFactory, String versionName);

    /**
     * 查询所有芯片型号
     *
     * @return 芯片型号列表
     */
    List<String> selectAllChipModels();

    /**
     * 查询 芯片型号 对应的板卡
     *
     * @param chipModel   芯片型号
     * @param versionName versionName
     * @return 板卡厂商列表
     */
    List<BoardFactory> selectByChipModel(String chipModel, String versionName);

    /**
     * 查询所有板卡类型
     *
     * @return 板卡类型列表
     */
    List<String> selectAllBoardType();

    /**
     * 通过板卡类型查询板卡厂商
     *
     * @param boardType   板卡类型
     * @param versionName versionName
     * @return 板卡厂商列表
     */
    List<BoardFactory> selectByBoardType(String boardType, String versionName);

    /**
     * 查询所有 操作系统名称（操作系统版本）
     *
     * @return 版本名称列表
     */
    List<String> selectAllVersionName();

    /**
     * 查询  所有 板卡
     *
     * @param versionName versionName
     * @return 板卡厂商列表
     */
    List<BoardFactory> selectAllBoardFromBoardFactory(String versionName);

    /**
     * 查询当前芯片是否支持该版本
     *
     * @param chipFactory 芯片厂商
     * @param chipModel   芯片型号
     * @param versionName 操作系统
     * @return int
     */
    int selectOsSupportOfChip(
            @Param("chipFactory") String chipFactory, @Param("chipModel") String chipModel,
            @Param("versionName") String versionName);

    /**
     * 查询指定的整机【典型机型】在计划表中匹配的对象
     *
     * @param boardFactory 板卡厂商
     * @return int
     */
    int selectBoardStatusFromPlan(BoardFactory boardFactory);

    /**
     * 根据传来的整机数据，在社区数据库中查询匹配的整机的数量
     * 不区分 典型机型/扩展机型
     *
     * @param boardFactory 板卡厂商
     * @return 匹配数量
     */
    int selectBoardStatusFromCommunity(BoardFactory boardFactory);
}
