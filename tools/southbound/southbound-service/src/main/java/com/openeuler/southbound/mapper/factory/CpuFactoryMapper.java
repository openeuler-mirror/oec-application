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

import com.openeuler.southbound.model.factory.CpuFactory;
import com.openeuler.southbound.model.overall.WholeOverall;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * CPU厂商表的操作接口
 *
 * @since 2022-08-29
 */
@Mapper
public interface CpuFactoryMapper {
    /**
     * 按条件查询cpu厂商
     *
     * @param cpuFactory cpu厂商实体类
     * @return cpu厂商
     */
    List<CpuFactory> queryAll(CpuFactory cpuFactory);

    /**
     * 添加cpu厂商
     *
     * @param cpuFactory cpu厂商实体类
     * @return int
     */
    int add(CpuFactory cpuFactory);

    /**
     * 添加cpu厂商版本关联关系
     *
     * @param cpuFactory cpu厂商实体类
     * @return int
     */
    int addCpuVersion(CpuFactory cpuFactory);

    /**
     * 修改cpu厂商
     *
     * @param cpuFactory cpu厂商实体类
     * @return int
     */
    int update(CpuFactory cpuFactory);

    /**
     * 删除cpu厂商
     *
     * @param ids cpu厂商id
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 删除cpu厂商关联关系
     *
     * @param ids cpu厂商id
     * @return int
     */
    int deleteCpuVersion(String ids);

    /**
     * 查询cpu厂商名称
     *
     * @return cpu厂商名称
     */
    List<String> queryNameList();

    /**
     * 查询cpu型号
     *
     * @return cpu型号
     */
    List<String> queryModelList();

    /**
     * 根据cpu厂商查询cpu型号
     *
     * @param cpuFactory cpu厂商
     * @return cpu型号
     */
    List<String> queryCpuModelNames(String cpuFactory);

    /**
     * 查询架构
     *
     * @return 架构
     */
    List<String> selectArchitecture();

    /**
     * 查看版本是否支持
     *
     * @param wholeOverall 整机
     * @return int
     */
    int selectSupportVersion(WholeOverall wholeOverall);

    /**
     * 通过cpu型号查询架构
     *
     * @param cpuModel cpu型号
     * @return string
     */
    String selectArchitectureByCpuModel(String cpuModel);

    /**
     * 导出cpu厂商
     *
     * @return cpu厂商
     */
    List<CpuFactory> exportAllData();
}
