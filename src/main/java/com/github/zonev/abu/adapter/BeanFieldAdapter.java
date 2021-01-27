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

import com.github.zonev.abu.config.FieldBindConfig;
import com.github.zonev.abu.config.FieldBindInfo;
import com.github.zonev.abu.util.FieldBindUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Set;

/**
 * @author Zonev
 */
public class BeanFieldAdapter implements FieldAdapter {

    @Override
    public boolean support(Object parameter) {
        return FieldBindConfig.getInstance().getBaseModel().isInstance(parameter);
    }

    @Override
    public void doFieldFill(MappedStatement mappedStatement, Object parameter) {
        Set<FieldBindInfo> config = FieldBindConfig.getInstance().getFieldBindInfoMap().get(mappedStatement.getSqlCommandType());
        MetaObject metaObject = SystemMetaObject.forObject(parameter);
        for (FieldBindInfo fieldBindInfo : config) {
            FieldBindUtils.setByJavaField(
                    metaObject,
                    fieldBindInfo.getJavaField(),
                    fieldBindInfo.getSupplier().get()
            );
        }
    }
}
