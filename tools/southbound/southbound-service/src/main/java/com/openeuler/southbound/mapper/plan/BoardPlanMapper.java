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

package com.openeuler.southbound.mapper.plan;

import com.openeuler.southbound.model.BoardQueryBean;
import com.openeuler.southbound.model.factory.BoardFactory;
import com.openeuler.southbound.model.plan.BoardPlan;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 板卡计划-mapper
 *
 * @since 2022-09-01
 */
@Mapper
public interface BoardPlanMapper {
    /**
     * 按条件查询板卡计划
     *
     * @param boardPlan boardPlan
     * @return list
     */
    List<BoardPlan> queryAll(BoardPlan boardPlan);

    /**
     * 添加板卡计划
     *
     * @param boardPlan boardPlan
     * @return int
     */
    int insert(BoardPlan boardPlan);

    /**
     * 修改板卡计划
     *
     * @param boardPlan boardPlan
     * @return int
     */
    int update(BoardPlan boardPlan);

    /**
     * 删除板卡计划
     *
     * @param ids 板卡ids
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 查询x86板卡型号适配数量
     *
     * @param chipFactory 芯片厂商
     * @param chipModel   芯片型号
     * @param modelList   板卡型号
     * @param versionName 操作系统
     * @return list
     */
    List<Integer> queryModelListCountByX86(String chipFactory, String chipModel, String modelList, String versionName);

    /**
     * 查询arm板卡型号适配数量
     *
     * @param chipFactory 芯片厂商
     * @param chipModel   芯片型号
     * @param modelList   板卡型号
     * @param versionName 操作系统
     * @return list
     */
    List<Integer> queryModelListCountByArm64(
            String chipFactory, String chipModel, String modelList, String versionName);

    /**
     * 查询板卡型号
     *
     * @param boardQueryBean boardQueryBean
     * @return list
     */
    List<BoardFactory> queryModelTables(BoardQueryBean boardQueryBean);


    /**
     * 根据版本名查询Beta阶段机型
     *
     * @param versionName versionName
     * @return 机型List
     */
    List<String> queryBetaModelList(String versionName);

    /**
     * 根据版本名查询Release阶段机型
     *
     * @param versionName versionName
     * @return 机型List
     */
    List<String> queryReleaseModelList(String versionName);

    /**
     * 查询板卡机型-版本
     *
     * @param boardPlan boardPlan
     * @return list
     */
    List<BoardPlan> queryVersionModelList(BoardPlan boardPlan);

    /**
     * 导出板卡计划
     *
     * @return list
     */
    List<BoardPlan> exportAllData();
}
