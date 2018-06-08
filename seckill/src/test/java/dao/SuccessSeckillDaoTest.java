package dao;

import entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.alibaba.fastjson.JSONObject;
import javax.annotation.Resource;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
/*告诉Junit Spring的配置文件*/
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessSeckillDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;
    /*
     *inserCount=0:已经插入相同记录
     *inserCount=1:当前执行操作插入了一条记录
     */
    @Test
    public void insertSuccessKilledTest()throws Exception{
        long seckillId = 1002;
        long userPhone = 15718879112L;
        int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount:"+insertCount);
    }

    @Test
    public void queryByIdWithSeckill()throws Exception{
        long seckillId = 1002;
        long userPhone = 15718879112L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(JSONObject.toJSONString(successKilled));
    }
}
