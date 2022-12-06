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

import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.adaptstatus.WholeAdaptStatusResp;
import com.openeuler.southbound.service.adaptstatus.WholeAdaptStatusService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 整机适配状态Controller
 *
 * @since 2022-8-29
 */
@RestController
@RequestMapping("/whole-status")
public class WholeAdaptStatusController {
    @Autowired
    private WholeAdaptStatusService wholeAdaptStatusService;

    /**
     * 从【整机厂商维度】查询所有整机的适配状态：已适配，适配中，未适配
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/whole-factory")
    @Log(operation = "Query WholeMachine Adaptation",
            detail = "Query the adaptation status according to the server-manufacturer.")
    public ResponseBean wholeFactory(String versionName) {
        List<WholeAdaptStatusResp> statisticFromWholeFactory =
                wholeAdaptStatusService.getStatisticFromWholeFactory(versionName);
        return ResponseBean.success(statisticFromWholeFactory);
    }

    /**
     * CPU厂商维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/cpu-factory")
    @Log(operation = "Query WholeMachine Adaptation",
            detail = "Query the adaptation status according to the cpu-manufacturer.")
    public ResponseBean cpuFactory(String versionName) {
        return ResponseBean.success(wholeAdaptStatusService.getStatisticFromCpuFactory(versionName));
    }

    /**
     * CPU型号维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/cpu-model")
    @Log(operation = "Query WholeMachine Adaptation",
            detail = "Query the adaptation status according to the cpu-model.")
    public ResponseBean cpuModel(String versionName) {
        return ResponseBean.success(wholeAdaptStatusService.getStatisticFromCpuModel(versionName));
    }

    /**
     * 操作系统版本维度
     *
     * @return ResponseBean
     */
    @GetMapping("/os-version")
    @Log(operation = "Query WholeMachine Adaptation",
            detail = "Query the adaptation status according to the os-version.")
    public ResponseBean osVersion() {
        return ResponseBean.success(wholeAdaptStatusService.getStatisticFromOsVersion());
    }

    /**
     * 操作系统架构维度
     *
     * @param versionName 版本名称
     * @return ResponseBean
     */
    @GetMapping("/architecture")
    @Log(operation = "Query WholeMachine Adaptation",
            detail = "Query the adaptation status according to the architecture.")
    public ResponseBean architecture(String versionName) {
        return ResponseBean.success(wholeAdaptStatusService.getStatisticFromOsArch(versionName));
    }
}