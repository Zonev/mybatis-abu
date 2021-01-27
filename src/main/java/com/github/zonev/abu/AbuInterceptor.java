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

package com.github.zonev.abu;

import com.github.zonev.abu.config.FieldBindConfig;
import com.github.zonev.abu.adapter.FieldAdapter;
import com.github.zonev.abu.adapter.BeanFieldAdapter;
import com.github.zonev.abu.processor.FieldProcessor;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * @author Zonev
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AbuInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (FieldBindConfig.getInstance().getFieldBindInfoMap().size() <= 0) {
            throw new IllegalArgumentException("FieldBindConfig 配置不正确，请检查配置");
        }
        if (FieldBindConfig.getInstance().getBaseModel() == null) {
            throw new IllegalArgumentException("BaseModel 配置不正确，请检查配置");
        }

        FieldProcessor processor = new FieldProcessor();
        processor.fieldFill(
                (MappedStatement) invocation.getArgs()[0],
                invocation.getArgs()[1]
        );

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}
}
