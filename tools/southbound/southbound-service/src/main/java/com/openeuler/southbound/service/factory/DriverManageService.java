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

package com.openeuler.southbound.service.factory;

import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.DriverManage;

import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 驱动管理service
 *
 * @since 2022-8-29
 */
@Service
public interface DriverManageService {
    /**
     * 分页查询
     *
     * @param driverManage 驱动
     * @return 查询结果
     */
    PageInfo queryAll(DriverManage driverManage);

    /**
     * 新增
     *
     * @param driverManage 驱动
     * @return 新增结果
     */
    int add(DriverManage driverManage);

    /**
     * 修改
     *
     * @param driverManage 驱动
     * @return 修改结果
     */
    int update(DriverManage driverManage);

    /**
     * 批量删除
     *
     * @param ids 数组
     * @return 删除结果
     */
    int deleteByIds(String ids);

    /**
     * 查询name集合
     *
     * @return 查询结果
     */
    List<String> queryChipNameList();

    /**
     * 查询model
     *
     * @param driverList 驱动
     * @return 查询结果
     */
    List<String> queryModelList(String driverList);

    /**
     * 查询驱动名称
     *
     * @param chipFactory 芯片厂商
     * @return list
     */
    List<String> queryDriverName(String chipFactory);

    /**
     * 芯片厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    ResponseBean uploadDriverFactoryExcel(MultipartFile file);

    /**
     * 导出所有驱动数据
     *
     * @return ResponseBean
     */
    List<DriverManage> exportAllData();
}
