package cn.yu.lib_network.response;

import cn.yu.lib_network.listeners.CommonResponseListener;

/**
 * Created on 2020-02-28
 * 这个类存在的意义是为了方便以后扩展,类似于Bundle
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class DisposeDataHandle {
    /**
     * 回调监听
     */
    public CommonResponseListener mCommonResponseListener;
    /**
     * 如果数据需要解析的话,传递
     */
    Class<?> clazz;

    /**
     * 下载文件的时候本地存储的文件路径
     */
    String source;

    /**
     * 传递一个回调
     *
     * @param commonResponseListener 回调
     */
    public DisposeDataHandle(CommonResponseListener commonResponseListener) {
        this.mCommonResponseListener = commonResponseListener;
    }

    /**
     * 传递一个回调和解析的数据类型
     *
     * @param commonResponseListener 回调
     * @param clazz                  数据类型
     */
    public DisposeDataHandle(CommonResponseListener commonResponseListener, Class<?> clazz) {
        this.mCommonResponseListener = commonResponseListener;
        this.clazz = clazz;
    }

    /**
     * 传递一个回调和本地存储文件的路径
     *
     * @param commonResponseListener 回调
     * @param source                 本地文件路径
     */
    public DisposeDataHandle(CommonResponseListener commonResponseListener, String source) {
        this.mCommonResponseListener = commonResponseListener;
        this.source = source;
    }
}
