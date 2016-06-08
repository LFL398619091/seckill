package test.java.com.fang.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import main.java.com.fang.dto.Exposer;
import main.java.com.fang.dto.SeckillExecution;
import main.java.com.fang.entity.Seckill;
import main.java.com.fang.exception.RepeatKillException;
import main.java.com.fang.exception.SeckillClosedException;
import main.java.com.fang.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-service.xml", "classpath:spring/spring-dao.xml" })
public class SeckillServiceTest {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		for (Seckill seckill : seckills) {
			log.info(seckill.toString());
		}
	}

	@Test
	public void testGetSeckillBySeckillId() {
		Seckill seckill = seckillService.getSeckillBySeckillId(2L);
		log.info("{}",seckill);
	}

	@Test
	public void testExportSeckillUrl() {
		Exposer exportSeckillUrl = seckillService.exportSeckillUrl(7L);
		log.info(exportSeckillUrl.toString());
	}

	@Test
	/**
	 * 测试代码完整逻辑，注意可重复执行
	 */
	public void testExcuteSeckill()  {
		try {
			Exposer exposer = seckillService.exportSeckillUrl(1L);
			if (exposer.isExposed()) {
				log.info("{}",exposer);
				SeckillExecution seckillExecution = seckillService.excuteSeckill(1L,15313118373L,exposer.getMd5());
				log.info("{}",seckillExecution);
			}else{
				log.warn("{}",exposer);
			}
		} catch(RepeatKillException e1){
			log.error("{}",e1.getMessage());
		}catch (SeckillClosedException e2) {
			log.error("{}",e2.getMessage());
		}catch (Exception e) {
			log.error("{}",e.getMessage());
		}
	}

}
