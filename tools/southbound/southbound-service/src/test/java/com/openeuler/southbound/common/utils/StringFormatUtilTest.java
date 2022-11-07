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
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 工具类测试类
 *
 * @since 2022-08-25
 */
class StringFormatUtilTest {
    @Test
    void list2StrUtil() {
        List<String> emptyList = new ArrayList<>();
        StringFormatUtil.list2StrUtil(emptyList);
        List<String> arrayList = new ArrayList<>();
        arrayList.add("MODEL1");
        arrayList.add("MODEL2");
        arrayList.add("MODEL3");
        StringFormatUtil.list2StrUtil(arrayList);
    }

    @Test
    void str2ListUtil() {
        StringFormatUtil.str2ListUtil("");
        StringFormatUtil.str2ListUtil("MODEL1");
        StringFormatUtil.str2ListUtil("MODEL1,MODEL2");
    }
}