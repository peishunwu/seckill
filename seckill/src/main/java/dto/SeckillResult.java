package dto;

/**
 * 秒杀结果分装泛型类型类
 * //所有ajax请求的返回类型，封装为json结果类型
 * @Author:peishunwu
 * @Description:
 * @Date:Created  2018/6/5
 */
public class SeckillResult<T> {
    private boolean Success;

    private T data;

    private String error;

    public SeckillResult(boolean success, T data) {
        Success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        Success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
