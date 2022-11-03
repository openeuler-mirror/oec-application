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

import com.openeuler.southbound.model.community.CommunityWholeMachineBean;
import com.openeuler.southbound.model.factory.WholeFactory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 整机厂商表的操作接口
 *
 * @since 2022-08-29
 */
@Mapper
public interface WholeFactoryMapper {
    /**
     * 按条件查询整机厂商
     *
     * @param wholeFactory 整机厂商
     * @return list
     */
    List<WholeFactory> queryAll(WholeFactory wholeFactory);

    /**
     * 添加整机厂商
     *
     * @param wholeFactory 整机厂商
     * @return int
     */
    int insert(WholeFactory wholeFactory);

    /**
     * 修改整机厂商
     *
     * @param wholeFactory 整机厂商
     * @return int
     */
    int update(WholeFactory wholeFactory);

    /**
     * 删除整机厂商
     *
     * @param ids 整机厂商id
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 整机厂商名称
     *
     * @return list
     */
    List<String> queryNameList();

    /**
     * 查询整机机型-典型
     *
     * @param wholeFactory 整机厂商
     * @return WholeFactory
     */
    List<String> queryTypicalModelList(WholeFactory wholeFactory);

    /**
     * 查询整机机型-扩展
     *
     * @param wholeFactory 整机厂商
     * @return WholeFactory
     */
    List<String> queryExtendModelList(WholeFactory wholeFactory);

    /**
     * 查询cpu厂商
     *
     * @param wholeFactory 整机厂商
     * @return list
     */
    List<String> queryCpuFactory(String wholeFactory);

    /**
     * 查询cpu型号
     *
     * @param wholeFactory 整机厂商
     * @param cpuFactory   cpu厂商
     * @return list
     */
    List<String> queryCpuModel(String wholeFactory, String cpuFactory);

    /**
     * 查询整机机型
     *
     * @return list
     */
    List<WholeFactory> queryWholeModelList();

    /**
     * 查询机型是否支持
     *
     * @param hardwareFactory 整机厂商
     * @param versionName     操作系统
     * @param hardwareModel   整机型号
     * @return list
     */
    List<CommunityWholeMachineBean> queryModelIsSupport(String hardwareFactory,
                                                        String versionName, String hardwareModel);

    /**
     * 导出整机厂商
     *
     * @return list
     */
    List<WholeFactory> exportAllData();

    /**
     * 查询cpuId是否存在
     *
     * @param wholeFactory wholeFactory
     * @return String
     */
    String queryCpuId(WholeFactory wholeFactory);
}
