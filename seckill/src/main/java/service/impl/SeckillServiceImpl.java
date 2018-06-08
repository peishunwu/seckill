package service.impl;

import dao.SeckillDao;
import dao.SuccessKilledDao;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import entity.SuccessKilled;
import enums.SeckillStateEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/4
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5盐值字符串，用于混淆md5;
    private final String salt="jnqw&o4ut922v#y54vq34U#*mn4v";
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if(seckill == null){
            return new Exposer(false,seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();
        if(nowTime.getTime() < startTime.getTime() || nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    private String getMD5(long seckillId){
        String base = seckillId+"/"+salt;
        //通过盐值转化为加密数据
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
    //秒杀是否成功，成功：减库存，增加明细；失败：抛出异常，事务回滚
    /*使用注解控制事务方法的优点：
    1、开发团队达成一致约定，明确标注事务方法的编程风格
    2、保证事务方法的执行时间尽可能短，不要穿插其他网络操作（RPC/HTTP请求），或者剥离到事务方法外部
    3、不是所有的方法都需要事务，如只有一条修改操作或只读操作不需要事务控制*/
    @Transactional
    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("秒杀数据被重写了 (seckill data rewrite)");//秒杀数据被重写了
            //return new SeckillExecution(seckillId,SeckillStateEnum.DATA_REWRITE);
        }
        //执行秒杀逻辑：减库存+增加购买明细
        Date nowTime = new Date();
        try{
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount <= 0){
                //没有更新库存记录，说明秒杀结束
                throw new SeckillCloseException("说明秒杀结束(seckill is closed)");
                //return new SeckillExecution(seckillId,SeckillStateEnum.END);
            }else {
                //否则更新库存成功，秒杀成功，增加明细
                int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
               //看是否该明细被重复插入，即用户是否重复秒杀
                if(insertCount <= 0){
                    throw new RepeatKillException("重复秒杀（seckill repeated）");
                    //return new SeckillExecution(seckillId,SeckillStateEnum.REPEAT_KILL);
                }else{
                    //秒杀成功，得到成功插入的明细记录，并返回秒杀信息
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                    return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS);
                }
            }

        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            //所以编译期异常转化为运行期异常
            throw new SeckillException(""+e.getMessage());
        }
    }
}
