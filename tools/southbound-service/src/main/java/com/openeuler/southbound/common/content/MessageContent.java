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

package com.openeuler.southbound.common.content;

import com.openeuler.southbound.common.utils.I18NServer;

/**
 * 异常message
 *
 * @since 2022-08-25
 */
public class MessageContent {
    /**
     * 操作成功
     */
    public static final String SUCCESS_MSG = I18NServer.get("southbound_common_resp_success");

    /**
     * 系统异常
     */
    public static final String SYSTEM_EXC = I18NServer.get("southbound_system_exception");

    /**
     * 被除数不可以为零
     */
    public static final String BY_ZERO_EXC = I18NServer.get("southbound_by_zero_exception");

    /**
     * 缺少必要的请求参数
     */
    public static final String MISSING_PARAMETER_EXC = I18NServer.get("southbound_missing_parameter_exception");

    /**
     * 空指针异常
     */
    public static final String NULL_POINTER_EXC = I18NServer.get("southbound_null_pointer_exception");

    /**
     * SQL异常
     */
    public static final String SQL_EXC = I18NServer.get("southbound_sql_exception");

    /**
     * 当前数据已存在，请重新输入
     */
    public static final String SQL_CAN_NOT_REPEATED_EXC = I18NServer.get("southbound_data_can_not_repeated");

    /**
     * 请求参数异常
     */
    public static final String REQUEST_PARAMETER_EXC = I18NServer.get("southbound_request_parameter_exception");

    /**
     * 数组索引越界异常
     */
    public static final String OUT_OF_BOUNDS_EXC = I18NServer.get("southbound_out_of_bounds_exception");

    /**
     * 类型转换异常
     */
    public static final String CLASS_CAST_EXC = I18NServer.get("southbound_class_cast_exception");

    /**
     * 格式转换异常
     */
    public static final String NUMBER_FORMAT_EXC = I18NServer.get("southbound_number_format_exception");

    /**
     * 版本不支持
     */
    public static final String ADAPTATION_STATUS_NOT_SUPPORT =
            I18NServer.get("southbound_adaptation_status_not_support");

    /**
     * 已适配
     */
    public static final String ADAPTATION_STATUS_ALREADY = I18NServer.get("southbound_adaptation_status_already");

    /**
     * 适配中
     */
    public static final String ADAPTATION_STATUS_ING = I18NServer.get("southbound_adaptation_status_ing");

    /**
     * 未适配
     */
    public static final String ADAPTATION_STATUS_NOT = I18NServer.get("southbound_adaptation_status_not");

    /**
     * 操作成功
     */
    public static final String RESP_SUCCESS = I18NServer.get("southbound_common_resp_success");

    /**
     * 操作失败
     */
    public static final String RESP_FAIL = I18NServer.get("southbound_common_resp_fail");

    /**
     * 非法请求
     */
    public static final String SERVER_EXCEPTION = I18NServer.get("southbound_server_exception");

    /**
     * 请先删除关联数据
     */
    public static final String DATA_EXCEPTION = I18NServer.get("southbound_data_exception");

    /**
     * 表格数据有误，请重新确认！
     */
    public static final String FORM_DATA_IS_INCORRECT_EXC = I18NServer.get("southbound_form_data_is_incorrect");

    /**
     * 无当前工作表，请重新确认！
     */
    public static final String FORM_NO_CURRENT_WORKSHEET_EXC = I18NServer.get("southbound_form_no_current_worksheet");

    /**
     * CPU厂商
     */
    public static final String FORM_SHEET_NAME_CPU = I18NServer.get("southbound_form_sheet_name_cpu");
}
