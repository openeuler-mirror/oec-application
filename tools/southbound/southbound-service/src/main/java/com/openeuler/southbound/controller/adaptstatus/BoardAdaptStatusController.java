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

package com.openeuler.southbound.controller.adaptstatus;

import com.openeuler.southbound.common.enums.ResCode;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.service.adaptstatus.BoardAdaptStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 板卡适配状态Controller
 *
 * @since 2022-8-29
 */
@RestController
@RequestMapping("/board-status")
public class BoardAdaptStatusController {
    @Autowired
    private BoardAdaptStatusService boardAdaptStatusService;

    /**
     * 芯片厂商维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/chip-factory")
    @Log(operation = "Query Board Adaptation",
            detail = "Query the adaptation status according to the server-factory.")
    public ResponseBean chipFactory(String versionName) {
        if (versionName == null) {
            return ResponseBean.error(ResCode.HTTP_400_BAD_REQUEST.value(), "versionName is null!");
        }
        return ResponseBean.success(boardAdaptStatusService.getStatisticFromChipFactory(versionName));
    }

    /**
     * 芯片型号维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/chip-model")
    @Log(operation = "Query Board Adaptation",
            detail = "Query the adaptation status according to the chip-model.")
    public ResponseBean cpuFactory(String versionName) {
        return ResponseBean.success(boardAdaptStatusService.getStatisticFromChipModel(versionName));
    }

    /**
     * 板卡类型维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/board-type")
    @Log(operation = "Query Board Adaptation",
            detail = "Query the adaptation status according to the board-type.")
    public ResponseBean cpuModel(String versionName) {
        return ResponseBean.success(boardAdaptStatusService.getStatisticFromBoardType(versionName));
    }

    /**
     * 操作系统版本维度
     *
     * @return ResponseBean
     */
    @GetMapping("/os-version")
    @Log(operation = "Query Board Adaptation",
            detail = "Query the adaptation status according to the os-version.")
    public ResponseBean osVersion() {
        return ResponseBean.success(boardAdaptStatusService.getStatisticFromOsVersion());
    }

    /**
     * 操作系统架构维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/architecture")
    @Log(operation = "Query Board Adaptation",
            detail = "Query the adaptation status according to the architecture.")
    public ResponseBean architecture(String versionName) {
        return ResponseBean.success(boardAdaptStatusService.getStatisticFromOsArch(versionName));
    }
}
