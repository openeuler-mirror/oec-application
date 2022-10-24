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

package com.openeuler.southbound.service.impl;

import com.openeuler.southbound.mapper.UserMapper;
import com.openeuler.southbound.model.SouthBoundUser;
import com.openeuler.southbound.service.UserService;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理-service 实现类
 *
 * @since 2022-8-26
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 判断是否管理员第一次登陆
     *
     * @return 管理员用户状态
     */
    @Override
    public JSONObject getAdminStatus() {
        JSONObject jsonObject = new JSONObject();
        int adminStatus = userMapper.getAdminStatus();
        if (adminStatus > 0) {
            jsonObject.put("isFirstLogin", false);
            jsonObject.put("status", 0);
        } else {
            jsonObject.put("isFirstLogin", true);
            jsonObject.put("status", 1);
        }
        return jsonObject;
    }

    /**
     * 添加管理员用户
     *
     * @param password 管理员设置密码
     * @return 添加管理员用户是否成功
     */
    @Override
    public int addAdmin(String password) {
        return userMapper.addAdmin(password);
    }

    /**
     * 查询所有用户数据
     *
     * @param southBoundUser southBoundUser
     * @return 所有用户数据
     */
    @Override
    public PageInfo queryAll(SouthBoundUser southBoundUser) {
        Page page = PageHelper.startPage(southBoundUser.getPageIndex(), southBoundUser.getPageSize());
        List<SouthBoundUser> userList = userMapper.queryAll(southBoundUser);
        PageInfo pageInfo = new PageInfo(userList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    /**
     * 根据用户名查找用户
     *
     * @param username username
     * @return SouthBoundUser
     */
    @Override
    public SouthBoundUser findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    /**
     * 添加用户
     *
     * @param user user
     * @return 添加结果
     */
    @Override
    public int addUser(SouthBoundUser user) {
        return userMapper.addUser(user);
    }

    /**
     * 修改用户
     *
     * @param user user
     * @return 修改结果
     */
    @Override
    public int updateUser(SouthBoundUser user) {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户
     *
     * @param ids id
     * @return 删除结果
     */
    @Override
    public int deleteUserByIds(String ids) {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 用户修改密码
     *
     * @param username    username
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return 用户修改密码结果
     */
    @Override
    public int password(String username, String oldPassword, String newPassword) {
        return userMapper.password(username, oldPassword, newPassword);
    }
}
