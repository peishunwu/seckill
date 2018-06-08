package exception;

/**
 * @Author:peishunwu
 * @Description:秒杀业务相关异常
 * @Date:Created  2018/6/4
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
