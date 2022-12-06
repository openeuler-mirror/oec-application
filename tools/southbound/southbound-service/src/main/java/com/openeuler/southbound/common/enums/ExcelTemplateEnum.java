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

package com.openeuler.southbound.common.enums;

import com.openeuler.southbound.common.content.MessageContent;
import org.springframework.dao.DuplicateKeyException;

/**
 * 批量导入厂商excel模板
 *
 * @since 2022-09-29
 */
public enum ExcelTemplateEnum {
    CPU_TEMPLATE("cpu", "templates/template_cpu.xlsx"),
    WHOLE_TEMPLATE("whole", "templates/template_whole.xlsx"),
    CHIP_TEMPLATE("chip", "templates/template_chip.xlsx"),
    DRIVER_TEMPLATE("driver", "templates/template_driver.xlsx"),
    BOARD_TEMPLATE("board", "templates/template_board.xlsx");

    private final String key;
    private final String value;

    /**
     * 构造方法
     *
     * @param key   模板类型
     * @param value 模板路径
     */
    ExcelTemplateEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取枚举value
     *
     * @return 返回字符串类型值
     */
    public String value() {
        return value;
    }

    /**
     * 获取枚举key
     *
     * @return 返回字符串类型值
     */
    public String key() {
        return key;
    }

    /**
     * 将str转化为枚举
     *
     * @param key 字符串
     * @return enum
     */
    public static ExcelTemplateEnum getExcelTemplateByKey(String key) {
        for (ExcelTemplateEnum template : ExcelTemplateEnum.values()) {
            if (template.key().equals(key)) {
                return template;
            }
        }
        // 请求参数异常
        throw new DuplicateKeyException(MessageContent.REQUEST_PARAMETER_EXC);
    }
}
