package service;

import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;

import java.util.List;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/4
 */
public interface SeckillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill  getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     * @throws SeckillException 秒杀业务相关异常
     * @throws RepeatKillException 秒杀重复异常
     * @throws SeckillCloseException 秒杀关闭异常
     *
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)throws SeckillException, RepeatKillException, SeckillCloseException;
}
