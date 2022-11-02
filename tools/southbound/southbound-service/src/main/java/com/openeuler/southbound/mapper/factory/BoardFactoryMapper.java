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

import com.openeuler.southbound.model.community.CommunityBoardBean;
import com.openeuler.southbound.model.factory.BoardFactory;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 板卡厂商表的操作接口
 *
 * @since 2022-08-29
 */
@Mapper
public interface BoardFactoryMapper {
    /**
     * 查询所有板卡厂商
     *
     * @param boardFactory 板卡厂商
     * @return 板卡列表
     */
    List<BoardFactory> queryAll(BoardFactory boardFactory);

    /**
     * 新增
     *
     * @param boardFactory 板卡厂商
     * @return int
     */
    int add(BoardFactory boardFactory);

    /**
     * 修改
     *
     * @param boardFactory 板卡厂商
     * @return int
     */
    int update(BoardFactory boardFactory);

    /**
     * 批量删除
     *
     * @param ids 根据id删除板卡
     * @return int
     */
    int deleteByIds(String ids);

    /**
     * 查询板卡型号
     *
     * @param boardFactory 板卡厂商
     * @return int
     */
    List<String> queryTypicalModelList(BoardFactory boardFactory);

    /**
     * 查询板卡型号
     *
     * @param boardFactory 板卡厂商
     * @return int
     */
    List<String> queryExtendModelList(BoardFactory boardFactory);

    /**
     * 查询芯片厂商
     *
     * @return 芯片厂商
     */
    List<String> queryChipFactory();

    /**
     * 查询芯片型号
     *
     * @param chipFactory 芯片厂商
     * @param boardType   板卡类型
     * @return 芯片型号
     */
    List<String> queryChipModel(String chipFactory, String boardType);

    /**
     * 查询板卡厂商
     *
     * @param boardFactory 板卡厂商
     * @return 板卡厂商
     */
    List<String> queryBoardType(BoardFactory boardFactory);

    /**
     * 查询板卡型号
     *
     * @return 板卡型号
     */
    List<BoardFactory> queryBoardModelList();

    /**
     * 社区兼容性清单-板卡
     *
     * @param chipFactory 芯片厂商
     * @param versionName 操作系统
     * @param boardModel 板卡型号
     * @return 社区兼容性清单-板卡
     */
    List<CommunityBoardBean> queryModelIsSupport(String chipFactory, String versionName, String boardModel);

    /**
     * 导出板卡上上所有数据
     *
     * @return 板卡厂商
     */
    List<BoardFactory> exportAllData();

    /**
     * 查看芯片id是否存在
     *
     * @param boardFactory boardFactory
     * @return string
     */
    String queryChipId(BoardFactory boardFactory);
}
