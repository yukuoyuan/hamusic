package cn.yu.lib_network.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import cn.yu.lib_network.configs.HttpConstants;
import cn.yu.lib_network.exceptions.OkHttpException;
import cn.yu.lib_network.listeners.CommonResponseListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created on 2020-02-28
 * 这是一个响应json带解析的回调
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class ResponseJsonCallBack implements Callback {
    /**
     * 得到一个主线程的handler
     */
    private Handler mHandler;
    /**
     * 需要解析的对应的model
     */
    private Class<?> mClass;
    /**
     * 回调监听
     */
    private CommonResponseListener mCommonResponseListener;

    public ResponseJsonCallBack(DisposeDataHandle disposeDataHandle) {
        /*
         * 需要解析的对应的model
         */
        this.mClass = disposeDataHandle.clazz;
        /*
         * 回调
         */
        this.mCommonResponseListener = disposeDataHandle.mCommonResponseListener;

        /*
         * 得到一个主线程的handler
         */
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException ioException) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mCommonResponseListener != null) {
                    mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.NET_WORK_ERROR, ioException));
                }
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        /*
         * 响应返回的字符串
         */
        final String responseString = response.body().string();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handlerResponse(responseString);
            }
        });

    }

    /**
     * 处理响应信息
     *
     * @param responseString 响应信息
     */
    private void handlerResponse(String responseString) {
        /*
         * 代表没有返回数据的话,回调失败方法
         */
        if (TextUtils.isEmpty(responseString)) {
            if (mCommonResponseListener != null) {
                mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.NET_WORK_ERROR, HttpConstants.EMPTY_MSG));
            }
            return;
        }
        try {

            /*
             * 不需要解析的话
             */
            if (mClass == null) {
                JSONObject jsonObject = new JSONObject(responseString);
                if (mCommonResponseListener != null) {
                    mCommonResponseListener.onSuccess(jsonObject);
                }
            } else {
                /*
                 * 需要进行解析的话
                 */
                Object object = new Gson().fromJson(responseString, mClass);
                if (object != null) {
                    /*
                     * 解析成功了
                     */
                    if (mCommonResponseListener != null) {
                        mCommonResponseListener.onSuccess(object);
                    }
                } else {
                    /*
                     * 解析失败了
                     */
                    if (mCommonResponseListener != null) {
                        mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.JSON_ERROR, HttpConstants.JSON_ERROR_MSG));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            /*
             * 解析失败了
             */
            if (mCommonResponseListener != null) {
                mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.JSON_ERROR, HttpConstants.JSON_ERROR_MSG));
            }
        }
    }
}
