package exception;

/**
 * @Author:peishunwu
 * @Description:秒杀关闭异常，当秒杀结束时候用户还要进行秒杀就会出现这个异常
 * @Date:Created  2018/6/4
 */
public class SeckillCloseException extends SeckillException{
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
