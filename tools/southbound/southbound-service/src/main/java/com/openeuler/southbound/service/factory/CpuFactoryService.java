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
import com.openeuler.southbound.model.factory.CpuFactory;
import com.openeuler.southbound.model.overall.WholeOverall;

import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * CPU厂商service
 *
 * @since 2022-8-29
 */
@Service
public interface CpuFactoryService {
    /**
     * 分页查询
     *
     * @param cpuFactory CPU厂商
     * @return 查询结果
     */
    PageInfo queryAll(CpuFactory cpuFactory);

    /**
     * 新增
     *
     * @param cpuFactory CPU厂商
     * @return 新增结果
     */
    int add(CpuFactory cpuFactory);

    /**
     * 修改
     *
     * @param cpuFactory CPU厂商
     * @return 修改结果
     */
    int update(CpuFactory cpuFactory);

    /**
     * 批量删除
     *
     * @param ids 数组
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
     * 查询架构
     *
     * @return 查询结果
     */
    List<String> queryArchitectureList();

    /**
     * 查询CPU型号
     *
     * @param cpuFactory CPU厂商
     * @return 查询结果
     */
    List<String> queryCpuModelNames(String cpuFactory);

    /**
     * 查看CPU代次版本是否支持
     *
     * @param wholeOverall wholeOverall
     * @return boolean
     */
    boolean isSupport(WholeOverall wholeOverall);

    /**
     * 批量上传cpu厂商
     *
     * @param file file
     * @return ResponseBean
     */
    ResponseBean uploadCpuFactoryExcel(MultipartFile file);

    /**
     * 导出全部cpu厂商
     *
     * @return list
     */
    List<CpuFactory> exportAllData();
}
