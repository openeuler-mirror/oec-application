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

package com.openeuler.southbound.service.log.impl;

import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.SouthBoundApplication;
import com.openeuler.southbound.common.utils.DateFormatUtil;
import com.openeuler.southbound.model.BaseRequestBean;
import com.openeuler.southbound.model.log.BusinessLog;

import java.util.Date;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 业务日志UT
 *
 * @since 2020-9-6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SouthBoundApplication.class)
public class BusinessLogServiceImplTest {
    @Autowired
    private BusinessLogServiceImpl businessLogService;

    @Test
    public void insertOperationLog() {
        BusinessLog logBean = new BusinessLog();
        logBean.setOperation("Operation");
        logBean.setUserName("setModule");
        logBean.setDetail("jsonObject.toJSONString()");
        logBean.setHost("request.getRemoteAddr()");
        logBean.setDateTime(DateFormatUtil.dateTimeFormat(new Date(System.currentTimeMillis())));
        logBean.setResult("request.getHeader(\"user - agent\")");
        businessLogService.insert(logBean);
    }

    @Test
    public void queryOperationLog() {
        BaseRequestBean requestBean = new BaseRequestBean();
        requestBean.setPageIndex(1);
        requestBean.setPageSize(10);
        PageInfo pageInfo = businessLogService.queryAllLog(requestBean);
        Assert.assertNotNull(pageInfo);
    }

    @Test
    public void exportExcel() {
        BaseRequestBean requestBean = new BaseRequestBean();
        requestBean.setPageIndex(1);
        requestBean.setPageSize(10);
        SXSSFWorkbook sheets = businessLogService.exportExcel(requestBean);
        Assert.assertNotNull(sheets);
    }
}