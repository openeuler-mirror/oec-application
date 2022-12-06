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

package com.openeuler.southbound.controller.overall;

import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.overall.WholeOverall;
import com.openeuler.southbound.service.overall.WholeOverallService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 整机对外接口（API）
 *
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/whole-machine")
@Slf4j
public class WholeOverallController {
    @Autowired
    private WholeOverallService wholeOverallService;

    /**
     * 查询整机列表
     *
     * @param wholeOverall 条件查询参数对象
     * @return ResponseBean
     */
    @GetMapping("/queryAll")
    @Log(operation = "Query WholeOverAll", detail = "Query all whole-overall data.")
    public ResponseBean queryAll(WholeOverall wholeOverall) {
        return ResponseBean.success(wholeOverallService.queryAll(wholeOverall));
    }

    /**
     * 导出所有整机数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export WholeOverAll", detail = "Export all whole-overall data.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(wholeOverallService.exportAllData());
    }
}