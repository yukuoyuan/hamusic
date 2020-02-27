package cn.yu.lib_network;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created on 2020-02-27
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class FileCallback implements Callback {


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        /*
         * 处理文件并且带进度
         */
        File file = handlerResponse(response);
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    private File handlerResponse(Response response) {
        /*
         * 文件路径
         */
        String filePath = null;
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
        long totalLength;
        try {
            file = new File(filePath);
            /*
             * 判断文件是否存在并创建
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
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, buffer.length);
                writeLength += length;
                long progress = writeLength / totalLength * 100;
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
