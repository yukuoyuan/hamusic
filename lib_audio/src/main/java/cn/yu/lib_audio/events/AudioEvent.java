package cn.yu.lib_audio.events;


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
        ERROR
    }

    /**
     * 播放器状态
     */
    private AudioEventStatus status;

    public AudioEvent(AudioEventStatus status) {
        this.status = status;
    }

    public AudioEventStatus getStatus() {
        return status;
    }
}
