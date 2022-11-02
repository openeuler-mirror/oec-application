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
import com.openeuler.southbound.model.overall.BoardOverall;
import com.openeuler.southbound.model.factory.ChipFactory;

import com.github.pagehelper.PageInfo;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 芯片厂商service
 *
 * @since 2022-8-29
 */
@Service
public interface ChipFactoryService {
    /**
     * 分页查询
     *
     * @param chipFactory 芯片厂商
     * @return 查询结果
     */
    PageInfo queryAll(ChipFactory chipFactory);

    /**
     * 新增
     *
     * @param chipFactory 芯片厂商
     * @return 新增结果
     */
    int add(ChipFactory chipFactory);

    /**
     * 修改
     *
     * @param chipFactory 芯片厂商
     * @return 修改结果
     */
    int update(ChipFactory chipFactory);

    /**
     * 批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    int deleteByIds(String ids);

    /**
     * 查询name
     *
     * @return 查询结果
     */
    List<String> queryNameList();

    /**
     * 查询model
     *
     * @return 查询结果
     */
    List<String> queryModelList();

    /**
     * 查询芯片厂商名称
     *
     * @param chipFactory 芯片厂商
     * @return 查询结果
     */
    List<String> queryChipModelName(String chipFactory);

    /**
     * 查询版本是否支持
     *
     * @param boardOverall boardOverall
     * @return boolean true表示支持 false表示不支持
     */
    boolean queryVersionSupport(BoardOverall boardOverall);

    /**
     * 芯片厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    ResponseBean uploadChipFactoryExcel(MultipartFile file);

    /**
     * 导出所有芯片厂商数据
     *
     * @return ResponseBean
     */
    List<ChipFactory> exportAllData();
}
