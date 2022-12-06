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
 * CPU厂商Model
 *
 * @since 2022-8-26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpuFactory extends BaseRequestBean {
    private int cpuId;
    private String cpuFactory;
    private String cpuModel;
    private String architecture;
    private String releaseTime;
    private String createTime;
    private String versionIds;
    private String versionNames;
}
