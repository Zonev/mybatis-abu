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

package com.github.zonev.abu.util;

import org.apache.ibatis.reflection.MetaObject;

import java.util.Objects;

/**
 * 通用字段填充工具类
 *
 * @author Zonev
 */
public class FieldBindUtils {

    private FieldBindUtils() {
    }

    /**
     * 填充字段
     *
     * @param metaObject 元数据对象
     * @param javaField  对应的 JavaBean 属性
     * @param fieldValue 具体字段属性值
     */
    public static void setByJavaField(MetaObject metaObject, String javaField, Object fieldValue) {
        if (metaObject.hasSetter(javaField) && Objects.isNull(metaObject.getValue(javaField))) {
            metaObject.setValue(javaField, fieldValue);
        }
    }
}
