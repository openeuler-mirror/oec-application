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

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import com.openeuler.southbound.model.BaseRequestBean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 整机厂商Model
 *
 * @since 2022-8-29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WholeFactory extends BaseRequestBean {
    private int wholeId;
    private String wholeFactory;
    private String cpuFactory;
    private String cpuModel;
    private String architecture;
    private String hardwareModel;
    private String extendModel;
    private String interfacePerson;
    private String contact;
    private String middleman;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    private String modelStatus;
    private String versionId;
    private String versionName;
}
