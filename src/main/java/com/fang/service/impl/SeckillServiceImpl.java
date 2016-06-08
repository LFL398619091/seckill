package main.java.com.fang.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import main.java.com.fang.dao.SeckillDao;
import main.java.com.fang.dao.SuccessKilledDao;
import main.java.com.fang.dto.Exposer;
import main.java.com.fang.dto.SeckillExecution;
import main.java.com.fang.entity.Seckill;
import main.java.com.fang.entity.SuccessKilledVo;
import main.java.com.fang.enums.SeckillStateEnum;
import main.java.com.fang.exception.RepeatKillException;
import main.java.com.fang.exception.SeckillClosedException;
import main.java.com.fang.exception.SeckillException;
import main.java.com.fang.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {
	// 日志类
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private SuccessKilledDao successKilledDao;

	// md5盐值字符串
	private final String salt = "sds!@43QWjkldf787%~%^&*(*)32lkdKKH&&21";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAllSeckills(0, 9);
	}

	@Override
	public Seckill getSeckillBySeckillId(Long seckillId) {
		return seckillDao.querySeckillBySeckillId(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(Long seckillId) {
		Seckill seckill = getSeckillBySeckillId(seckillId);
		if (seckill == null) {
			return new Exposer(false, seckillId);
		}
		// 库存已经没有了
		if (seckill.getNumber() <= 0) {
			return new Exposer(false, seckillId);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 系统当前时间
		Date nowTime = new Date();
		// 不在秒杀规定时间范围内
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMd5(seckillId.toString());
		return new Exposer(true, md5, seckillId);
	}

	@Override
	@Transactional
	/**
	 * 使用注解来管理事务的有点：
	 * 1:开发团队达成一致的约定，明确标注事务方法的编程风格
	 * 2:保证事务方法的执行时间尽可能短，不要穿插其他的网络操作，RPC／Http请求，或者剥离到事务方法外头
	 * 3:不是所有的方法都需要事务，如只有一条修改操作或者式只读操作是不需要事务控制的
	 */
	public SeckillExecution excuteSeckill(Long seckillId, Long userPhone, String md5)
			throws SeckillException, RepeatKillException, SeckillClosedException {
		if (md5 == null || !md5.equals(getMd5(seckillId.toString()))) {
			throw new SeckillException("seckill data rewrite");
		}
		// 执行秒杀逻辑：减库存＋添加购买行为
		Date nowTime = new Date();
		try {
			// 减库存
			int updateCount = seckillDao.reduceNumberBySeckillId(seckillId, nowTime);
			if (updateCount <= 0) {
				// 没有更新纪录,标示秒杀结束
				throw new SeckillClosedException("seckill is closed");
			} else {
				// 纪录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				// 唯一验证，根据联合组建：id＋phone（insert ignore into。。。）
				if (insertCount <= 0) {
					// 重复秒杀
					throw new RepeatKillException("seckill repeated");
				} else {
					// 秒杀成功
					SuccessKilledVo successKilledVo = successKilledDao.querySuccessKilledById(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilledVo);
				}
			}
		} catch (SeckillClosedException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			// 所有编译期异常转化为运行时异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	private String getMd5(String source) {
		String base = source + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

}
