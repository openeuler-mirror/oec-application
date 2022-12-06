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

package com.openeuler.southbound.controller.plan;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.config.aop.Log;
import com.openeuler.southbound.model.BoardQueryBean;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.plan.BoardPlan;
import com.openeuler.southbound.service.plan.BoardPlanService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 板卡计划_API
 *
 * @since 2022-08-25
 */
@RestController
@RequestMapping("/board-plan")
@Slf4j
public class BoardPlanController {
    @Autowired
    private BoardPlanService boardPlanService;

    /**
     * 查询所有板卡计划
     *
     * @param boardPlan 板卡计划实体类
     * @return ResponseBean
     */
    @GetMapping("/queryAll")
    @Log(operation = "Query BoardPlan", detail = "Query all data of board-plan.")
    public ResponseBean queryAll(BoardPlan boardPlan) {
        return ResponseBean.success(boardPlanService.queryAll(boardPlan));
    }

    /**
     * 新增板卡计划
     *
     * @param boardPlan 板卡计划实体类
     * @return ResponseBean
     */
    @PostMapping("/add")
    @Log(operation = "Add BoardPlan", detail = "Add data of board-plan.")
    public ResponseBean add(@RequestBody BoardPlan boardPlan) {
        int addCount = boardPlanService.add(boardPlan);
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改板卡计划
     *
     * @param boardPlan 板卡计划实体类
     * @return ResponseBean
     */
    @PutMapping("/update")
    @Log(operation = "Update BoardPlan", detail = "Update data of board-plan.")
    public ResponseBean update(@RequestBody BoardPlan boardPlan) {
        int updateCount = boardPlanService.update(boardPlan);
        if (updateCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 删除板卡计划
     *
     * @param ids 根据id删除板卡计划
     * @return ResponseBean
     */
    @DeleteMapping("/delete")
    @Log(operation = "Delete BoardPlan", detail = "Delete data of board-plan.")
    public ResponseBean batchDeleteByIds(String ids) {
        int deleteCount = boardPlanService.deleteByIds(ids);
        if (deleteCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询机型适配情况列表
     *
     * @param boardQueryBean boardQueryBean
     * @return ResponseBean
     */
    @GetMapping("/queryModelTables")
    public ResponseBean queryModelTables(BoardQueryBean boardQueryBean) {
        return ResponseBean.success(boardPlanService.queryModelTables(boardQueryBean));
    }

    /**
     * 根据版本查询板卡计划适配情况
     *
     * @param versionName versionName
     * @return ResponseBean
     */
    @GetMapping("/queryVersionModel")
    public ResponseBean queryModelByVersion(String versionName) {
        return ResponseBean.success(boardPlanService.queryModelByVersion(versionName));
    }

    /**
     * 根据芯片厂商名称查询支持的版本
     *
     * @param chipFactory 芯片厂商
     * @return ResponseBean
     */
    @GetMapping("/queryVersionListByChipFactory")
    public ResponseBean queryVersionListByChipFactory(String chipFactory) {
        return ResponseBean.success(boardPlanService.queryVersionListByChipFactory(chipFactory));
    }

    /**
     * 导出所有板卡计划数据
     *
     * @return ResponseBean
     */
    @GetMapping("/export")
    @Log(operation = "Export BoardPlan", detail = "Export all data of board-plan.")
    public ResponseBean exportAllData() {
        return ResponseBean.success(boardPlanService.exportAllData());
    }
}
