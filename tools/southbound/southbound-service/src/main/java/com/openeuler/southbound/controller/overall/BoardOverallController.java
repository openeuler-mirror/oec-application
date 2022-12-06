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
import com.openeuler.southbound.model.overall.BoardOverall;
import com.openeuler.southbound.service.overall.BoardOverallService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 全景管理-板卡
 *
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/board")
@Slf4j
public class BoardOverallController {
    @Autowired
    private BoardOverallService boardOverallService;

    /**
     * 查询板卡
     *
     * @param boardOverall board对象
     * @return ResponseBean
     */
    @GetMapping("/queryAll")
    @Log(operation = "Query BoardOverAll", detail = "Query all board-overall data.")
    public ResponseBean queryBoard(BoardOverall boardOverall) {
        return ResponseBean.success(boardOverallService.queryAll(boardOverall));
    }

    /**
     * 导出所有板卡数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export BoardOverall", detail = "Export all board-overall data.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(boardOverallService.exportAllData());
    }
}
