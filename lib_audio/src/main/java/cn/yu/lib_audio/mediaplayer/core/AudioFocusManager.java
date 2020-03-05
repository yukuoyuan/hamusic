package cn.yu.lib_audio.mediaplayer.core;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created on 2020-03-05
 * 这是一个语音焦点的管理类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener {
    /**
     * 监听
     */
    private AudioFocusListener mAudioFocusListener;

    /**
     * 获取
     */
    private AudioManager audioManager;

    /**
     * 初始化
     *
     * @param context            上下文为了获取AudioManager
     * @param audioFocusListener 回调监听
     */
    public AudioFocusManager(Context context, AudioFocusListener audioFocusListener) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.mAudioFocusListener = audioFocusListener;
    }

    /**
     * 请求语音的焦点,并注册监听
     *
     * @return 是否得到
     */
    public boolean requestAudioFocus() {
        return audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    /**
     * 取消对焦点的监听
     */
    public void abandonAudioFocus() {
        audioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                /*
                 * 重新获得焦点
                 */
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onAudioFocusGain();
                }

                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                /*
                 * 永久丢失焦点,其他播放器播放音乐
                 */
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onAudioFocusLoss();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                /*
                 * 短暂丢失焦点,如来电
                 */
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onAudioFocusLossTransient();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                /*
                 * 瞬间丢失焦点,如通知
                 */
                if (mAudioFocusListener != null) {
                    mAudioFocusListener.onAudioFocusLossTransientCanDuck();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 音乐焦点的回调
     */
    public interface AudioFocusListener {
        /**
         * 获得焦点后的监听
         */
        void onAudioFocusGain();

        /**
         * 当失去焦点后的监听
         */
        void onAudioFocusLoss();

        /**
         * 短暂失去焦点后的监听
         */
        void onAudioFocusLossTransient();

        /**
         * 瞬间失去焦点后的通知
         */
        void onAudioFocusLossTransientCanDuck();
    }
}
