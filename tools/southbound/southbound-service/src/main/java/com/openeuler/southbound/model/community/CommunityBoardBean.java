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
 * 社区兼容性数据包装类-板卡
 * 社区接口返回值中变量名为 DriverComp
 *
 * @since 2022-8-26
 */
@Data
public class CommunityBoardBean {
    int id;
    String architecture;
    String boardModel;
    String chipModel;
    String chipVendor;
    String deviceID;
    String downloadLink;
    String driverDate;
    String driverName;
    String driverSize;
    String item;
    String lang;
    String os;
    String sha256;
    String ssID;
    String svID;
    String type;
    String vendorID;
    String version;
    String updateTime;
}
