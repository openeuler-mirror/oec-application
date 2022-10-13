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

package com.openeuler.southbound.model.overall;

import com.openeuler.southbound.model.BaseRequestBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 整机的实体类
 *
 * @since 2022-06-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WholeOverall extends BaseRequestBean {
    private Integer id;
    private String hardwareFactory;     // 整机厂商/硬件厂商
    private String cpuFactory;          // CPU厂商
    private String cpuModel;            // CPU型号
    private String hardwareModel;       // 典型机型/硬件型号
    private Integer extendModelCount;   // 扩展机型数量
    private String extendModel;         // 扩展机型型号
    private String versionId;           // 操作系统id
    private String versionName;         // 操作系统
    private String architecture;        // 架构
    private String createTime;          // 创建日期
    private String hardwareModelStatus; // 典型机型适配状态
    private String extendModelStatus;   // 扩展机型适配状态
}
