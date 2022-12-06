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

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 社区兼容性数据包装类-整机-service
 *
 * @since 2022-8-26
 */
@Service
public interface CommunityDataService<T> {
    /**
     * 查询【本地数据库】中所有整机的社区兼容性数据
     *
     * @return list
     */
    List<T> queryAllDataFromDataBase();

    /**
     * 从社区获取数据，同步到本地数据库
     */
    void syncDataFromCommunity();

    /**
     * 根据时间查询数据
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 数组
     */
    JSONObject queryData(String startTime, String endTime);
}
