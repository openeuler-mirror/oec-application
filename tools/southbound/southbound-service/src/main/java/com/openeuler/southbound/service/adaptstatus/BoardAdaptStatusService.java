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

package com.openeuler.southbound.service.adaptstatus;

import com.openeuler.southbound.model.adaptstatus.BoardAdaptStatusResp;

import java.util.List;

/**
 * 板卡适配状态Service
 *
 * @since 2022-8-29
 */
public interface BoardAdaptStatusService {
    /**
     * 芯片厂商维度-查询所有 板卡的适配状态
     *
     * @param versionName 版本名称
     * @return List<BoardAdaptStatusResp>
     */
    List<BoardAdaptStatusResp> getStatisticFromChipFactory(String versionName);

    /**
     * 芯片型号维度
     *
     * @param versionName 版本名称
     * @return List<BoardAdaptStatusResp>
     */
    List<BoardAdaptStatusResp> getStatisticFromChipModel(String versionName);

    /**
     * 板卡类型
     *
     * @param versionName 版本名称
     * @return List<BoardAdaptStatusResp>
     */
    List<BoardAdaptStatusResp> getStatisticFromBoardType(String versionName);

    /**
     * 操作系统版本维度
     *
     * @return List<BoardAdaptStatusResp>
     */
    List<BoardAdaptStatusResp> getStatisticFromOsVersion();

    /**
     * 操作系统架构维度
     *
     * @param versionName 版本名称
     * @return List<BoardAdaptStatusResp>
     */
    List<BoardAdaptStatusResp> getStatisticFromOsArch(String versionName);
}
