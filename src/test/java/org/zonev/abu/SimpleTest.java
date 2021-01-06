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

package org.zonev.abu;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zonev.abu.config.BindDataConfig;
import org.zonev.abu.dao.StudentsDao;
import org.zonev.abu.dto.StudentsInsertDTO;
import org.zonev.abu.enums.BindScene;

import java.util.*;

/**
 * @author Zonev
 */
public class SimpleTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTest.class);

    private SqlSession sqlSession;

    private BindDataConfig bindDataConfig;

    @BeforeEach
    public void getSqlSession() {
        sqlSession = SqlSessionHelper.getSqlSession();

        bindDataConfig = new BindDataConfig();
        bindDataConfig
                .add("name", "JJJ", BindScene.INSERT, BindScene.UPDATE)
                .add("classNum", "Tom", BindScene.UPDATE);
    }

    @Test
    public void insertStudentByEntity() {
        StudentsDao studentsDao = sqlSession.getMapper(StudentsDao.class);
        int result = studentsDao.insertStudentByEntity(
                new StudentsInsertDTO("小明", 5, 11)
        );
        logger.debug("影响数据行数 -> {}", result);
        sqlSession.commit();
    }

    @Test
    public void insertStudentByColumn() {
        StudentsDao studentsDao = sqlSession.getMapper(StudentsDao.class);
        int result = studentsDao.insertStudentByColumn(
                "小明", 1, 3
        );
        logger.debug("影响数据行数 -> {}", result);
        sqlSession.commit();
    }

    @Test
    public void insertBatchStudentByEntity() {
        StudentsDao studentsDao = sqlSession.getMapper(StudentsDao.class);

        List<StudentsInsertDTO> studentsInsertDTOList = new ArrayList<>();
        studentsInsertDTOList.add(
                new StudentsInsertDTO("小明", 1, 2)
        );
        studentsInsertDTOList.add(
                new StudentsInsertDTO("小光", 3, 3)
        );

        int result = studentsDao.insertBatchStudentByEntity(studentsInsertDTOList);
        logger.debug("影响数据行数 -> {}", result);
        sqlSession.commit();
    }

    @Test
    public void insertStudentByMap() {
        StudentsDao studentsDao = sqlSession.getMapper(StudentsDao.class);

        List<StudentsInsertDTO> studentsInsertDTOList = new ArrayList<>();
        studentsInsertDTOList.add(
                new StudentsInsertDTO("小明", 1, 2)
        );
        studentsInsertDTOList.add(
                new StudentsInsertDTO("小光", 3, 3)
        );

        Map<String, Object> map = new HashMap<>();

        map.put("list", studentsInsertDTOList);
        map.put("entity", new StudentsInsertDTO("小新", 3, 10));
        map.put("a", "a");

        int result = studentsDao.insertStudentByMap(map);
        logger.debug("影响数据行数 -> {}", result);
        sqlSession.commit();
    }
}
