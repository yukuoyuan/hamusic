package cn.yu.lib_network.request;

import java.util.HashMap;

/**
 * Created on 2020-02-27
 * 这是一个请求参数类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class RequestParams {
    /**
     * 这是一个参数集合
     */
    private HashMap<String, String> params = new HashMap<>();
    /**
     * 这是一个文件集合
     */
    private HashMap<String, Object> fileParams = new HashMap<>();

    /**
     * 这是一个header集合
     */
    private HashMap<String, String> headers = new HashMap<>();

    /**
     * 这是一个请求路径
     */
    private String url;
    /**
     * 请求方式
     */
    private RequestMethod mRequestMethod;

    /**
     * 获取参数集合
     *
     * @return 参数集合
     */
    public HashMap<String, String> getParams() {
        return params;
    }

    /**
     * 获取文件参数集合
     *
     * @return 文件参数集合
     */
    public HashMap<String, Object> getFileParams() {
        return fileParams;
    }

    /**
     * 获取头数据集合
     *
     * @return 头数据集合
     */
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    /**
     * 添加参数
     *
     * @param paramKey   参数key
     * @param paramValue 参数value
     */
    public void putParam(String paramKey, String paramValue) {
        params.put(paramKey, paramValue);
    }

    /**
     * 添加文件参数
     *
     * @param fileParamKey   文件参数key
     * @param fileParamValue 文件参数value
     */
    public void putParam(String fileParamKey, Object fileParamValue) {
        fileParams.put(fileParamKey, fileParamValue);
    }

    /**
     * 添加头参数
     *
     * @param headerKey   头参数key
     * @param headerValue 头参数value
     */
    public void putHeader(String headerKey, String headerValue) {
        params.put(headerKey, headerValue);
    }

    /**
     * 获取请求路径
     *
     * @return 路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置请求路径
     *
     * @param url 路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取请求方式
     *
     * @return 请求方式, RequestMethod.GET 还是 RequestMethod.POST
     */
    public RequestMethod getRequestMethod() {
        return mRequestMethod;
    }

    /**
     * 设置请求方式
     *
     * @param requestMethod 请求方式
     */
    public void setRequestMethod(RequestMethod requestMethod) {
        mRequestMethod = requestMethod;
    }
}
