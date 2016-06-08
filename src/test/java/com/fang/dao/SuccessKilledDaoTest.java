package test.java.com.fang.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import main.java.com.fang.dao.SuccessKilledDao;
import main.java.com.fang.entity.SuccessKilledVo;

/**
 * Created by Jameslin on 16/6/7.
 */

/**
 * 为了确保在JUnit启动时,SpringIoc容器也随之启动
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    private Logger log = LoggerFactory.getLogger(SuccessKilledDaoTest.class);

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int res = successKilledDao.insertSuccessKilled(1L,15313118386L);
        log.info(Integer.toString(res));
    }

    @Test
    public void querySuccessKilledById() throws Exception {
        SuccessKilledVo successKilledVo = successKilledDao.querySuccessKilledById(1L,15313118386L);
        System.out.println(successKilledVo.getSeckill());
        System.out.println("");
    }

}