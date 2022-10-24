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

package com.openeuler.southbound.mapper.overall;

import com.openeuler.southbound.model.overall.WholeOverall;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 整机表的操作接口
 *
 * @since 2022-06-29
 */
@Mapper
public interface WholeOverallMapper {
    /**
     * 查询整机列表
     *
     * @param wholeOverall 条件查询参数对象
     * @return List<WholeOverall>
     */
    List<WholeOverall> queryAll(WholeOverall wholeOverall);

    /**
     * 查询典型适配状态
     *
     * @param wholeOverall 整机对象
     * @return int
     */
    int queryHardwareModelStatus(WholeOverall wholeOverall);

    /**
     * 查询扩展适配状态
     *
     * @param wholeOverall 整机对象
     * @return int
     */
    int queryExtendModelStatus(WholeOverall wholeOverall);

    /**
     * 计划中查询典型适配状态
     *
     * @param wholeOverall 整机对象
     * @return int
     */
    int queryHardwareModelStatusForPlan(WholeOverall wholeOverall);

    /**
     * 导出所有整机数据
     *
     * @return ResponseBean
     */
    List<WholeOverall> exportAllData();
}
