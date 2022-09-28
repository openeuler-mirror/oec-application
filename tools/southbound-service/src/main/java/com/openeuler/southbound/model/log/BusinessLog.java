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

package com.openeuler.southbound.model.log;

import lombok.Data;

/**
 * 业务日志bean
 *
 * @since 2022-9-6
 */
@Data
public class BusinessLog {
    int id;

    // 操作时登录用户名
    String userName;

    // 操作名称
    String operation;

    // 操作结果
    String result;

    // 操作主机ip
    String host;

    // 操作时间
    String dateTime;

    // 操作详情
    String detail;
}
