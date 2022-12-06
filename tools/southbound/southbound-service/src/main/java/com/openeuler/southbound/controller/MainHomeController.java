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

package com.openeuler.southbound.controller;

import com.openeuler.southbound.model.plan.BoardPlan;
import com.openeuler.southbound.model.plan.WholePlan;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.service.MainHomeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/main-home")
@Slf4j
public class MainHomeController {
    @Autowired
    private MainHomeService mainHomeService;

    /**
     * 查询首页卡片数据
     *
     * @return 首页卡片数据
     */
    @GetMapping("/queryCardList")
    public ResponseBean queryCardList() {
        return ResponseBean.success(mainHomeService.queryCardList());
    }

    /**
     * 查询已适配机型数量
     *
     * @return 已适配机型列表
     */
    @GetMapping("/queryWholeModelSupportCount")
    public ResponseBean queryWholeModelSupportCount() {
        return ResponseBean.success(mainHomeService.queryWholeModelSupportCount());
    }

    /**
     * 查询整机对应机型
     *
     * @return 整机机型
     */
    @GetMapping("/queryWholeModels")
    public ResponseBean queryWholeModels() {
        return ResponseBean.success(mainHomeService.queryWholeModels());
    }

    /**
     * 查询已适配机型列表
     *
     * @return 已适配机型列表
     */
    @GetMapping("/queryWholeModelSupports")
    public ResponseBean queryWholeModelSupports() {
        return ResponseBean.success(mainHomeService.queryWholeModelSupports());
    }

    /**
     * 查询芯片厂商对应板卡型号适配数量
     *
     * @return 已适配机型列表
     */
    @GetMapping("/queryBoardModelSupportCount")
    public ResponseBean queryBoardModelSupportCount() {
        return ResponseBean.success(mainHomeService.queryBoardModelSupportCount());
    }

    /**
     * 查询芯片厂商对应板卡型号
     *
     * @return 芯片厂商对应板卡型号
     */
    @GetMapping("/queryBoardModels")
    public ResponseBean queryBoardModels() {
        return ResponseBean.success(mainHomeService.queryBoardModels());
    }

    /**
     * 查询已适配板卡型号
     *
     * @return 已适配板卡型号
     */
    @GetMapping("/queryBoardModelSupports")
    public ResponseBean queryBoardModelSupports() {
        return ResponseBean.success(mainHomeService.queryBoardModelSupports());
    }

    /**
     * 根据版本返回整机适配情况
     *
     * @param wholePlan 整机计划实体类
     * @return 整机机型
     */
    @GetMapping("/queryVersionWholeDetail")
    public ResponseBean queryVersionWholeDetail(WholePlan wholePlan) {
        return ResponseBean.success(mainHomeService.queryVersionWholeDetail(wholePlan));
    }

    /**
     * 根据版本返回板卡适配情况
     *
     * @param boardPlan 板卡计划实体类
     * @return 板卡机型
     */
    @GetMapping("/queryVersionBoardDetail")
    public ResponseBean queryVersionBoardDetail(BoardPlan boardPlan) {
        return ResponseBean.success(mainHomeService.queryVersionBoardDetail(boardPlan));
    }
}
