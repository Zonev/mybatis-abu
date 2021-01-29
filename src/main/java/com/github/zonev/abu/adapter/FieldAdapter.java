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

package com.github.zonev.abu.adapter;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 通用字段填充方法适配器接口
 *
 * @author Zonev
 */
public interface FieldAdapter {

    /**
     * 判断是否被该适配器支持
     *
     * @param parameter Object
     * @return true:支持 false:不支持
     */
    boolean support(Object parameter);

    /**
     * 字段填充
     *
     * @param mappedStatement MappedStatement
     * @param parameter       Object
     */
    void doFieldFill(MappedStatement mappedStatement, Object parameter);
}
