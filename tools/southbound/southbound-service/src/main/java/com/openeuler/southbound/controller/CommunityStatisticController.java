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

package com.openeuler.southbound.controller;

import com.alibaba.fastjson.JSONObject;
import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.common.enums.ResCode;
import com.openeuler.southbound.common.utils.I18NServer;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.SouthBoundUser;
import com.openeuler.southbound.service.UserService;
import com.openeuler.southbound.service.impl.CommunityBoardDataServiceImpl;
import com.openeuler.southbound.service.impl.CommunityWholeDataServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社区兼容性清单统计数据 Controller
 *
 * @since 2022-9-22
 */
@RestController
@RequestMapping("/community-data")
@Slf4j
public class CommunityStatisticController {
    @Autowired
    private CommunityWholeDataServiceImpl communityWholeDataService;
    @Autowired
    private CommunityBoardDataServiceImpl communityBoardDataService;
    @Autowired
    private UserService userService;

    /**
     * 从整机统计兼容性清单中认证日期查询
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List
     */
    @GetMapping("/whole")
    public ResponseBean statisticByWhole(String startTime, String endTime) {
        JSONObject jsonObject = communityWholeDataService.queryData(startTime, endTime);
        if (jsonObject != null) {
            return ResponseBean.success(jsonObject);
        }
        return ResponseBean.success(MessageContent.SUCCESS_QUERY);
    }

    /**
     * 从板卡统计兼容性清单中认证日期查询
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List
     */
    @GetMapping("/board")
    public ResponseBean statisticByBoard(String startTime, String endTime) {
        JSONObject jsonObject = communityBoardDataService.queryData(startTime, endTime);
        if (jsonObject != null) {
            return ResponseBean.success(jsonObject);
        }
        return ResponseBean.success(MessageContent.SUCCESS_QUERY);
    }

    /**
     * 同步社区数据
     *
     * @param username username
     * @param password password
     * @return 执行结果
     */
    @GetMapping("/sync")
    public ResponseBean syncCommunityData(String username, String password) {
        ResponseBean responseBean = new ResponseBean<>();
        // 校验用户是否存在
        SouthBoundUser user = userService.findByUserName(username);
        if (user == null) {
            responseBean = ResponseBean.error(ResCode.HTTP_401_UNAUTHORIZED.value(),
                    I18NServer.get("southbound_user_undefined"));
        } else {
            // 检验用户密码是否正确
            if (!user.getPassword().equals(password)) {
                responseBean = ResponseBean.error(ResCode.HTTP_403_FORBIDDEN.value(),
                        I18NServer.get("southbound_user_error_password"));
            } else {
                communityWholeDataService.syncDataFromCommunity();
                communityBoardDataService.syncDataFromCommunity();
                responseBean = ResponseBean.success(MessageContent.RESP_SUCCESS);
            }
        }
        return responseBean;
    }
}


