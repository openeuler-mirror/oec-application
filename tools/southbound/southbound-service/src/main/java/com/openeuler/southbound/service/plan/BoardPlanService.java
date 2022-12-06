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

package com.openeuler.southbound.service.plan;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.model.BoardQueryBean;
import com.openeuler.southbound.model.plan.BoardPlan;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 板卡计划service
 *
 * @since 2022-8-29
 */
@Service
public interface BoardPlanService {
    /**
     * 查询全部
     *
     * @param boardPlan 板卡计划
     * @return 查询结果
     */
    PageInfo queryAll(BoardPlan boardPlan);

    /**
     * 新增
     *
     * @param boardPlan 板卡计划
     * @return 增加结果
     */
    int add(BoardPlan boardPlan);

    /**
     * 更新
     *
     * @param boardPlan 板卡计划
     * @return 修改结果
     */
    int update(BoardPlan boardPlan);

    /**
     * 删除
     *
     * @param ids 数组
     * @return 删除结果
     */
    int deleteByIds(String ids);

    /**
     * 查询模型表
     *
     * @param boardQueryBean 板卡
     * @return 查询结果
     */
    JSONObject queryModelTables(BoardQueryBean boardQueryBean);

    /**
     * 查询模型表
     *
     * @param versionName 版本名称
     * @return 查询结果
     */
    JSONObject queryModelByVersion(String versionName);

    /**
     * 查询版本列表
     *
     * @param boardFactory 板卡厂商
     * @return 查询结果
     */
    List<Map<Integer, String>> queryVersionListByChipFactory(String boardFactory);

    /**
     * 导出
     *
     * @return 导出
     */
    List<BoardPlan> exportAllData();
}
