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

package com.openeuler.southbound.config.interceptor;

import com.openeuler.southbound.common.enums.ResCode;
import com.openeuler.southbound.common.utils.I18NServer;
import com.openeuler.southbound.common.utils.TokenCacheUtil;
import com.openeuler.southbound.common.utils.TokenUtil;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 *
 * @since : 2022-09-06
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws IOException {
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String token = request.getHeader("token");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String, String> result = new HashMap<>();
        if (token != null) {
            if (TokenCacheUtil.isCacheEmpty()) {
                // token认证失败
                result.put("msg", I18NServer.get("southbound_user_error_provided"));
                result.put("code", ResCode.HTTP_401_UNAUTHORIZED.value());
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(JSON.toJSONString(result).getBytes());
                return false;
            }
            if (!TokenCacheUtil.hasCache(token)) {
                // 您的账号在另一处地点登录，您已被迫下线
                result.put("msg", I18NServer.get("southbound_user_error_verify_logout"));
                result.put("code", ResCode.HTTP_401_UNAUTHORIZED.value());
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(JSON.toJSONString(result).getBytes());
                return false;
            }
            if (TokenUtil.verify(token)) {
                return true;
            }
        } else {
            result.put("msg", I18NServer.get("southbound_user_error_provided"));
        }
        result.put("code", ResCode.HTTP_401_UNAUTHORIZED.value());
        result.put("msg", I18NServer.get("southbound_user_error_provided"));
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSON.toJSONString(result).getBytes());
        return false;
    }
}

