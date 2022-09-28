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

import lombok.Data;

/**
 * 社区兼容性数据包装类-整机
 * 社区接口返回值名称为 HardwareComp
 *
 * @since 2022-8-26
 */
@Data
public class CommunityWholeMachineBean {
    int id;
    String architecture;
    String biosUefi;
    String certificationAddr;
    String certificationTime;
    String commitID;
    String computerType;
    String cpu;
    String date;
    String friendlyLink;
    String hardDiskDrive;
    String hardwareFactory;
    String hardwareModel;
    String hostBusAdapter;
    String lang;
    String mainboardModel;
    String osVersion;
    String portsBusTypes;
    String productInformation;
    String ram;
    String videoAdapter;
    String updateTime;
    String compatibilityConfiguration;
    String boardCards;
}
