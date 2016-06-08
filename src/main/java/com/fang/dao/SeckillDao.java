package main.java.com.fang.dao;

import java.util.Date;
import java.util.List;

import main.java.com.fang.entity.Seckill;
import org.apache.ibatis.annotations.Param;

public interface SeckillDao {
    /**
     * 添加库存
     * @param seckill 库存商品实例
     * @return
     */
    int insertSeckill(Seckill seckill);

    /**
     * 减库存
     *
     * @param seckillId 库存商品ID
     * @param killTime  秒杀时间
     * @return
     */
    int reduceNumberBySeckillId(@Param("seckillId") Long seckillId, @Param("killTime") Date killTime);

    /**
     * 通过秒杀商品ID，查询秒杀商品详情
     *
     * @param seckillId 库存商品ID
     * @return
     */
    Seckill querySeckillBySeckillId(Long seckillId);

    /**
     * 获取所有的秒杀商品（带分页）
     *
     * @param offSet 偏移量
     * @param limit  每页大小
     * @return java运行过程中不会保存形参纪录=>arg0,arg1,所以此处我们使用MyBatis提供的@Param注解,显式的告诉
     * MyBatis,进行响应的参数绑定
     */
    List<Seckill> queryAllSeckills(@Param("offSet") int offSet, @Param("limit") int limit);
}
