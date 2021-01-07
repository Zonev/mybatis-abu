/*
 * MIT License
 *
 * Copyright (c) 2020 Zonev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.zonev.abu.config;

import com.github.zonev.abu.enums.BindScene;

import java.util.HashMap;
import java.util.Map;

/**
 * 主要配置类
 *
 * @author Zonev
 */
public class BindDataConfig {

    private static final Map<String, Map<String, BindData>> SCENE_BIND_MAP = new HashMap<>();

    /**
     * 新建通用字段绑定信息
     *
     * @param column   绑定的字段名，该属性应为数据库中对应的字段名
     * @param supplier 字段名对应的取值方法
     * @param scene    生效场景 {@link BindScene}
     * @return this
     */
    public BindDataConfig add(String column, Object supplier, BindScene... scene) {
        for (BindScene bindScene : scene) {
            if (SCENE_BIND_MAP.get(bindScene.toString()) == null) {
                Map<String, BindData> map = new HashMap<>();
                map.put(column, new BindData(column, () -> supplier, scene));
                SCENE_BIND_MAP.put(bindScene.toString(), map);
            } else {
                SCENE_BIND_MAP.get(bindScene.toString()).put(column, new BindData(column, () -> supplier, scene));
            }
        }
        return this;
    }

    /**
     * 获取配置
     *
     * @return map
     */
    public static Map<String, Map<String, BindData>> getConfig() {
        return SCENE_BIND_MAP;
    }
}
