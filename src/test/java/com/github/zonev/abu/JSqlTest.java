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

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Zonev
 */
public class JSqlTest {

    private static final Logger logger = LoggerFactory.getLogger(JSqlTest.class);

    @Test
    public void updateSqlParser() throws Exception {
        String sql = "update t_user set age = ? where username = ?";

        Update update = (Update) CCJSqlParserUtil.parse(sql);

        List<Column> columnList = update.getColumns();
        columnList.add(new Column("id"));
        update.setColumns(columnList);

        List<Expression> expressionList = update.getExpressions();
        expressionList.add(new JdbcParameter());

        logger.info(update.toString());
    }

    @Test
    public void insertSqlParser() throws Exception {
        String sql = "insert into t_user (username, age) values (?, ?)";

        Insert insert = (Insert) CCJSqlParserUtil.parse(sql);
        insert.addColumns(new Column("id"));
        insert.getItemsList(ExpressionList.class).addExpressions(new JdbcParameter());

        logger.info(insert.toString());
    }
}
