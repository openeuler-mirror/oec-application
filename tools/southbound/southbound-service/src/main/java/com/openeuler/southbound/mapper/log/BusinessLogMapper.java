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

package com.openeuler.southbound.mapper.log;

import com.openeuler.southbound.model.BaseRequestBean;
import com.openeuler.southbound.model.log.BusinessLog;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务日志mapper
 *
 * @since 2022-9-29
 */
@Mapper
public interface BusinessLogMapper {
    /**
     * 插入日志
     *
     * @param operationLog 插入对象
     * @return 结果
     */
    int insert(BusinessLog operationLog);

    /**
     * 根据参数查询所有日志
     *
     * @param requestBean 关键字
     * @return 查询列表
     */
    List<BusinessLog> queryAll(BaseRequestBean requestBean);
}