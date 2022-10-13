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

package com.openeuler.southbound.model.plan;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import com.openeuler.southbound.model.BaseRequestBean;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 板卡计划Model
 *
 * @since 2022-8-29
 */
@Data
public class BoardPlan extends BaseRequestBean {
    Integer id;
    String chipFactory;
    String driverName;
    String driverVersion;
    String architecture;
    String betaList;
    String releaseList;
    String remark;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    private String versionId;
    private String versionName;

    private JSONObject jsonBetaList;
    private JSONObject jsonReleaseList;
}
