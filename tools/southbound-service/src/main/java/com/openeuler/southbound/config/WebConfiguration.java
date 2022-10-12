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

package com.openeuler.southbound.config;

import com.openeuler.southbound.config.interceptor.TokenInterceptor;

import java.util.concurrent.Executors;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 *
 * @since : 2022-09-19
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final TokenInterceptor interceptor;

    /**
     * 构造方法
     *
     * @param tokenInterceptor tokenInterceptor
     */
    public WebConfiguration(TokenInterceptor tokenInterceptor) {
        this.interceptor = tokenInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**").allowCredentials(true)
                .allowedHeaders("*").allowedMethods("*").allowedOrigins("*");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configure) {
        configure.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configure.setDefaultTimeout(30000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 排除拦截，除了登录和判断是否第一次登录(此时还没token)，其他都拦截
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/user/login/**", "/**/user/adminStatus/**", "/**/user/addAdmin/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}