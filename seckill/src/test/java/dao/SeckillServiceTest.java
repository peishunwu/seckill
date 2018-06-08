package dao;


import com.alibaba.fastjson.JSON;
import dto.Exposer;
import dto.SeckillExecution;
import entity.Seckill;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.SeckillService;

import java.util.List;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);

    @Autowired
    private SeckillService seckillService;


    @Test
    public void getSeckillListTest()throws Exception{
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list="+list.toString());
        System.out.println(list.toString());
        for(Seckill sk : list){
            System.out.println(sk);
        }
    }

    //完整的逻辑代码集成测试
    @Test
    public void testExportSeckillLogic()throws Exception{
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        //判断秒杀是否开启，如果开启则保留地址和盐值md5开始秒杀
        if(exposer.isExposed()){

            System.out.println(exposer);

            long userPhone = 15718879112L;
            String md5 = exposer.getMd5();
            try{
                SeckillExecution seckillExecution = seckillService.executeSeckill(id,userPhone,md5);
                logger.info("秒杀结果SeckillExecution："+ JSON.toJSONString(seckillExecution));
            }catch (RepeatKillException e){
                e.printStackTrace();
            }catch (SeckillCloseException e1){
                e1.printStackTrace();
            }
        }else {
            System.out.println("秒杀未开启");
        }
    }

    //单独执行测试
    @Test
    public void testExecuteSeckill()throws Exception{
        long id = 1001;
        long phone = 15718879112L;
        String md5 = "1da8af7e7ad6829f9eb2e6f18cb45225";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}",seckillExecution);
            System.out.println(seckillExecution);
        }catch (RepeatKillException e)
        {
            e.printStackTrace();
        }catch (SeckillCloseException e1)
        {
            e1.printStackTrace();
        }
    }
}

