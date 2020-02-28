package cn.yu.lib_network.request;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import cn.yu.lib_network.listeners.UploadFileListener;
import cn.yu.lib_network.response.DisposeDataHandle;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * Created on 2020-02-28
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MultipartRequestBody extends RequestBody {
    /**
     * 请求体
     */
    private MultipartBody requestBody;
    /**
     * 上传的长度
     */
    private long currentLength;
    /**
     * 文件总的长度
     */
    private long totalLength;
    /**
     * 这是一个上传文件成功的回调
     */
    private UploadFileListener mUploadFileListener;
    /**
     * 处理进度的handler
     */
    private Handler mHandler;
    /**
     * 发送上传进度的消息标识
     */
    private static final int PROGRESS_MESSAGE = 0x01;

    public MultipartRequestBody(MultipartBody requestBody) {
        this.requestBody = requestBody;
    }

    public MultipartRequestBody(MultipartBody requestBody, DisposeDataHandle disposeDataHandle) {
        this.requestBody = requestBody;
        this.mUploadFileListener = (UploadFileListener) disposeDataHandle.mCommonResponseListener;
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                    default:
                        if (mUploadFileListener != null) {
                            mUploadFileListener.onProgress((int) msg.obj);
                        }
                        break;
                }
            }
        };
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        totalLength = requestBody.contentLength();
        return totalLength;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                currentLength += byteCount;
                /*
                 * 上传的进度
                 */
                int progress = (int) (currentLength / totalLength * 100);
                /*
                 * 回调进度
                 */
                mHandler.obtainMessage(PROGRESS_MESSAGE, progress).sendToTarget();

            }
        };
        //转一下
        BufferedSink bufferedSink = Okio.buffer(forwardingSink);
        //写数据
        requestBody.writeTo(bufferedSink);
        //刷新一下数据
        bufferedSink.flush();
    }
}
