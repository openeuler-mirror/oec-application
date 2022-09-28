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

/**
 * 整机适配状态返回对象
 *
 * @since 2022-8-29
 */
@Data
public class BoardAdaptStatusResp {
    /**
     * 芯片厂商
     */
    String chipFactory;

    /**
     * 芯片型号
     */
    String chipModel;

    /**
     * 板卡类型
     */
    String boardType;

    /**
     * 操作系统版本
     */
    String osVersion;

    /**
     * 操作系统架构
     */
    String osArch;

    /**
     * 不支持的板卡型号
     */
    List<String> modelNotSupList;

    /**
     * 典型板卡 已适配列表
     */
    List<String> typicalModelAdaptList;

    /**
     * 典型板卡 适配中列表
     */
    List<String> typicalModelPlanAdaptList;

    /**
     * 典型板卡 未适配列表
     */
    List<String> typicalModelNotAdaptList;

    /**
     * 扩展板卡 已适配的列表
     */
    List<String> extendModelAdaptList;

    /**
     * 扩展板卡 适配中列表
     */
    List<String> extendModelPlanAdaptList;

    /**
     * 扩展板卡 未适配的列表
     */
    List<String> extendModelNotAdaptList;
}
