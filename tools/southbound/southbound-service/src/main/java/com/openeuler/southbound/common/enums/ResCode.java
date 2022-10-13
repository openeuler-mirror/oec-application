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

package com.openeuler.southbound.common.enums;

/**
 * http请求响应状态枚举
 *
 * @since 2022-06-27
 */
public enum ResCode {
    HTTP_200_OK("200"),
    HTTP_400_BAD_REQUEST("400"),
    HTTP_401_UNAUTHORIZED("401"),
    HTTP_403_FORBIDDEN("403"),
    HTTP_404_NOT_FOUND("404"),
    HTTP_406_NOT_ACCEPTABLE("406"),
    HTTP_409_CONFLICT("409"),
    HTTP_412_PRECONDITION_FAILED("412"),
    HTTP_417_EXPECTATION_FAILED("417"),
    HTTP_423_LOCKED("423"),
    HTTP_500_SERVER_ERROR("500"),
    HTTP_502_SERVERERROR("502");

    private final String value;

    /**
     * 获取字符串类型值
     *
     * @param value value
     */
    ResCode(String value) {
        this.value = value;
    }

    /**
     * 获取字符串类型值
     *
     * @return 返回字符串类型值
     */
    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * 通过延伸信息value获取HttpStatus类的一个枚举实例
     *
     * @param value 状态值
     * @return 返回HttpStatus
     */
    public static ResCode getHttpStatusByValue(String value) {
        for (ResCode status : ResCode.values()) {
            if (status.value().equals(value)) {
                return status;
            }
        }
        return ResCode.HTTP_200_OK;
    }
}