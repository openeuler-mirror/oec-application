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

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.ObjectUtils;

/**
 * token缓存工具类
 *
 * @since 2022-06-27
 */
public class TokenCacheUtil {
    private static Map<Object, Object> cacheMap = new HashMap<>();

    /**
     * 设置布尔值的缓存
     *
     * @param key  key
     * @param date date
     */
    public static synchronized void setSimpleFlag(String key, Long date) {
        cacheMap.put(key, date);
    }

    /**
     * 根据键获取时间long
     *
     * @param key key
     * @return long
     */
    public static Long getServerStartLongTime(String key) {
        Long aLong = null;
        Object objKey = cacheMap.get(key);
        if (objKey instanceof Long) {
            aLong = (Long) objKey;
        }
        return aLong;
    }

    /**
     * 判断是否存在缓存
     *
     * @return boolean
     */
    public static synchronized boolean isCacheEmpty() {
        return ObjectUtils.isEmpty(cacheMap);
    }

    /**
     * 判断是否存在一个缓存
     *
     * @param key key
     * @return hasCache
     */
    public static synchronized boolean hasCache(String key) {
        return cacheMap.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public static synchronized void clearAll() {
        cacheMap.clear();
    }

    /**
     * 清除指定的缓存
     *
     * @param key key
     */
    public static synchronized void clearOnly(String key) {
        cacheMap.remove(key);
    }

    /**
     * 获取缓存中的大小
     *
     * @return cacheMapSize
     */
    public static int getCacheSize() {
        return cacheMap.size();
    }

    /**
     * 根据最新的token通过用户名相同删除之前的token
     *
     * @param token token
     */
    public static void existByUserName(String token) {
        String nowUserName = TokenUtil.getUser(token);
        Iterator<Entry<Object, Object>> it = cacheMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (nowUserName.equalsIgnoreCase(TokenUtil.getUser(key))) {
                it.remove();
            }
            // 超过30分钟，清理缓存
            if ((new Date()).getTime() - (Long.parseLong(value)) >= 1000 * 60 * 30) {
                it.remove();
            }
        }
    }

    /**
     * 过滤当前时间如果过期了就移除
     *
     * @param time time
     */
    public static void deleteTokenForExpired(Long time) {
        Iterator<Entry<Object, Object>> it = cacheMap.entrySet().iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String value = entry.getValue().toString();
            // 顺路可以把过期的也移除下
            if ((new Date()).getTime() - (Long.parseLong(value)) >= time) {
                it.remove();
            }
        }
    }
}
