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

package com.openeuler.southbound.model.overall;

import com.openeuler.southbound.model.BaseRequestBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 板卡全景的实体类
 *
 * @since 2022-06-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardOverall extends BaseRequestBean {
    private Integer id;
    private String chipFactory;             // 芯片厂商
    private String boardType;               // 板卡类型
    private String chipModel;               // 芯片型号
    private String typicalBoardModel;       // 典型板卡型号
    private Integer extendBoardModelCount;  // 扩展板卡型号数量
    private String extendBoardModel;        // 扩展板卡型号
    private String versionId;               // 操作系统id
    private String versionName;             // 操作系统
    private String createTime;              // 时间
    private String typicalBoardArm;         // arm典型机型适配状态
    private String typicalBoardX86;         // x86典型机型适配状态
    private String extendBoardArm;          // arm扩展机型适配状态
    private String extendBoardX86;          // x86扩展机型适配状态

    /**
     * 架构：仅为查询使用，与数据库结构无关
     */
    private String architecture;
}

