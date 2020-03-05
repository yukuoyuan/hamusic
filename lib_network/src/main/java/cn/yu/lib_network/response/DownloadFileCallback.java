package cn.yu.lib_network.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.yu.lib_network.configs.HttpConstants;
import cn.yu.lib_network.exceptions.OkHttpException;
import cn.yu.lib_network.listeners.DownloadFileListener;
import cn.yu.lib_network.utils.FileUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created on 2020-02-27
 * 这是一个下载文件带进度的回调
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class DownloadFileCallback implements Callback {
    /**
     * 用来处理异步消息
     */
    private Handler mHandler;
    /**
     * 通用的回调监听
     */
    private DownloadFileListener mCommonResponseListener;

    /**
     * 发送下载进度的消息标识
     */
    private static final int PROGRESS_MESSAGE = 0x01;
    /**
     * 文件路径
     */
    private String filePath;

    public DownloadFileCallback(DisposeDataHandle disposeDataHandle) {
        this.mCommonResponseListener = (DownloadFileListener) disposeDataHandle.mCommonResponseListener;
        this.filePath = disposeDataHandle.source;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        if (mCommonResponseListener != null) {
                            mCommonResponseListener.onProgress((int) msg.obj);
                        }
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(Call call, IOException ioException) {
        if (mCommonResponseListener != null) {
            mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.NET_WORK_ERROR, ioException));
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        /*
         * 处理文件并且带进度
         */
        final File file = handlerResponse(response);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    /*
                     * 写入文件成功了
                     */
                    if (mCommonResponseListener != null) {
                        mCommonResponseListener.onSuccess(file);
                    }
                } else {
                    /*
                     * 写入失败了
                     */
                    if (mCommonResponseListener != null) {
                        mCommonResponseListener.onFailure(new OkHttpException(HttpConstants.IO_ERROR, HttpConstants.IO_ERROR_MSG));
                    }
                }

            }
        });

    }

    /**
     * 处理响应流
     *
     * @param response 响应
     * @return 写好的本地文件
     */
    private File handlerResponse(Response response) {

        File file;
        /*
         * 每次读取的长度
         */
        byte[] buffer = new byte[2048];
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        /*
         * 已经写入的长度
         */
        long writeLength = 0;
        /*
         * 总的长度
         */
        final long totalLength;
        try {

            /*
             * 判断文件是否存在并创建
             */
            FileUtils.getInstance().checkLocalFilePath(filePath);
            /*
             * 得到本地的一个空文件
             */
            file = new File(filePath);
            /*
             * 准备写入文件
             */
            fileOutputStream = new FileOutputStream(file);
            /*
             * 获取输入流
             */
            inputStream = response.body().byteStream();
            /*
             * 获取总的长度
             */
            totalLength = response.body().contentLength();
            /*
             * 读取的长度
             */
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, buffer.length);
                writeLength += length;
                int mProgress = (int) (writeLength / totalLength * 100);
                /*
                 * 把进度发消息给handler进行处理
                 */
                mHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }
            fileOutputStream.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            /*
             * 关闭流
             */
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
