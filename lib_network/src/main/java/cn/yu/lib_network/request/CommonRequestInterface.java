package cn.yu.lib_network.request;

import cn.yu.lib_network.response.DisposeDataHandle;
import okhttp3.Request;

/**
 * Created on 2020-02-27
 * 这是一个通用请求的接口类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public interface CommonRequestInterface {
    /**
     * 创建一个发送form表单的post请求
     *
     * @param requestParams 请求参数,包含body参数,head参数 url参数,注意*****请求方式设置无效****
     * @return request一个请求
     */
    Request createPostFormRequest(RequestParams requestParams);

    /**
     * 创建一个发送json的post请求
     *
     * @param requestParams 请求参数,包含body参数,head参数 url参数,注意*****请求方式设置无效****
     * @return request一个请求
     */
    Request createPostJsonRequest(RequestParams requestParams);

    /**
     * 创建一个get请求
     *
     * @param requestParams 请求参数,包含body参数,head参数 url参数,注意*****请求方式设置无效****
     * @return request 一个请求
     */
    Request createGetRequest(RequestParams requestParams);

    /**
     * 创建一个通用的请求
     *
     * @param requestParams 请求参数,包含body参数,head参数 url参数,请求方式
     * @return request 一个请求
     */
    Request createRequest(RequestParams requestParams);

    /**
     * 创建一个文件上传请求,带回调
     *
     * @param requestParams     请求参数,包含body参数,head参数 url参数,请求方式
     * @param disposeDataHandle 带回调的封装
     * @return request 一个请求
     */
    Request createMultipartRequest(RequestParams requestParams, DisposeDataHandle disposeDataHandle);
}
