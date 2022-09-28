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

package com.openeuler.southbound.model.factory;

import com.openeuler.southbound.model.BaseRequestBean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 驱动管理Model
 *
 * @since 2022-8-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverManage extends BaseRequestBean {
    private int driverId;
    private String chipFactory;                 // 芯片厂商
    private String driverName;                  // 驱动名称
    private String kernelDriverPublish;         // 内核驱动发布
    private String kernelDriverVersion;         // 内核驱动版本
    private String exteriorDriverPublish;       // 外部驱动发布
    private String exteriorDriverPublishTime;   // 外部驱动发布日期
    private String exteriorDriverVersion;       // 外部驱动版本
    private String webDriverUrl;                // 官网驱动链接
    private String allDriverUrl;                // 软件所驱动链接
    private String createTime;                  // 创建日期
    private String versionIds;
    private String versionNames;
}
