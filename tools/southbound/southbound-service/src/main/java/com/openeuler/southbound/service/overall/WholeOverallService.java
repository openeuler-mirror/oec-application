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

import com.openeuler.southbound.model.overall.WholeOverall;

import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 整机接口
 *
 * @since 2022-06-29
 */
@Service
public interface WholeOverallService {
    /**
     * 查询整机列表
     *
     * @param wholeOverall 条件查询参数对象
     * @return PageInfo
     */
    PageInfo queryAll(WholeOverall wholeOverall);

    /**
     * 导出所有整机数据
     *
     * @return ResponseBean
     */
    List<WholeOverall> exportAllData();
}


