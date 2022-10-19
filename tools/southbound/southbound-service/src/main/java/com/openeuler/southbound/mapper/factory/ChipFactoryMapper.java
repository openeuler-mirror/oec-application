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

import com.openeuler.southbound.model.factory.ChipFactory;
import com.openeuler.southbound.model.overall.BoardOverall;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 芯片厂商表的操作接口
 *
 * @since 2022-08-29
 */
@Mapper
public interface ChipFactoryMapper {
    /**
     * 查询所有芯片厂商
     *
     * @param chipFactory 芯片厂商实体类
     * @return 片厂商
     */
    List<ChipFactory> queryAll(ChipFactory chipFactory);

    /**
     * 添加芯片厂商
     *
     * @param chipFactory 芯片厂商实体类
     */
    void add(ChipFactory chipFactory);

    /**
     * 添加芯片版本关联关系
     *
     * @param chipId     芯片id
     * @param versionIds 操作系统id
     * @return int
     */
    int addChipVersion(int chipId, String versionIds);

    /**
     * 修改芯片厂商
     *
     * @param chipFactory 芯片厂商实体类
     * @return int
     */
    int update(ChipFactory chipFactory);

    /**
     * 删除芯片厂商
     *
     * @param ids 芯片厂商id
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 删除芯片版本关联关系
     *
     * @param ids 芯片厂商id
     * @return int
     */
    int deleteChipVersion(String ids);

    /**
     * 查询芯片厂商名称
     *
     * @return 芯片厂商名称
     */
    List<String> queryNameList();

    /**
     * 查询芯片型号
     *
     * @return 芯片型号
     */
    List<String> queryModelList();

    /**
     * 根据芯片厂商查询芯片型号
     *
     * @param chipFactory 芯片厂商
     * @return 芯片型号
     */
    List<String> queryChipModelName(String chipFactory);

    /**
     * 查询版本是否支持
     *
     * @param boardOverall boardOverall
     * @return int
     */
    int queryVersionSupport(BoardOverall boardOverall);

    /**
     * 导出所有芯片厂商
     *
     * @return 芯片厂商
     */
    List<ChipFactory> exportAllData();
}
