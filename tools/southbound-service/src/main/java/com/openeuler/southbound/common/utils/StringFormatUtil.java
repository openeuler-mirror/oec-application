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

package com.openeuler.southbound.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串转换工具类
 *
 * @since 2022-06-27
 */
@Slf4j
public class StringFormatUtil {
    /**
     * 集合转换为字符串
     *
     * @param betaList betaList
     * @return String
     */
    public static String list2StrUtil(List<String> betaList) {
        StringBuilder betaListStrBuilder = new StringBuilder();
        for (String item : betaList) {
            betaListStrBuilder.append(item).append(",");
        }
        int index = betaListStrBuilder.lastIndexOf(",");
        if (index > 0) {
            return betaListStrBuilder.deleteCharAt(index).toString();
        }
        return "";
    }

    /**
     * 字符串转换为集合
     *
     * @param listStr listStr
     * @return List<String>
     */
    public static List<String> str2ListUtil(String listStr) {
        if (listStr == null) {
            log.error("string 2 list error, str is null");
            return new ArrayList<>();
        }
        if ("".equals(listStr)) {
            log.warn("string 2 list waring, str is empty");
            return new ArrayList<>();
        }
        String[] split = listStr.split(",");
        return new ArrayList<>(Arrays.asList(split));
    }
}
