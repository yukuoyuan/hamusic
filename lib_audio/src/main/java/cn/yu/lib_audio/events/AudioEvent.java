package cn.yu.lib_audio.events;


import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.mediaplayer.control.AudioController;

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
        PROGRESS,
        /**
         * 播放模式
         */
        PLAY_MODE
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
    /**
     * 播放模式
     */
    private AudioController.PlayMode playMode;

    public AudioEvent(AudioEventStatus status, AudioBean audioBean) {
        this.status = status;
        this.audioBean = audioBean;
    }

    public AudioEvent(AudioEventStatus status, int currentPosition, int duration) {
        this.status = status;
        this.currentPosition = currentPosition;
        this.duration = duration;
    }

    public AudioEvent(AudioEventStatus status, AudioController.PlayMode playMode) {
        this.status = status;
        this.playMode = playMode;
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

    /**
     * 获取当前的进度
     *
     * @return 当前的进度
     */
    public int getCurrentPosition() {
        return currentPosition;
    }

    /**
     * 获取总的长度
     *
     * @return 长度
     */
    public int getDuration() {
        return duration;
    }
}
