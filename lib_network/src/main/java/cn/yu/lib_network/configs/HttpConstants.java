package cn.yu.lib_network.configs;

/**
 * Created on 2020-02-28
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class HttpConstants {
    /**
     * io异常错误码
     */
    public static final int IO_ERROR = 0x001;
    /**
     * 网络异常错误码
     */
    public static final int NET_WORK_ERROR = 0x002;
    /**
     * 解析异常错误码
     */
    public static final int JSON_ERROR = 0x003;
    /**
     * io异常的错误信息
     */
    public static final String IO_ERROR_MSG = "";
    /**
     * 空信息错误信息
     */
    public static final String EMPTY_MSG = "";
    /**
     * 解析异常错误信息
     */
    public static final String JSON_ERROR_MSG = "";
    /**
     * 网络读取超时时间
     */
    public static final long READ_TIME_OUT = 30;

    /**
     * 网络连接超时时间
     */
    public static final long CONNECT_TIME_OUT = 30;
    /**
     * 网络写入超时时间
     */
    public static final long WRITE_TIME_OUT = 30;
}
