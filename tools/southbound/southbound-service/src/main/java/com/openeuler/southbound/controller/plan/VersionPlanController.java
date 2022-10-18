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
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.plan.VersionPlan;
import com.openeuler.southbound.service.plan.VersionPlanService;
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
@RequestMapping("/version-plan")
@Slf4j
public class VersionPlanController {
    @Autowired
    private VersionPlanService releasePlanService;

    /**
     * 查询所有版本计划
     *
     * @param releasePlan 版本计划实体类
     * @return ResponseBean ResponseBean
     */
    @GetMapping("/queryAll")
    @Log(operation = "query versionPlan", detail = "query all data of version plan")
    public ResponseBean queryAll(VersionPlan releasePlan) {
        return ResponseBean.success(releasePlanService.queryAll(releasePlan));
    }

    /**
     * 新增版本计划
     *
     * @param releasePlan 版本计划实体类
     * @return ResponseBean ResponseBean
     */
    @PostMapping("/add")
    @Log(operation = "add versionPlan", detail = "add data of version plan")
    public ResponseBean add(@RequestBody VersionPlan releasePlan) {
        int addCount = releasePlanService.add(releasePlan);
        if (addCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 修改版本计划
     *
     * @param releasePlan 版本计划实体类
     * @return ResponseBean ResponseBean
     */
    @PutMapping("/update")
    @Log(operation = "update versionPlan", detail = "update data of version plan")
    public ResponseBean update(@RequestBody VersionPlan releasePlan) {
        int updateCount = releasePlanService.update(releasePlan);
        if (updateCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 删除版本计划
     *
     * @param versionId 版本计划id
     * @return ResponseBean ResponseBean
     */
    @DeleteMapping("/delete")
    @Log(operation = "delete versionPlan", detail = "delete data of version plan")
    public ResponseBean deleteById(String versionId) {
        int deleteCount = releasePlanService.deleteById(versionId);
        if (deleteCount > 0) {
            return ResponseBean.success(MessageContent.RESP_SUCCESS);
        }
        return ResponseBean.error(MessageContent.RESP_FAIL);
    }

    /**
     * 查询版本计划列表
     *
     * @return ResponseBean ResponseBean
     */
    @GetMapping("/queryVersionList")
    public ResponseBean queryVersionList() {
        return ResponseBean.success(releasePlanService.queryVersionList());
    }
}
