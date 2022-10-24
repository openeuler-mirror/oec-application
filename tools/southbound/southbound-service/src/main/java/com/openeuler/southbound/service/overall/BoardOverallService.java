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

package com.openeuler.southbound.service.overall;

import com.openeuler.southbound.model.overall.BoardOverall;

import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 板卡接口
 *
 * @since 2022-06-29
 */
@Service
public interface BoardOverallService {
    /**
     * 查询板卡
     *
     * @param boardOverall board对象
     * @return PageInfo
     */
    PageInfo queryAll(BoardOverall boardOverall);

    /**
     * 导出所有板卡数据
     *
     * @return list
     */
    List<BoardOverall> exportAllData();
}
