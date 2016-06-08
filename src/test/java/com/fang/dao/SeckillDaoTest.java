package test.java.com.fang.dao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import main.java.com.fang.dao.SeckillDao;
import main.java.com.fang.entity.Seckill;


//整合junit和spring，确保junit启动时，能够自动加载SpringIoc容器
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    private Logger log = LoggerFactory.getLogger(SeckillDaoTest.class);

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testInsertSeckill(){
        Seckill seckill = new Seckill();
        seckill.setName("999秒杀Le1Max");
        seckill.setNumber(100);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new Date());
        gc.add(5,-1);
        seckill.setStartTime(gc.getTime());
        gc.add(5,2);
        seckill.setEndTime(gc.getTime());
        seckill.setCreateTime(new Date());
        int res = seckillDao.insertSeckill(seckill);
        log.info(Integer.toString(res));
    }

    @Test
    public void testReduceNumberBySeckillId() {
        Long seckillId = 3L;
        int res = seckillDao.reduceNumberBySeckillId(seckillId,new Date());
        log.debug(res+"");
    }

    @Test
    public void testQuerySeckillBySeckillId() {
        Long seckillId = 1000L;
        Seckill seckill = seckillDao.querySeckillBySeckillId(seckillId);
        log.info(seckill.toString());
    }

    @Test
    public void testQueryAllSeckills() {
        List<Seckill> seckills = seckillDao.queryAllSeckills(0, 5);
        for (Seckill seckill : seckills) {
            log.debug(seckill.toString());
        }
    }

}
