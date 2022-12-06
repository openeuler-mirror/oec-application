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

package com.openeuler.southbound.service.overall.impl;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.mapper.overall.WholeOverallMapper;
import com.openeuler.southbound.mapper.factory.CpuFactoryMapper;
import com.openeuler.southbound.model.overall.WholeOverall;
import com.openeuler.southbound.service.overall.WholeOverallService;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.NumberFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 整机services
 *
 * @since 2022-06-28
 */
@Service
public class WholeOverallServiceImpl implements WholeOverallService {
    @Autowired
    private WholeOverallMapper wholeOverallMapper;

    @Autowired
    private CpuFactoryMapper cpuFactoryMapper;

    /**
     * 查询整机列表
     *
     * @param wholeOverallMachine 条件查询参数对象
     * @return PageInfo
     */
    @Override
    public PageInfo queryAll(WholeOverall wholeOverallMachine) {
        Page page = PageHelper.startPage(wholeOverallMachine.getPageIndex(), wholeOverallMachine.getPageSize());
        List<WholeOverall> wholeOverallList = wholeOverallMapper.queryAll(wholeOverallMachine);
        queryWholeStatus(wholeOverallList);
        PageInfo pageInfo = new PageInfo(wholeOverallList);
        pageInfo.setPageNum(page.getPageNum());
        pageInfo.setPageSize(page.getPageSize());
        return pageInfo;
    }

    /**
     * 导出所有整机数据
     *
     * @return ResponseBean
     */
    @Override
    public List<WholeOverall> exportAllData() {
        List<WholeOverall> wholeOverallList = wholeOverallMapper.exportAllData();
        queryWholeStatus(wholeOverallList);
        return wholeOverallList;
    }

    private void queryWholeStatus(List<WholeOverall> wholeOverallList) {
        wholeOverallList.forEach(item -> {
            item.setExtendModelCount(StringUtils.isEmpty(item.getExtendModel())
                    ? 0 : item.getExtendModel().split(",").length);
            // 典型机型适配状态
            // 查看版本是否支持
            int supportCount = cpuFactoryMapper.selectSupportVersion(item);
            if (supportCount < 1) {
                // 通过CPU代次和操作系统在社区中未查到时
                item.setHardwareModelStatus(MessageContent.ADAPTATION_STATUS_NOT_SUPPORT);
            } else {
                // 查询典型机型适配状态
                int statusCount = wholeOverallMapper.queryHardwareModelStatus(item);
                if (statusCount > 0) {
                    // 在社区中查到
                    item.setHardwareModelStatus(MessageContent.ADAPTATION_STATUS_ALREADY);
                } else {
                    int planCount = wholeOverallMapper.queryHardwareModelStatusForPlan(item);
                    if (planCount > 0) {
                        // 在社区中未查到，在计划中查到
                        item.setHardwareModelStatus(MessageContent.ADAPTATION_STATUS_ING);
                    } else {
                        // 在社区中未查到，在计划未查到
                        item.setHardwareModelStatus(MessageContent.ADAPTATION_STATUS_NOT);
                    }
                }
            }
            // 扩展机型适配状态
            int extendModelCount = wholeOverallMapper.queryExtendModelStatus(item);
            if (item.getExtendModelCount() < 1 || extendModelCount < 1) {
                item.setExtendModelStatus("0%");
            } else {
                item.setExtendModelStatus(NumberFormat.getPercentInstance()
                        .format((double) extendModelCount / item.getExtendModelCount()));
            }
        });
    }
}




