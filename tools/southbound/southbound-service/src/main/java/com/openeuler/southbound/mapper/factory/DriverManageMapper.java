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

package com.openeuler.southbound.mapper.factory;

import com.openeuler.southbound.model.factory.DriverManage;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 驱动管理表的操作接口
 *
 * @since 2022-08-29
 */
@Mapper
public interface DriverManageMapper {
    /**
     * 按条件查询驱动
     *
     * @param driverManage driverManage
     * @return list
     */
    List<DriverManage> queryAll(DriverManage driverManage);

    /**
     * 添加驱动
     *
     * @param driverManage driverManage
     * @return int
     */
    int add(DriverManage driverManage);

    /**
     * 修改驱动
     *
     * @param driverManage driverManage
     * @return int
     */
    int update(DriverManage driverManage);

    /**
     * 删除驱动
     *
     * @param ids 驱动ids
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 查询芯片厂商名称
     *
     * @return list
     */
    List<String> queryChipNameList();

    /**
     * 查询芯片型号
     *
     * @param driverList 驱动
     * @return list
     */
    List<String> queryModelList(String driverList);

    /**
     * 根据芯片厂商查询驱动名称
     *
     * @param chipFactory 芯片厂商
     * @return list
     */
    List<String> queryDriverName(String chipFactory);

    /**
     * 导出芯片厂商
     *
     * @return list
     */
    List<DriverManage> exportAllData();
}