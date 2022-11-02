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

package com.openeuler.southbound.config.aop;

import com.openeuler.southbound.common.utils.DateFormatUtil;
import com.openeuler.southbound.common.utils.TokenUtil;
import com.openeuler.southbound.model.log.BusinessLog;
import com.openeuler.southbound.service.log.impl.BusinessLogServiceImpl;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面
 *
 * @since 2022-06-27
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private BusinessLogServiceImpl businessLogService;
    private BusinessLog businessLog;

    /**
     * 表示匹配带有自定义注解的方法
     */
    @Pointcut("@annotation(com.openeuler.southbound.config.aop.Log)")
    public void pointcut() {
        log.info("Method pointcut");
    }

    /**
     * 在方法执行后执行，根据用户请求参数创建要保存到数据库的日志对象
     *
     * @param joinPoint 切点
     */
    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        businessLog = getRequestLogBean(joinPoint);
    }

    /**
     * 方法执行成功，日志插入
     *
     * @param result 执行结果
     */
    @AfterReturning(returning = "result", pointcut = "pointcut()")
    public void doAfterReturning(Object result) {
        log.info("Method executed successfully");
        businessLog.setResult("1");
        businessLogService.insert(businessLog);
    }

    /**
     * 方法执行出现异常，日志插入
     *
     * @param throwable 异常信息
     */
    @AfterThrowing(throwing = "throwable", pointcut = "pointcut()")
    public void doAfterThrowing(Throwable throwable) {
        log.error("Method executed failed: ", throwable.getMessage());
        businessLog.setResult("0");
        businessLogService.insert(businessLog);
    }

    private BusinessLog getRequestLogBean(JoinPoint point) {
        Object signatureObj = point.getSignature();
        MethodSignature signature = null;
        if (signatureObj instanceof MethodSignature) {
            signature = (MethodSignature) signatureObj;
        }
        Method method = signature.getMethod();
        BusinessLog business = new BusinessLog();
        Log userAction = method.getAnnotation(Log.class);
        if (userAction != null) {
            // 注解上的描述
            business.setDetail(userAction.detail());
            business.setOperation(userAction.operation());
        }
        business.setDateTime(DateFormatUtil.dateTimeFormat(new Date(System.currentTimeMillis())));
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.error("requestAttributes is null, cannot get request");
            return business;
        }
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        Object requestObj = requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        if (!(requestObj instanceof HttpServletRequest)) {
            return business;
        }
        HttpServletRequest request = (HttpServletRequest) requestObj;
        business.setHost(request.getRemoteAddr());
        if (!request.getHeader("token").equals("null")) {
            business.setUserName(TokenUtil.getUserNameByToken(request.getHeader("token")));
        }
        return business;
    }
}
