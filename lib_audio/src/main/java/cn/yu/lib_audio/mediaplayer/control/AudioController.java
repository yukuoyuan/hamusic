package cn.yu.lib_audio.mediaplayer.control;

import java.util.ArrayList;

import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.mediaplayer.core.AudioPlayer;

/**
 * Created on 2020-03-05
 * 这是一个控制播放逻辑类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioController {
    /**
     * 得到一个播放器
     */
    private final AudioPlayer mAudioPlayer;
    /**
     * 播放的索引,默认从第一个开始
     */
    private int mQueueIndex = 0;
    /**
     * 队列数据
     */
    private ArrayList<AudioBean> mAudioBeanArrayList;
    /**
     * 播放模式,默认是列表循环
     */
    private PlayMode mPlayMode = PlayMode.LOOP;

    /**
     * 播放方式
     */
    public enum PlayMode {
        /**
         * 循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT

    }

    private static AudioController mAudioController = null;

    private AudioController() {
        mAudioPlayer = new AudioPlayer();
    }

    public static AudioController getInstance() {
        synchronized (AudioController.class) {
            if (mAudioController == null) {
                mAudioController = new AudioController();
            }
        }
        return mAudioController;
    }

    private AudioBean getNowPlaying(int queueIndex) {
        return null;
    }

    /**
     * 开始播放
     */
    public void start() {
        AudioBean audioBean = getNowPlaying(mQueueIndex);
        mAudioPlayer.load(audioBean);
    }


    /**
     * 暂停播放
     */
    public void pause() {
        mAudioPlayer.pause();
    }

    /**
     * 继续播放
     */
    public void resume() {
        mAudioPlayer.resume();
    }

    /**
     * 释放资源
     */
    public void release() {
        mAudioPlayer.resume();
    }
}
