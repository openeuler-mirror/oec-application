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

import com.github.pagehelper.PageInfo;
import com.openeuler.southbound.model.ResponseBean;
import com.openeuler.southbound.model.factory.WholeFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 整机厂商service
 *
 * @since 2022-8-29
 */
@Service
public interface WholeFactoryService {
    /**
     * 查询
     *
     * @param wholeFactory 整机厂商
     * @return 查询结果
     */
    PageInfo queryAll(WholeFactory wholeFactory);

    /**
     * 新增
     *
     * @param wholeFactory 整机厂商
     * @return 新增结果
     */
    int add(WholeFactory wholeFactory);

    /**
     * 修改
     *
     * @param wholeFactory 整机厂商
     * @return 修改结果
     */
    int update(WholeFactory wholeFactory);

    /**
     * 批量删除
     *
     * @param ids 数组
     * @return 删除结果
     */
    int deleteByIds(String ids);

    /**
     * 查询名字
     *
     * @return 查询结果
     */
    List<String> queryNameList();

    /**
     * 查询model
     *
     * @param wholeFactory 整机厂商
     * @return 查询结果
     */
    Map<String, String> queryModelList(WholeFactory wholeFactory);

    /**
     * 查询CPU厂商
     *
     * @param wholeFactory 整机厂商
     * @return 查询结果
     */
    List<String> queryCpuFactory(String wholeFactory);

    /**
     * 查询CPU型号
     *
     * @param wholeFactory 整机厂商
     * @param cpuFactory   CPU厂商
     * @return 查询结果
     */
    List<String> queryCpuModel(String wholeFactory, String cpuFactory);

    /**
     * 导入整机厂商
     *
     * @param file file
     * @return ResponseBean
     */
    ResponseBean uploadWholeFactoryExcel(MultipartFile file);

    /**
     * 导出所有整机厂商数据
     *
     * @return ResponseBean
     */
    List<WholeFactory> exportAllData();
}
