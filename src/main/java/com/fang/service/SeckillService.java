package main.java.com.fang.service;

import java.util.List;

import main.java.com.fang.dto.Exposer;
import main.java.com.fang.dto.SeckillExecution;
import main.java.com.fang.entity.Seckill;
import main.java.com.fang.exception.RepeatKillException;
import main.java.com.fang.exception.SeckillClosedException;
import main.java.com.fang.exception.SeckillException;

/**
 * 业务接口:站在"使用者"的角度进行设计 三个方面:方法定义粒度,参数,返回类型 Created by Jameslin on 16/6/7.
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀产品
	 * 
	 * @return
	 */
	List<Seckill> getSeckillList();

	/**
	 * 通过秒杀产品ID获取库存商品详情
	 * 
	 * @param seckillId
	 *            产品ID
	 * @return
	 */
	Seckill getSeckillBySeckillId(Long seckillId);

	/**
	 * 秒杀开启时输出秒杀接口的地址, 否则输出系统时间和秒杀时间
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(Long seckillId);

	/**
	 * 执行秒杀
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution excuteSeckill(Long seckillId, Long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillClosedException;
}
