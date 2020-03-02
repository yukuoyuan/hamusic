package cn.yu.lib_network;

import java.util.concurrent.TimeUnit;

import cn.yu.lib_network.configs.HttpConstants;
import cn.yu.lib_network.request.CommonRequest;
import cn.yu.lib_network.request.RequestParams;
import cn.yu.lib_network.response.DisposeDataHandle;
import cn.yu.lib_network.response.DownloadFileCallback;
import cn.yu.lib_network.response.ResponseJsonCallBack;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created on 2020-02-28
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class CommonOkHttpClient {

    private static OkHttpClient mOkHttpClient;

    /*
     *静态构造方法,只会创建一次
     */
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        /*
         * 设置读取超时时间,单位是秒
         */
        okHttpClientBuilder.readTimeout(HttpConstants.READ_TIME_OUT, TimeUnit.SECONDS);
        /*
         * 连接超时时间
         */
        okHttpClientBuilder.connectTimeout(HttpConstants.CONNECT_TIME_OUT, TimeUnit.SECONDS);
        /*
         * 写入超时时间
         */
        okHttpClientBuilder.writeTimeout(HttpConstants.WRITE_TIME_OUT, TimeUnit.SECONDS);
        /*
         * 可以设置其他的参数,例如ssl,cookie,公共请求头
         */
        /*
         * 得到
         */
        mOkHttpClient = okHttpClientBuilder.build();
    }

    private static CommonOkHttpClient mCommonOkHttpClient = null;

    private CommonOkHttpClient() {
    }

    public static CommonOkHttpClient getInstance() {
        synchronized (CommonOkHttpClient.class) {
            if (mCommonOkHttpClient == null) {
                mCommonOkHttpClient = new CommonOkHttpClient();
            }
        }
        return mCommonOkHttpClient;
    }

    /**
     * 发送一个异步的get请求
     *
     * @param requestParams     请求参数
     * @param disposeDataHandle 包含回调等包装
     */
    public void get(RequestParams requestParams, DisposeDataHandle disposeDataHandle) {
        Call getCall = mOkHttpClient.newCall(CommonRequest.getInstance().createGetRequest(requestParams));
        getCall.enqueue(new ResponseJsonCallBack(disposeDataHandle));
    }

    /**
     * 发送一个异步的post表单的请求
     *
     * @param requestParams     请求参数
     * @param disposeDataHandle 包含回调等包装
     */
    public void postForm(RequestParams requestParams, DisposeDataHandle disposeDataHandle) {
        Call postFormCall = mOkHttpClient.newCall(CommonRequest.getInstance().createPostFormRequest(requestParams));
        postFormCall.enqueue(new ResponseJsonCallBack(disposeDataHandle));
    }

    /**
     * 发送一个异步的postjson的请求
     *
     * @param requestParams     请求参数
     * @param disposeDataHandle 包含回调等包装
     */
    public void postJson(RequestParams requestParams, DisposeDataHandle disposeDataHandle) {
        Call posJsonCall = mOkHttpClient.newCall(CommonRequest.getInstance().createPostJsonRequest(requestParams));
        posJsonCall.enqueue(new ResponseJsonCallBack(disposeDataHandle));
    }

    /**
     * 下载文件
     *
     * @param requestParams     请求参数
     * @param disposeDataHandle 包含回调进度,以及本地存储路径等的包装
     */
    public void downloadFile(RequestParams requestParams, DisposeDataHandle disposeDataHandle) {
        Call downloadFile = mOkHttpClient.newCall(CommonRequest.getInstance().createGetRequest(requestParams));
        downloadFile.enqueue(new DownloadFileCallback(disposeDataHandle));
    }


    /**
     * 这是一个上传文件带进度的请求
     *
     * @param requestParams     请求参数
     * @param disposeDataHandle 包含回调等的包装
     */
    public void uploadFileProgress(RequestParams requestParams, DisposeDataHandle disposeDataHandle) {
        Call uploadFileCall = mOkHttpClient.newCall(CommonRequest.getInstance().createMultipartRequest(requestParams, disposeDataHandle));
        uploadFileCall.enqueue(new ResponseJsonCallBack(disposeDataHandle));
    }
}
