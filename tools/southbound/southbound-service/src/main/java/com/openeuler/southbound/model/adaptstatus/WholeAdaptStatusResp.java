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

package com.openeuler.southbound.model.adaptstatus;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 整机适配状态返回对象
 *
 * @since 2022-8-29
 */
@Data
@NoArgsConstructor
public class WholeAdaptStatusResp {
    /**
     * 整机厂家
     */
    private String wholeFactory;

    /**
     * CPU厂家
     */
    private String cpuFactory;

    /**
     * CPU型号维度
     */
    private String cpuModel;

    /**
     * 操作系统版本维度
     */
    private String osVersion;

    /**
     * 操作系统架构维度
     */
    private String architecture;

    /**
     * 典型机型 已适配列表
     */
    private List<String> typicalModelAdaptList;

    /**
     * 典型机型 适配中列表
     */
    private List<String> typicalModelPlanAdaptList;

    /**
     * 典型机型 未适配列表
     */
    private List<String> typicalModelNotAdaptList;

    /**
     * cpu 与 操作系统版本 不支持的 典型机型和扩展机型
     */
    private List<String> modelNotSupList;

    /**
     * 扩展机型 已适配的列表
     */
    private List<String> extendModelAdaptList;

    /**
     * 扩展机型 适配中列表
     */
    private List<String> extendModelPlanAdaptList;

    /**
     * 扩展机型 未适配的列表
     */
    private List<String> extendModelNotAdaptList;

    /**
     * 有参构造
     *
     * @param wholeFactory wholeFactory
     */
    public WholeAdaptStatusResp(String wholeFactory) {
        this.wholeFactory = wholeFactory;
    }
}
