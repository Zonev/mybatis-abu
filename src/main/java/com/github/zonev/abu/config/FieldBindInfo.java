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

import org.apache.ibatis.mapping.SqlCommandType;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 字段绑定信息类
 *
 * @author Zonev
 */
public class FieldBindInfo implements Serializable {

    private static final long serialVersionUID = 4585491184756219245L;

    /**
     * 实体类属性值
     */
    private String javaField;

    /**
     * 数据库字段名
     */
    private String jdbcField;

    /**
     * 字段名对应的取值方法或固定值
     */
    private Supplier<?> supplier;

    /**
     * 字段（属性）类型
     */
    private Class<?> fieldType;

    /**
     * 生效场景，自动绑定在 insert 或 update 语句下生效
     * {@link org.apache.ibatis.mapping.SqlCommandType}
     */
    private SqlCommandType[] sceneTypes;

    public FieldBindInfo(String javaField, String jdbcField, Supplier<?> supplier, Class<?> fieldType, SqlCommandType... sceneTypes) {
        this.javaField = javaField;
        this.jdbcField = jdbcField;
        this.supplier = supplier;
        this.fieldType = fieldType;
        this.sceneTypes = sceneTypes;
    }

    public String getJavaField() {
        return javaField;
    }

    public String getJdbcField() {
        return jdbcField;
    }

    public Supplier<?> getSupplier() {
        return supplier;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public SqlCommandType[] getSceneTypes() {
        return sceneTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FieldBindInfo that = (FieldBindInfo) o;
        return Objects.equals(javaField, that.javaField) && Objects.equals(jdbcField, that.jdbcField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(javaField, jdbcField);
    }
}
