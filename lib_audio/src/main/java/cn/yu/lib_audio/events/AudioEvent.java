package cn.yu.lib_audio.events;


import cn.yu.lib_audio.bean.AudioBean;

/**
 * Created on 2020-03-05
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioEvent {
    /**
     * 各种状态
     */
    public enum AudioEventStatus {
        /**
         * 加载
         */
        LOAD,
        /**
         * 开始
         */
        START,
        /**
         * 暂停
         */
        PAUSE,
        /**
         * 播放完成
         */
        COMPLETE,
        /**
         * 释放
         */
        RELEASE,
        /**
         * 播放错误
         */
        ERROR,
        /**
         * 收藏歌曲
         */
        FAVORITE,
        /**
         * 取消收藏
         */
        CANCEL_FAVORITE,
        /**
         * 进度
         */
        PROGRESS
    }

    /**
     * 播放器状态
     */
    private AudioEventStatus status;
    /**
     * 播放数据
     */
    private AudioBean audioBean;
    /**
     * 播放的位置
     */
    private int currentPosition;
    /**
     * 总的时长
     */
    private int duration;

    public AudioEvent(AudioEventStatus status, AudioBean audioBean) {
        this.status = status;
        this.audioBean = audioBean;
    }

    public AudioEvent(AudioEventStatus status, int currentPosition, int duration) {
        this.status = status;
        this.currentPosition = currentPosition;
        this.duration = duration;
    }

    /**
     * 事件状态
     *
     * @return 状态
     */
    public AudioEventStatus getStatus() {
        return status;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public AudioBean getAudioBean() {
        return audioBean;
    }
}
