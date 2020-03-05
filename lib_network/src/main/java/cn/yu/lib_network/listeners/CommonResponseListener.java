package cn.yu.lib_network.listeners;

/**
 * Created on 2020-02-28
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public interface CommonResponseListener {
    /**
     * 成功的回调
     *
     * @param successObj 返回解析好的实体
     */
    void onSuccess(Object successObj);

    /**
     * 失败的回调
     *
     * @param failureObj 失败的实体
     */
    void onFailure(Object failureObj);
}
