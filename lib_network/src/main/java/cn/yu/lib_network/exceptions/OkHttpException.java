package cn.yu.lib_network.exceptions;

/**
 * Created on 2020-02-28
 * 这是一个自定义的异常
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class OkHttpException extends Exception {
    /**
     * 异常码
     */
    private int errorCode;
    /**
     * 异常信息
     */
    private Object errorMsg;

    /**
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public OkHttpException(int errorCode, Object errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 获取异常码
     *
     * @return 异常码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取异常信息
     *
     * @return 信息
     */
    public Object getErrorMsg() {
        return errorMsg;
    }
}
