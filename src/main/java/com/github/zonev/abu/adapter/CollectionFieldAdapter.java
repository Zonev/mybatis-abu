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
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.*;

/**
 * 集合适配器
 *
 * @author Zonev
 */
public class CollectionFieldAdapter implements FieldAdapter {

    @Override
    public boolean support(Object parameter) {
        return (parameter instanceof MapperMethod.ParamMap);
    }

    @Override
    public void doFieldFill(MappedStatement mappedStatement, Object parameter) {
        Set<Collection<?>> collectionSet = new HashSet<>();
        Set<FieldBindInfo> config = FieldBindConfig.getInstance().getFieldBindInfoMap().get(mappedStatement.getSqlCommandType());

        ((Map<?, ?>) parameter).forEach((k, v) -> {
            if (v instanceof Collection) {
                collectionSet.add((Collection<?>) v);
            }
        });
        if (collectionSet.size() > 0) {
            for (Collection<?> collection : collectionSet) {
                for (Object object : collection) {
                    if (!FieldBindConfig.getInstance().getBaseModel().isInstance(object)) {
                        break;
                    }
                    MetaObject metaObject = SystemMetaObject.forObject(object);
                    for (FieldBindInfo fieldBindInfo : config) {
                        FieldBindUtils.setByJavaField(
                                metaObject,
                                fieldBindInfo.getJavaField(),
                                fieldBindInfo.getSupplier().get()
                        );
                    }
                }
            }
        }
    }
}
