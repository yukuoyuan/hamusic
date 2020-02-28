package cn.yu.lib_network.listeners;

/**
 * Created on 2020-02-28
 * 这是一个上传文件的回调
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public interface UploadFileListener extends CommonResponseListener {
    /**
     * 这是一个上传文件回调进度的方法
     *
     * @param progress 进度
     */
    void onProgress(int progress);
}
