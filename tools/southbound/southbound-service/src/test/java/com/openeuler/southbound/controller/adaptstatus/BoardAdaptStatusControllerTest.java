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

package com.openeuler.southbound.controller.adaptstatus;

import com.openeuler.southbound.SouthBoundApplication;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * 板卡状态 controller UT类
 *
 * @since 2022-08-25
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
public class BoardAdaptStatusControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void chipFactory() throws Exception {
        test("/board-status/chip-factory");
    }

    @Test
    void cpuFactory() throws Exception {
        test("/board-status/chip-model");
    }

    @Test
    void cpuModel() throws Exception {
        test("/board-status/board-type");
    }

    @Test
    void osVersion() throws Exception {
        test("/board-status/os-version");
    }

    @Test
    void architecture() throws Exception {
        test("/board-status/architecture");
    }

    void test(String uri) throws Exception {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        // 请求参数
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // 请求URI，controller中接口上写明的mapping，不加配置的“server.servlet.context-path”

        headers.add("head", "");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).headers(headers).params(params)).andReturn();
        // 获取执行结果的状态
        int status = result.getResponse().getStatus();
        Assert.assertEquals("错误", 200, status);
    }
}