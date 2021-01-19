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
import com.github.zonev.abu.dao.UserDao;
import com.github.zonev.abu.dto.UserDTO;
import com.github.zonev.abu.util.CommonFieldUtils;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zonev
 */
public class DaoTest {

    private static final Logger logger = LoggerFactory.getLogger(DaoTest.class);

    private static final SqlSession sqlSession = SqlSessionHelper.getSqlSession();

    @BeforeEach
    public void getSqlSession() {
        FieldBindConfig.builder()
                .bind("id", CommonFieldUtils::nextId, Long.class, SqlCommandType.INSERT)
                .bind("createTime", CommonFieldUtils::time, Long.class, SqlCommandType.INSERT)
                .bind("updateTime", CommonFieldUtils::time, Long.class, SqlCommandType.INSERT, SqlCommandType.UPDATE)
                .bind("delete", false, Boolean.class, SqlCommandType.INSERT, SqlCommandType.UPDATE);
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
    public void insertByDTOList() {
        List<UserDTO> list = Arrays.asList(
                new UserDTO("小红", 1),
                new UserDTO("小黄", 2),
                new UserDTO("小绿", 3),
                new UserDTO("小黑", 4),
                new UserDTO("小白", 5),
                new UserDTO("小蓝", 6)
        );
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.insertByDTOList(list);
        sqlSession.commit();
    }

    @Test
    public void insertByDTOMap() {
        Map<String, UserDTO> map = new HashMap<>();
        map.put("a", new UserDTO("小红", 1));
        map.put("b", new UserDTO("小黄", 2));
        map.put("c", new UserDTO("小绿", 3));
        map.put("d", new UserDTO("小黑", 4));
        map.put("e", new UserDTO("小白", 5));
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.insertByDTOMap(map);
        sqlSession.commit();
    }

    @Test
    public void insertByDTOField() {
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        userDao.insertByDTOField("小明");
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
