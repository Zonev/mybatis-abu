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

import com.github.zonev.abu.adapter.BeanFieldAdapter;
import com.github.zonev.abu.adapter.CollectionFieldAdapter;
import com.github.zonev.abu.adapter.FieldAdapter;
import com.github.zonev.abu.adapter.MixFieldAdapter;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.*;
import java.util.function.Supplier;

/**
 * 字段绑定配置类
 *
 * @author Zonev
 */
public class FieldBindConfig {

    private FieldBindConfig() {
    }

    private static FieldBindConfig instance = new FieldBindConfig();

    private Map<SqlCommandType, Set<FieldBindInfo>> fieldBindInfoMap;

    private List<FieldAdapter> adapterList;

    private Class<?> baseModel;

    static {
        FieldBindConfig instance = getInstance();
        instance.adapterList = Arrays.asList(
                new BeanFieldAdapter(),
                new CollectionFieldAdapter(),
                new MixFieldAdapter()
        );
        instance.fieldBindInfoMap = new HashMap<>();
    }

    public static FieldBindConfig getInstance() {
        return instance;
    }

    public Map<SqlCommandType, Set<FieldBindInfo>> getFieldBindInfoMap() {
        return fieldBindInfoMap;
    }

    public List<FieldAdapter> getAdapterList() {
        return adapterList;
    }

    public Class<?> getBaseModel() {
        return baseModel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public Builder bind(String javaField, Object fieldValue, Class<?> fieldType, SqlCommandType... sceneTypes) {
            this.bind(javaField, javaField, fieldValue, fieldType, sceneTypes);
            return this;
        }

        public Builder bind(String javaField, Supplier<?> fieldValueMethod, Class<?> fieldType, SqlCommandType... sceneTypes) {
            this.bind(javaField, javaField, fieldValueMethod, fieldType, sceneTypes);
            return this;
        }

        public Builder bind(String javaField, String jdbcField, Object fieldValue, Class<?> fieldType, SqlCommandType... sceneTypes) {
            this.bind(javaField, jdbcField, () -> fieldValue, fieldType, sceneTypes);
            return this;
        }

        public Builder bind(String javaField, String jdbcField, Supplier<?> fieldValueMethod, Class<?> fieldType, SqlCommandType... sceneTypes) {
            for (SqlCommandType sceneType : sceneTypes) {
                Set<FieldBindInfo> fieldBindInfoSet = getInstance().fieldBindInfoMap.get(sceneType);
                if (fieldBindInfoSet == null) {
                    fieldBindInfoSet = new HashSet<>();
                }
                fieldBindInfoSet.add(new FieldBindInfo(javaField, jdbcField, fieldValueMethod, fieldType, sceneTypes));
                getInstance().fieldBindInfoMap.put(sceneType, fieldBindInfoSet);
            }
            return this;
        }

        public Builder baseModel(Class<?> baseModel) {
            getInstance().baseModel = baseModel;
            return this;
        }
    }
}
