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

package com.openeuler.southbound.service.overall.impl;

import com.openeuler.southbound.common.content.MessageContent;
import com.openeuler.southbound.mapper.factory.ChipFactoryMapper;
import com.openeuler.southbound.mapper.overall.BoardOverallMapper;
import com.openeuler.southbound.model.overall.BoardOverall;
import com.openeuler.southbound.service.overall.BoardOverallService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 板卡services
 *
 * @since 2022-06-28
 */
@Service
@Slf4j
public class BoardOverallServiceImpl implements BoardOverallService {
    @Autowired
    private BoardOverallMapper boardOverallMapper;

    @Autowired
    private ChipFactoryMapper chipFactoryMapper;

    /**
     * 查询板卡
     *
     * @param boardOverall board对象
     * @return PageInfo
     */
    @Override
    public PageInfo queryAll(BoardOverall boardOverall) {
        PageHelper.startPage(boardOverall.getPageIndex(), boardOverall.getPageSize());
        List<BoardOverall> boardOverallList = boardOverallMapper.queryAll(boardOverall);
        queryBoardStatus(boardOverallList);
        return new PageInfo(boardOverallList);
    }

    /**
     * 导出所有板卡数据
     *
     * @return ResponseBean
     */
    @Override
    public List<BoardOverall> exportAllData() {
        List<BoardOverall> boardOverallList = boardOverallMapper.exportAllData();
        queryBoardStatus(boardOverallList);
        return boardOverallList;
    }

    private void queryBoardStatus(List<BoardOverall> boardOverallList) {
        boardOverallList.forEach(item -> {
            item.setExtendBoardModelCount(StringUtils.isEmpty(item.getExtendBoardModel())
                    ? 0 : item.getExtendBoardModel().split(",").length);
            // 典型适配状态
            int supportCount = chipFactoryMapper.queryVersionSupport(item);
            if (supportCount <= 0) {
                item.setTypicalBoardX86(MessageContent.ADAPTATION_STATUS_NOT_SUPPORT);
                item.setTypicalBoardArm(MessageContent.ADAPTATION_STATUS_NOT_SUPPORT);
            } else {
                // 典型x86适配状态
                getTypicalModelStatusX86(item);
                // 典型arm适配状态
                getTypicalModelStatusArm(item);
            }
            // 扩展适配状态
            String extendBoardModel = item.getExtendBoardModel();
            if (StringUtils.isEmpty(extendBoardModel)) {
                item.setExtendBoardX86("0%");
                item.setExtendBoardArm("0%");
            } else {
                List<String> extendModelList = Arrays.asList(extendBoardModel.split(","));
                // 扩展x86适配状态
                int x86Count = boardOverallMapper.queryExtendModelStatusX86(item, extendModelList);
                // 扩展arm适配状态
                int armCount = boardOverallMapper.queryExtendModelStatusArm(item, extendModelList);
                item.setExtendBoardX86(NumberFormat.getPercentInstance()
                        .format((double) x86Count / item.getExtendBoardModelCount()));
                item.setExtendBoardArm(NumberFormat.getPercentInstance()
                        .format((double) armCount / item.getExtendBoardModelCount()));
            }
        });
    }

    private void getTypicalModelStatusArm(BoardOverall item) {
        int armCount = boardOverallMapper.queryTypicalModelStatusArm(item);
        if (armCount > 0) {
            item.setTypicalBoardArm(MessageContent.ADAPTATION_STATUS_ALREADY);
        } else {
            int armPlanCount = boardOverallMapper.queryTypicalModelStatusX86FromPlan(item);
            if (armPlanCount > 0) {
                item.setTypicalBoardArm(MessageContent.ADAPTATION_STATUS_ING);
            } else {
                item.setTypicalBoardArm(MessageContent.ADAPTATION_STATUS_NOT);
            }
        }
    }

    private void getTypicalModelStatusX86(BoardOverall item) {
        int x86Count = boardOverallMapper.queryTypicalModelStatusX86(item);
        if (x86Count > 0) {
            item.setTypicalBoardX86(MessageContent.ADAPTATION_STATUS_ALREADY);
        } else {
            int x86PlanCount = boardOverallMapper.queryTypicalModelStatusX86FromPlan(item);
            if (x86PlanCount > 0) {
                item.setTypicalBoardX86(MessageContent.ADAPTATION_STATUS_ING);
            } else {
                item.setTypicalBoardX86(MessageContent.ADAPTATION_STATUS_NOT);
            }
        }
    }
}
