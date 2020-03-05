package cn.yu.lib_audio.events;


/**
 * Created on 2020-03-05
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioEvent {
    public enum AudioEventStatus {
        /*
         * 各种状态
         */
        LOAD,
        START,
        PAUSE,
        RELEASE,
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
