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

package com.openeuler.southbound.model.community;

import java.util.List;
import lombok.Data;

/**
 * 从社区获取兼容性数据返回体包装类-数据体
 *
 * @since 2022-8-26
 */
@Data
public class CommunityDataRespResult {
    /**
     * 社区兼容性数据包装类-【整机】
     */
    List<CommunityWholeMachineBean> hardwareCompList;

    /**
     * 社区兼容性数据包装类-【板卡】
     */
    List<CommunityBoardBean> driverCompList;
}
