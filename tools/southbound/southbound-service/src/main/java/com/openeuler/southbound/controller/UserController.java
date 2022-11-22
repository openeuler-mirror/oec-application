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
import com.openeuler.southbound.common.utils.Sha256Util;
import com.openeuler.southbound.common.utils.TokenCacheUtil;
import com.openeuler.southbound.common.utils.TokenUtil;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.SouthBoundUser;
import com.openeuler.southbound.service.UserService;

import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户_API
 *
 * @since 2022-09-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 判断是否第一次登录系统
     *
     * @return adminStatus
     */
    @GetMapping("/adminStatus")
    public ResponseBean getAdminStatus() {
        return ResponseBean.success(userService.getAdminStatus());
    }

    /**
     * 设置超级管理员密码
     *
     * @param password password
     * @return addAdmin
     */
    @PostMapping("/addAdmin")
    @Log(operation = "Admin First Login", detail = "Admin first login and set password.")
    public ResponseBean addAdmin(String password) {
        String decryptPwd = Sha256Util.getSHA256StrJava(password);
        return ResponseBean.success(userService.addAdmin(decryptPwd));
    }

    /**
     * 登录
     *
     * @param username username
     * @param password password
     * @return ResponseBean
     */
    @PostMapping("/login")
    @Log(operation = "Login", detail = "User login.")
    public ResponseBean userLogin(String username, String password) {
        ResponseBean responseBean;
        // 校验用户是否存在
        SouthBoundUser user = userService.findByUserName(username);
        if (user == null) {
            responseBean = ResponseBean.error(ResCode.HTTP_401_UNAUTHORIZED.value(),
                    I18NServer.get("southbound_user_undefined"));
        } else {
            // 检验用户密码是否正确
            if (user.getPassword().equals(Sha256Util.getSHA256StrJava(password))) {
                // 登录成功返回用户详情及token信息
                JSONObject data = new JSONObject();
                String token = TokenUtil.sign(user);
                data.put("userInfo", user);
                data.put("token", token);
                // 根据最新的token通过用户名相同删除之前的token
                TokenCacheUtil.existByUserName(token);
                // 将token放到缓存的数据之中
                TokenCacheUtil.setTokenCache(token, new Date().getTime());
                responseBean = ResponseBean.success(data);
            } else {
                responseBean = ResponseBean.error(ResCode.HTTP_403_FORBIDDEN.value(),
                        I18NServer.get("southbound_user_error_password"));
            }
        }
        return responseBean;
    }

    /**
     * 退出登录
     *
     * @param token token
     * @return 退出注销登录
     */
    @GetMapping(value = "/logout")
    @Log(operation = "LogOut", detail = "User logout.")
    public ResponseBean logout(@RequestHeader String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseBean.error(I18NServer.get("southbound_user_error_logout_token"));
        }
        // 清除指定的缓存
        TokenCacheUtil.clearOnly(token);
        return ResponseBean.success(I18NServer.get("southbound_user_success_logout"));
    }

    /**
     * 用户修改密码
     *
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return 用户修改密码结果
     */
    @PutMapping("/password")
    @Log(operation = "Password", detail = "User Change Password.")
    public ResponseBean password(String oldPassword, String newPassword) {
        String token = Objects.requireNonNull(TokenUtil.getRequest()).getHeader("token");
        String username = TokenUtil.getUserNameByToken(token);
        String encoderOldPwd = Sha256Util.getSHA256StrJava(oldPassword);
        String encoderNewPwd = Sha256Util.getSHA256StrJava(newPassword);
        // 先校验用户密码是否正确
        SouthBoundUser user = userService.findByUserName(username);
        if (!user.getPassword().equals(encoderOldPwd)) {
            return ResponseBean.error(I18NServer.get("southbound_user_error_verify_password"));
        }
        int updateCount = userService.password(username, encoderOldPwd, encoderNewPwd);
        if (updateCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }


    /**
     * 查询全部用户
     *
     * @param southBoundUser 用户实体类
     * @return 查询结果
     */
    @GetMapping("/queryAll")
    public ResponseBean getAllUser(SouthBoundUser southBoundUser) {
        return ResponseBean.success(userService.queryAll(southBoundUser));
    }

    /**
     * 添加用户
     *
     * @param user user
     * @return 添加结果
     */
    @PostMapping("/add")
    @Log(operation = "User Add", detail = "Admin Add add user.")
    public ResponseBean add(@RequestBody SouthBoundUser user) {
        String decryptPwd = Sha256Util.getSHA256StrJava(user.getPassword());
        user.setPassword(decryptPwd);
        int addCount = userService.addUser(user);
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改用户
     *
     * @param user user
     * @return 修改结果
     */
    @PutMapping("/update")
    @Log(operation = "User Update", detail = "Admin update user.")
    public ResponseBean update(@RequestBody SouthBoundUser user) {
        String encoderOldPwd = Sha256Util.getSHA256StrJava(user.getPassword());
        user.setPassword(encoderOldPwd);
        int updateCount = userService.updateUser(user);
        if (updateCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 批量删除用户
     *
     * @param ids id
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Log(operation = "User Delete", detail = "Admin delete user.")
    public ResponseBean deleteByIds(String ids) {
        int deleteCount = userService.deleteUserByIds(ids);
        if (deleteCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }
}
