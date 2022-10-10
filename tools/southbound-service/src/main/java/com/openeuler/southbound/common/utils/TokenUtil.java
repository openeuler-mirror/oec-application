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

package com.openeuler.southbound.common.utils;

import com.openeuler.southbound.model.SouthBoundUser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token工具类
 *
 * @since 2022-06-27
 */
@Slf4j
public class TokenUtil {
    private static final long EXPIRE_TIME = 1 * 30 * 60 * 1000;
    private static final String TOKEN_SECRET = "southbound-dashboard";  // 密钥盐

    /**
     * 签名生成
     *
     * @param user user
     * @return token
     */
    public static String sign(SouthBoundUser user) {
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("username", user.getUsername())
                .withClaim("userid", user.getId())
                .withExpiresAt(expiresAt)
                // 使用了HMAC256加密算法。
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    /**
     * 签名验证
     *
     * @param token token
     * @return 验证结果
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 获取request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        Object requestObj = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = null;
        if (requestObj instanceof ServletRequestAttributes) {
            requestAttributes = (ServletRequestAttributes) requestObj;
        }
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        return request;
    }

    /**
     * getUser
     *
     * @param token token
     * @return String
     */
    public static String getUser(String token) {
        String username = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT jwt = verifier.verify(token);
            username = jwt.getClaim("username").asString();
        } catch (TokenExpiredException t) {
            log.error("Get user name failed", t.getMessage());
        }
        return username;
    }
}
