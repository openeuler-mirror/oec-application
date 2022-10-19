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
import com.openeuler.southbound.model.factory.BoardFactory;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 板卡厂商service
 *
 * @since 2022-8-29
 */
@Service
public interface BoardFactoryService {
    /**
     * 分页查询
     *
     * @param boardFactory 板卡厂商
     * @return 查询结果
     */
    PageInfo queryAll(BoardFactory boardFactory);

    /**
     * 新增
     *
     * @param boardFactory 板卡厂商
     * @return 新增结果
     */
    int add(BoardFactory boardFactory);

    /**
     * 修改
     *
     * @param boardFactory 板卡厂商
     * @return 修改结果
     */
    int update(BoardFactory boardFactory);

    /**
     * 批量删除
     *
     * @param ids 数组
     * @return 删除结果
     */
    int deleteByIds(String ids);

    /**
     * 查询model
     *
     * @param boardFactory 板卡厂商
     * @return 查询结果
     */
    Map<String, String> queryModelList(BoardFactory boardFactory);

    /**
     * 查询板卡厂商表中芯片厂商列表
     *
     * @return ResponseBean
     */
    List<String> queryChipFactory();

    /**
     * 根绝芯片厂商、板卡类型查询芯片型号
     *
     * @param chipFactory 芯片厂商
     * @param boardType   板卡类型
     * @return ResponseBean
     */
    List<String> queryChipModel(String chipFactory, String boardType);

    /**
     * 查询板卡类型列表
     *
     * @param boardFactory 板卡厂商
     * @return ResponseBean
     */
    List<String> queryBoardType(BoardFactory boardFactory);

    /**
     * 板卡厂商批量导入
     *
     * @param file 导入模板文件
     * @return ResponseBean
     */
    ResponseBean uploadBoardFactoryExcel(MultipartFile file);

    /**
     * 导出所有板卡厂商数据
     *
     * @return ResponseBean
     */
    List<BoardFactory> exportAllData();
}
