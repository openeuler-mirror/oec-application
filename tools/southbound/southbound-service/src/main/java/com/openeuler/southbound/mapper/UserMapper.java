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

package com.openeuler.southbound.mapper;

import com.openeuler.southbound.model.SouthBoundUser;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户管理-mapper
 *
 * @since 2022-8-26
 */
@Mapper
public interface UserMapper {
    /**
     * 判断是否管理员第一次登陆
     *
     * @return 管理员是否第一次登陆
     */
    int getAdminStatus();

    /**
     * 查询所有用户数据
     *
     * @param southBoundUser southBoundUser
     * @return 所有用户数据
     */
    List<SouthBoundUser> queryAll(SouthBoundUser southBoundUser);

    /**
     * 根据用户名查找用户
     *
     * @param username username
     * @return SouthBoundUser
     */
    SouthBoundUser findByUserName(String username);

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
     * @param username username
     * @param oldPassword oldPassword
     * @param newPassword newPassword
     * @return 用户修改密码结果
     */
    int password(String username, String oldPassword, String newPassword);

    /**
     * 添加管理员用户
     *
     * @param password 管理员设置密码
     * @return 添加管理员用户是否成功
     */
    int addAdmin(String password);
}
