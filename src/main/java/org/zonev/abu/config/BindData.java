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

package org.zonev.abu.config;

import org.zonev.abu.enums.BindScene;

import java.util.function.Supplier;

/**
 * 绑定参数元信息
 *
 * @author Zonev
 */
public class BindData {

    /**
     * 绑定的字段名，该属性为实体类中对应的属性名
     */
    private final String column;

    /**
     * 字段名对应的取值方法或固定值
     */
    private final Supplier<Object> supplier;

    /**
     * 生效场景 {@link BindScene}
     */
    private final BindScene[] scene;

    protected BindData(String column, Supplier<Object> supplier, BindScene[] scene) {
        this.column = column;
        this.supplier = supplier;
        this.scene = scene;
    }

    public String getColumn() {
        return column;
    }

    public Supplier<Object> getSupplier() {
        return supplier;
    }

    public BindScene[] getScene() {
        return scene;
    }
}
