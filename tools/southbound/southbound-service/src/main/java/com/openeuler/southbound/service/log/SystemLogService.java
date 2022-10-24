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

package com.openeuler.southbound.service.log;

import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.model.BaseRequestBean;
import com.openeuler.southbound.model.log.BusinessLog;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 * 系统日志 service
 *
 * @since 2022-9-27
 */
@Service
public interface SystemLogService {
    /**
     * 插入数据库
     *
     * @param log 日志对象
     */
    void insert(BusinessLog log);

    /**
     * 查询所有符合条件的日志
     *
     * @param requestBean 带查询的关键字
     * @return 查询结果list
     */
    PageInfo queryAllLog(BaseRequestBean requestBean);

    /**
     * 将匹配的日志列表导出为excel
     *
     * @param requestBean 关键字
     * @return SXSSFWorkbook
     */
    SXSSFWorkbook exportExcel(BaseRequestBean requestBean);
}
