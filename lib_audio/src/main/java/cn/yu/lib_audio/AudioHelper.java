package cn.yu.lib_audio;


import android.content.Context;

/**
 * Created on 2020-03-05
 * 这是该module与外界通信的唯一桥梁类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioHelper {

    private static AudioHelper mAudioHelper = null;
    /**
     * 该module下的全局上下文
     */
    private Context context;

    private AudioHelper() {
    }

    public static AudioHelper getInstance() {
        synchronized (AudioHelper.class) {
            if (mAudioHelper == null) {
                mAudioHelper = new AudioHelper();
            }
        }
        return mAudioHelper;
    }

    /**
     * 设置该module下的全局上下文
     *
     * @param context 上下文
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public Context getContext() {
        return context;
    }
}
