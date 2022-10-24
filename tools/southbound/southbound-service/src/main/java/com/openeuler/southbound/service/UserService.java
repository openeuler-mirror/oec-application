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

package com.openeuler.southbound.service;

import com.openeuler.southbound.model.SouthBoundUser;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

/**
 * 用户管理-service
 *
 * @since 2022-8-26
 */
public interface UserService {
    /**
     * 判断是否第一次登录系统
     *
     * @return 管理员用户状态
     */
    JSONObject getAdminStatus();

    /**
     * 根据用户名查找用户
     *
     * @param username username
     * @return SouthBoundUser
     */
    SouthBoundUser findByUserName(String username);

    /**
     * 查询所有用户数据
     *
     * @param southBoundUser southBoundUser
     * @return 所有用户数据
     */
    PageInfo queryAll(SouthBoundUser southBoundUser);

    /**
     * 添加用户
     *
     * @param user user
     * @return 添加结果
     */
    int addUser(SouthBoundUser user);

    /**
     * 修改用户
     *
     * @param user user
     * @return 修改结果
     */
    int updateUser(SouthBoundUser user);

    /**
     * 批量删除用户
     *
     * @param ids id
     * @return 删除结果
     */
    int deleteUserByIds(String ids);

    /**
     * 用户修改密码
     *
     * @param username    username
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return 用户修改密码结果
     */
    int password(String username, String oldPassword, String newPassword);

    /**
     * 设置超级管理员密码
     *
     * @param password password
     * @return addAdmin
     */
    int addAdmin(String password);
}
