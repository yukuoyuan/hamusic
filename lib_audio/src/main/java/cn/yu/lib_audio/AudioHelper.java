package cn.yu.lib_audio;


import android.content.Context;

import java.util.ArrayList;

import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.servicess.MusicService;

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
     * 启动播放服务
     *
     * @param audios 数据
     */
    public void startMusicService(ArrayList<AudioBean> audios) {
        MusicService.startMusicService(audios);
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
