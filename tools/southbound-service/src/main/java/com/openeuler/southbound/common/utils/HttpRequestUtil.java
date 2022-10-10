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

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 通过外部请求获取数据
 *
 * @since 2022-06-27
 */
@Slf4j
public class HttpRequestUtil {
    /**
     * 发送HttpPost请求
     *
     * @param url       url
     * @param paramJson paramJson
     * @return 请求结果
     */
    public static String sendPost(String url, String paramJson) {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        StringEntity entity = new StringEntity(paramJson, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        // 设置请求体
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httppost);
        } catch (IOException e) {
            log.error("Failed to execute https request.");
        }
        HttpEntity entity1 = response.getEntity();
        String result = "";
        try {
            result = EntityUtils.toString(entity1);
        } catch (ParseException | IOException e) {
            log.error("HttpEntity to string failed.");
        }
        return result;
    }
}


