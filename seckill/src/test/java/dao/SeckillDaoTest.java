package dao;


import entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/1
 * 配置Spring和Junit整合，Junit启动时加载SPringIOC容器
 * spring-test,junit:spring测试的依赖
 * 1:RunWith:Junit本身需要的依赖
 */
@RunWith(SpringJUnit4ClassRunner.class)
//2:告诉Junit Spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //3:注入Dao实现类依赖  --会自动去Spring容器中查找seckillDao的实现类注入到单元测试类
    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testQueryById()throws Exception{
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void reduceNumber()throws Exception{
        long seckillId = 1000;
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId,killTime);
        System.out.println(updateCount);
    }

}
