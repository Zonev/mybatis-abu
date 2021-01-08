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

import com.github.zonev.abu.config.BindDataConfig;
import com.github.zonev.abu.dao.UserDao;
import com.github.zonev.abu.dto.UserDTO;
import com.github.zonev.abu.enums.BindScene;
import com.github.zonev.abu.util.CommonFieldUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zonev
 */
public class DaoTest {

    private static final Logger logger = LoggerFactory.getLogger(DaoTest.class);

    private static final SqlSession sqlSession = SqlSessionHelper.getSqlSession();

    private static final BindDataConfig bindDataConfig = new BindDataConfig();

    @BeforeEach
    public void getSqlSession() {
        bindDataConfig
                .add("id", CommonFieldUtils.nextId(), BindScene.INSERT)
                .add("createTime", CommonFieldUtils.time(), BindScene.INSERT)
                .add("updateTime", CommonFieldUtils.time(), BindScene.INSERT, BindScene.UPDATE)
                .add("delete", false, BindScene.INSERT, BindScene.UPDATE);
    }

    @Test
    public void insertByDTO() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.insertByDTO(
                new UserDTO("小明", 20)
        );
        sqlSession.commit();
    }

    @Test
    public void updateAgeByUsername() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.updateAgeByUsername(
                new UserDTO("小明", 30)
        );
        sqlSession.commit();
    }
}
