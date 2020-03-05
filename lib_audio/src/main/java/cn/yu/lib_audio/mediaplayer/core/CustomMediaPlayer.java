package cn.yu.lib_audio.mediaplayer.core;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created on 2020-03-05
 * 这是一个自定义的mediaPlayer
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class CustomMediaPlayer extends MediaPlayer implements MediaPlayer.OnCompletionListener {

    public enum Status {
        /**
         * 各种播放的状态
         * 无状态,初始化状态,开始状态,暂停状态,停止状态,播放结束状态
         */
        IDLE,
        INITIALIZED,
        START,
        PAUSE,
        STOP,
        COMPLETE,
    }

    /**
     * 播放状态,默认无状态
     */
    private Status mStatus = Status.IDLE;

    /**
     * 播放完成的监听
     */
    private OnCompletionListener onCompletionListener;

    public CustomMediaPlayer() {
        /*
         * 初始化为无状态
         */
        mStatus = Status.IDLE;
        /*
         * 设置播放结束的回调,不要调用自己的,要调用父类的
         */
        super.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /*
         * 设置为播放完成的状态
         */
        mStatus = Status.COMPLETE;
        /*
         * 设置回调监听
         */
        if (onCompletionListener != null) {
            onCompletionListener.onCompletion(mp);
        }
    }

    @Override
    public void reset() {
        super.reset();
        mStatus = Status.IDLE;
    }

    @Override
    public void setDataSource(String path) throws IOException, IllegalArgumentException, IllegalStateException, SecurityException {
        super.setDataSource(path);
        /*
         * 设置为初始化状态
         */
        mStatus = Status.INITIALIZED;
    }

    @Override
    public void start() throws IllegalStateException {
        super.start();
        /*
         * 设置为开始状态
         */
        mStatus = Status.START;

    }

    @Override
    public void pause() throws IllegalStateException {
        super.pause();
        /*
         * 设置为暂停状态
         */
        mStatus = Status.PAUSE;

    }

    @Override
    public void stop() throws IllegalStateException {
        super.stop();
        /*
         * 设置为停止状态
         */
        mStatus = Status.STOP;
    }

    /**
     * 获取当前的状态
     *
     * @return 状态
     */
    public Status getStatus() {
        return mStatus;
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }
}
