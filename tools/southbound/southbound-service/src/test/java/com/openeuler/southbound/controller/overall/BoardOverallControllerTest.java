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

package com.openeuler.southbound.controller.overall;

import com.alibaba.fastjson.JSONObject;
import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.model.overall.WholeOverall;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 全景板卡 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
class BoardOverallControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void queryBoard() throws Exception {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 请求参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        String uri = "/board-machine/queryAll";
        headers.add("head", "");
        WholeOverall wholeOverall = new WholeOverall();
        // 添加参数
        params.add("pageIndex", "1");
        params.add("pageSize", "10");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).headers(headers).params(params)).andReturn();
        // 获取执行结果的状态
        int status = result.getResponse().getStatus();
        Assert.assertEquals(200, status);
    }

    @Test
    void exportAllData() throws Exception {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 请求参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        // 请求URI，controller中接口上写明的mapping，不加配置的“server.servlet.context-path”
        String uri = "/board-machine/export";
        headers.add("head", "");
        // 添加参数
        JSONObject paramJsObj = new JSONObject();
        paramJsObj.put("boardId", "1");
        paramJsObj.put("hardwareFactory", "超聚变");
        paramJsObj.put("cpuFactory", "dddd");
        paramJsObj.put("versionName", "dddd");
        paramJsObj.put("cpuModel", "dddd");
        paramJsObj.put("hardwareModel", "dddd");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).headers(headers)
                .contentType(MediaType.APPLICATION_JSON).content(paramJsObj.toJSONString())
        ).andReturn();
        // 获取执行结果的状态
        int status = result.getResponse().getStatus();
        Assert.assertEquals("错误", 200, status);
    }
}