package web;

import com.alibaba.fastjson.JSON;
import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillResult;
import entity.Seckill;
import enums.SeckillStateEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * Controller中的方法完全按照Service接口中的方法进行开发的：
 * 第一个方法：用于访问我们商品的列表页
 * 第二个方法：访问商品的详情页
 * 第三个方法：返回Json数据，封装了我们商品的秒杀地址
 * 第四个方法：用于封装用户是否秒杀成功的信息
 * 第五个方法：返回系统当前时间
 * @Author:peishunwu
 * @Description:
 * @Date:Created 2018/6/5
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(SeckillController.class);
    @Autowired
    private SeckillService seckillService;

    /**
     * 获取秒杀列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        System.out.println("======================"+model);
        //list.jsp+model=ModelAndView
        return "list";
    }

    /**
     * 通过id获取秒杀详情
     * @param seckillId
     * @param model
     * @return
     */
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){
        //请求不存在的时候，直接重定向回到列表页
        if(seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        System.out.println("seckill:"+ JSON.toJSONString(seckill));
        if(seckill == null){
            //如果请求对象不存在
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }


    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;
        try{
            //Exposer:存放是否秒杀的状态信息
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;

    }
    /*
     * md5：验证用户的请求有没有被篡改
     * 默认的ajax输出是Json格式，所以将输出结果都封装成Json格式。
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        SeckillResult<SeckillExecution> result;
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,phone,md5);
            result = new SeckillResult<SeckillExecution>(true,seckillExecution);
            logger.info("秒杀成功结果：{}",JSON.toJSONString(result));
        }catch (RepeatKillException e1){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            result = new SeckillResult<SeckillExecution>(false,seckillExecution);
        }catch (SeckillCloseException e2) {
            SeckillExecution execution = new SeckillExecution(seckillId,
                    SeckillStateEnum.END);
            result = new SeckillResult<SeckillExecution>(false, execution);
        } catch (Exception e) {
            SeckillExecution execution = new SeckillExecution(seckillId,
                    SeckillStateEnum.INNER_ERROR);
            result = new SeckillResult<SeckillExecution>(false, execution);
        }
        logger.info("秒杀结果：{}",JSON.toJSONString(result));
        return  result;
    }

    /**
     * 获取系统时间
     * @return
     */
    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now = new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
