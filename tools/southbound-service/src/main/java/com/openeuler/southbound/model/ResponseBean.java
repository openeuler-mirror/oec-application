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

package com.openeuler.southbound.model;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.common.enums.ResCode;

import lombok.Data;

/**
 * Response Bean
 *
 * @since 2022-8-26
 */
@Data
public class ResponseBean<T> {
    private String code;
    private String msg;
    private T data;

    /**
     * 有参构造
     *
     * @param data data
     */
    public ResponseBean(T data) {
        super();
        this.data = data;
    }

    /**
     * 有参构造
     *
     * @param code data
     * @param msg  msg
     * @param data data
     */
    public ResponseBean(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 有参构造
     *
     * @param code code
     * @param msg  msg
     */
    public ResponseBean(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

    /**
     * 无参构造
     */
    public ResponseBean() {
    }

    /**
     * 返回错误状态信息
     *
     * @param msg 错误提示语
     * @param <T> 泛型
     * @return 泛型
     */
    public static <T> ResponseBean<T> error(String msg) {
        return new ResponseBean<>(ResCode.HTTP_400_BAD_REQUEST.value(), msg);
    }

    /**
     * 返回错误状态信息
     *
     * @param code 状态码
     * @param msg  错误提示语
     * @param <T>  泛型
     * @return 泛型
     */
    public static <T> ResponseBean<T> error(String code, String msg) {
        return new ResponseBean<>(code, msg);
    }

    /**
     * 返回正常状态信息
     *
     * @param data 数据
     * @param <T>  泛型
     * @return 泛型
     */
    public static <T> ResponseBean<T> success(T data) {
        ResponseBean<T> response = new ResponseBean<>();
        response.setData(data);
        response.setCode(ResCode.HTTP_200_OK.value());
        response.setMsg(MessageContent.SUCCESS_MSG);
        return response;
    }
}
