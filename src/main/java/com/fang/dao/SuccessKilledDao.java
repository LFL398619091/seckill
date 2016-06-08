package main.java.com.fang.dao;

import main.java.com.fang.entity.SuccessKilledVo;
import org.apache.ibatis.annotations.Param;

public interface SuccessKilledDao {
	/**
	 * 添加购买明细，可过滤重复
	 * @param seckillId 库存商品ID
	 * @param userPhone 用户手机号
	 * @return
	 */
	int insertSuccessKilled(@Param("seckillId") Long seckillId,@Param("userPhone") Long userPhone);
	
	/**
	 * 根据库存商品ID查询购买明细，并且携带库存商品详情
	 * @param seckillId 库存商品ID
	 * @return
	 */
	SuccessKilledVo querySuccessKilledById(@Param("seckillId") Long seckillId, @Param("userPhone") Long userPhone);
}
