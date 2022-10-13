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

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.common.enums.ResCode;

import com.openeuler.southbound.model.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截处理
 *
 * @since 2022-08-25
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String DATA_CAN_NOT_REPEATED = "SQLIntegrityConstraintViolationException";

    /**
     * 系统异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseBean globalExceptionHandler(Exception ex) {
        log.error("Execution failed because of system exception.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.SYSTEM_EXC, ex.getClass().getName());
    }

    /**
     * 运行时异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler(SouthBoundRuntimeException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseBean resourceNotFoundException(SouthBoundRuntimeException ex) {
        log.error("Execution failed because of runtime exception.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_404_NOT_FOUND.value(), "Not Found", ex.getClass().getName());
    }

    /**
     * 被除数为零异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResponseBean byZeroExceptionHandler(Exception ex) {
        log.error("Execution failed because of divided by zero.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.BY_ZERO_EXC, ex.getClass().getName());
    }

    /**
     * 缺失参数异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseBean missingParameterExceptionHandler(Exception ex) {
        log.error("Execution failed because of missing parameter.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.MISSING_PARAMETER_EXC, ex.getClass().getName());
    }

    /**
     * 空指针异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseBean nullPointerExceptionHandler(Exception ex) {
        log.error("Execution failed because of null pointer exception.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.NULL_POINTER_EXC, ex.getClass().getName());
    }

    /**
     * sql语法异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({BadSqlGrammarException.class})
    public ResponseBean badSqlGrammarExceptionHandler(Exception ex) {
        log.error("Execution failed because of bad sql grammar exceptions.");
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(), MessageContent.SYSTEM_EXC);
    }

    /**
     * 数组越界异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    public ResponseBean outOfBoundsExceptionHandler(Exception ex) {
        log.error("Execution failed because of out of bound of array.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.OUT_OF_BOUNDS_EXC, ex.getClass().getName());
    }

    /**
     * 类型转换异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({ClassCastException.class})
    public ResponseBean classCastExceptionHandler(Exception ex) {
        log.error("Execution failed because of classCastException.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.CLASS_CAST_EXC, ex.getClass().getName());
    }

    /**
     * 格式转换异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({NumberFormatException.class})
    public ResponseBean numberFormatExceptionHandler(Exception ex) {
        log.error("Execution failed because of abnormal format conversion.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.NUMBER_FORMAT_EXC, ex.getClass().getName());
    }

    /**
     * 非法请求
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseBean httpRequestMethodNotSupportedException(Exception ex) {
        log.error("Execution failed because of illegal request.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_400_BAD_REQUEST.value(),
                MessageContent.SERVER_EXCEPTION, ex.getClass().getName());
    }

    /**
     * 请求参数异常
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({DuplicateKeyException.class, MyBatisSystemException.class})
    public ResponseBean duplicateKeyException(Exception ex) {
        log.error("Execution failed because of request parameters abnormal.", ex.getMessage());
        if (ex.getMessage().contains(DATA_CAN_NOT_REPEATED)) {
            return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                    MessageContent.SQL_CAN_NOT_REPEATED_EXC, ex.getClass().getName());
        }
        return new ResponseBean(ResCode.HTTP_400_BAD_REQUEST.value(),
                MessageContent.REQUEST_PARAMETER_EXC, ex.getClass().getName());
    }

    /**
     * 请先删除关联数据
     *
     * @param ex ex
     * @return ResponseBean
     */
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseBean dataIntegrityViolationException(Exception ex) {
        log.error("Execution failed because of data correlation relationship.", ex.getMessage());
        return new ResponseBean(ResCode.HTTP_500_SERVER_ERROR.value(),
                MessageContent.DATA_EXCEPTION, ex.getClass().getName());
    }
}
