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

package com.openeuler.southbound.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.openeuler.southbound.common.utils.HttpRequestUtil;
import com.openeuler.southbound.mapper.CommunityBoardMapper;
import com.openeuler.southbound.model.community.CommunityBoardBean;
import com.openeuler.southbound.model.community.CommunityDataRespResult;
import com.openeuler.southbound.service.CommunityDataService;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 社区兼容性数据包装类-板卡-service 实现类
 *
 * @since 2022-8-26
 */
@Service
@Slf4j
public class CommunityBoardDataServiceImpl implements CommunityDataService<CommunityBoardBean> {
    private static final String BOARD_DRIVER_URL =
            "https://www.openeuler.org/api-cve/cve-security-notice-server/drivercomp/findAll";

    @Autowired
    private CommunityBoardMapper communityBoardMapper;

    @Override
    public List<CommunityBoardBean> queryAllDataFromDataBase() {
        return communityBoardMapper.selectAll();
    }

    @Override
    public JSONObject queryData(String startTime, String endTime) {
        JSONObject jsonObject = new JSONObject();
        List boardModelList = communityBoardMapper.selectAllBoardByData(startTime, endTime);
        List chipModelList = communityBoardMapper.selectAllChipByData(startTime, endTime);
        jsonObject.put("boardModel", boardModelList);
        jsonObject.put("chipModel", chipModelList);
        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncDataFromCommunity() {
        // 清空本地数据
        communityBoardMapper.deleteBatchAll();
        // 从社区获取最新数据
        List<CommunityBoardBean> communityData = getDriverFromCommunity();
        // 将新数据插入
        if (communityData.size() > 0) {
            communityBoardMapper.insertBatch(communityData);
        } else {
            log.info("The community has no new data to save");
        }
    }

    /**
     * 获取所有板卡信息
     *
     * @return 板卡信息list
     */
    public static List<CommunityBoardBean> getDriverFromCommunity() {
        // 获取totalCount
        String param = "{\"keyword\":\"\",\"os\":\"\",\"architecture\":\"\",\"pages\":{\"page\":1,\"size\":9999},"
                + "\"lang\":\"zh\"}";
        String responseStr = HttpRequestUtil.sendPost(BOARD_DRIVER_URL, param);
        JSONObject responseJsObj = JSONObject.parseObject(responseStr);
        JSONObject resultJsObj = responseJsObj.getJSONObject("result");
        CommunityDataRespResult communityDataRespResult = resultJsObj.toJavaObject(CommunityDataRespResult.class);
        return communityDataRespResult.getDriverCompList();
    }
}
