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

import com.openeuler.southbound.model.community.CommunityWholeMachineBean;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 社区兼容性数据包装类-整机-mapper
 *
 * @since 2022-8-26
 */
@Mapper
public interface CommunityWholeMachineMapper {
    /**
     * 批量插入数据
     *
     * @param lists 待插入的数据
     * @return 插入行数
     */
    int insertBatch(List<CommunityWholeMachineBean> lists);

    /**
     * 批量删除所有数据
     *
     * @return 删除行数
     */
    int deleteBatchAll();

    /**
     * 查询本地数据库所有数据
     *
     * @return list
     */
    List<CommunityWholeMachineBean> selectAll();

    /**
     * 查询认证时间数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List
     */
    CommunityWholeMachineBean selectAllByData(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
