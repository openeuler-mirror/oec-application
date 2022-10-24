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

import com.openeuler.southbound.model.overall.BoardOverall;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 板卡表的操作接口
 *
 * @since 2022-06-29
 */
@Mapper
public interface BoardOverallMapper {
    /**
     * 查询板卡
     *
     * @param boardOverall boardOverall
     * @return List<Board>
     */
    List<BoardOverall> queryAll(BoardOverall boardOverall);

    /**
     * 查询典型X86适配状态
     *
     * @param boardOverall boardOverall
     * @return int
     */
    int queryTypicalModelStatusX86(BoardOverall boardOverall);

    /**
     * 查询典型arm适配状态
     *
     * @param boardOverall boardOverall
     * @return int
     */
    int queryTypicalModelStatusArm(BoardOverall boardOverall);

    /**
     * 计划中查询典型X86适配状态
     *
     * @param boardOverall boardOverall
     * @return int
     */
    int queryTypicalModelStatusX86FromPlan(BoardOverall boardOverall);

    /**
     * 计划中查询典型arm适配状态
     *
     * @param boardOverall boardOverall
     * @return int
     */
    int queryTypicalModelStatusArmFromPlan(BoardOverall boardOverall);

    /**
     * 查询扩展X86适配状态
     *
     * @param boardOverall    boardOverall
     * @param extendModelList extendModelList
     * @return int
     */
    int queryExtendModelStatusX86(BoardOverall boardOverall, List<String> extendModelList);

    /**
     * 查询扩展ARM适配状态
     *
     * @param boardOverall    boardOverall
     * @param extendModelList extendModelList
     * @return int
     */
    int queryExtendModelStatusArm(BoardOverall boardOverall, List<String> extendModelList);

    /**
     * 导出所有板卡数据
     *
     * @return list
     */
    List<BoardOverall> exportAllData();
}
