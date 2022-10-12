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

package com.openeuler.southbound.schedule;

import com.openeuler.southbound.service.impl.CommunityBoardDataServiceImpl;
import com.openeuler.southbound.service.impl.CommunityWholeDataServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 从社区兼容性平台获取数据-定期执行任务
 *
 * @since 2022-8-26
 */
@Component
@Slf4j
public class SyncDataTask {
    @Autowired
    private CommunityWholeDataServiceImpl wholeCommService;
    @Autowired
    private CommunityBoardDataServiceImpl boardCommService;

    /**
     * 定期执行任务-从社区获取数据，保存到本地数据库
     * cron表达式，： [秒] [分] [小时] [日] [月] [周] [年]；
     * 每天2点执行一次
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void execute() {
        log.info("schedule task is start running");
        wholeCommService.syncDataFromCommunity();
        boardCommService.syncDataFromCommunity();
        log.info("schedule task is finish running");
    }
}
