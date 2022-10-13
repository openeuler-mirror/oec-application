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

package com.openeuler.southbound.exception;

/**
 * 自定义南向看板运行时异常
 *
 * @since 2022-8-25
 */
public class SouthBoundRuntimeException extends RuntimeException {
    private final Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message 异常信息
     * @param code 状态码
     */
    public SouthBoundRuntimeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResourceNotFoundException{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }
}
