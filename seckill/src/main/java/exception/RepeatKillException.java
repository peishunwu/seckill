package exception;

/**
 * @Author:peishunwu
 * @Description:重复秒杀异常，是一个运行期异常，不需要我们手动try catch
 * mysql只支持运行期异常的回滚操作
 * @Date:Created  2018/6/4
 */
public class RepeatKillException extends SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
