package dao;

import entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/1
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，过滤重复（联合唯一主键）
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象体
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
