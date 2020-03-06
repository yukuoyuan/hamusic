package cn.yu.lib_audio.mediaplayer.core;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.util.Log;


import org.greenrobot.eventbus.EventBus;

import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.events.AudioEvent;

/**
 * Created on 2020-03-05
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class AudioPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, AudioFocusManager.AudioFocusListener {
    /**
     * 获得播放器
     */
    private CustomMediaPlayer mCustomMediaPlayer;
    /**
     * 标签
     */
    private static final String TAG = "AudioPlayer";
    /**
     * 焦点管理器
     */
    private AudioFocusManager mAudioFocusManager;
    /**
     * 用来保持无线网络的连接,记得手动释放
     */
    private WifiManager.WifiLock mWifiLock;
    /**
     * 是否是外界导致的暂时失去焦点
     */
    private boolean isAudioFocusLossTransient;

    public AudioPlayer() {
        /*
         * 进行一系列的初始化
         */
        init();
    }

    /**
     * 进行一系列的初始化
     */
    private void init() {
        /*
         * 获得一个播放器
         */
        mCustomMediaPlayer = new CustomMediaPlayer();
        /*
         * 设置唤醒模式,设置cpu唤醒
         */
        mCustomMediaPlayer.setWakeMode(AudioHelper.getInstance().getContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mCustomMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        /*
         * 设置监听
         */
        /*
         * 播放完成的监听
         */
        mCustomMediaPlayer.setOnCompletionListener(this);
        /*
         * 准备播放的监听
         */
        mCustomMediaPlayer.setOnPreparedListener(this);
        /*
         * 文件流更新的监听
         */
        mCustomMediaPlayer.setOnBufferingUpdateListener(this);
        /*
         * 播放错误的监听
         */
        mCustomMediaPlayer.setOnErrorListener(this);

        /*
         *设置无线网唤醒
         */
        mWifiLock = ((WifiManager) AudioHelper.getInstance().getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, TAG);
        /*
         * 初始化焦点管理器
         */
        mAudioFocusManager = new AudioFocusManager(AudioHelper.getInstance().getContext(), this);

    }

    /**
     * 设置音量
     *
     * @param leftVolume  左声道音量
     * @param rightVolume 右声道音量
     */
    private void setVolume(float leftVolume, float rightVolume) {
        if (mCustomMediaPlayer != null) {
            mCustomMediaPlayer.setVolume(leftVolume, rightVolume);
        }
    }

    /**
     * 开始播放
     */
    private void start() {
        /*
         * 是否获得焦点,如果没有占用,就获取焦点
         */
        if (!mAudioFocusManager.requestAudioFocus()) {
            Log.e(TAG, "没有得到焦点,请关闭其他应用的播放");
            return;
        }
        /*
         * 开始播放
         */
        mCustomMediaPlayer.start();
        /*
         * 锁定无线网,不被关闭
         */
        mWifiLock.acquire();
        /*
         * 发送事件
         */
        postEvent(AudioEvent.AudioEventStatus.START, null);
    }

    /**
     * 发送各种事件
     *
     * @param status    状态
     * @param audioBean 数据
     */
    private void postEvent(AudioEvent.AudioEventStatus status, AudioBean audioBean) {
        EventBus.getDefault().post(new AudioEvent(status, audioBean));
    }

    /**
     * 加载资源并播放
     */
    public void load(AudioBean audioBean) {
        try {
            if (mCustomMediaPlayer != null) {
                /*
                 * 清空之前的所有数据
                 */
                mCustomMediaPlayer.reset();
                /*
                 * 设置播放源
                 */
                mCustomMediaPlayer.setDataSource(audioBean.pathUrl);
                /*
                 * 异步进行缓冲准备
                 */
                mCustomMediaPlayer.prepareAsync();
                /*
                 * 发送事件
                 */
                postEvent(AudioEvent.AudioEventStatus.LOAD, audioBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            /*
             * 发送事件
             */
            postEvent(AudioEvent.AudioEventStatus.ERROR, null);
        }

    }

    /**
     * 暂停播放
     */
    public void pause() {
        /*
         * 如果是在播放中的话,可以进行暂停,否则不可暂停
         */
        if (mCustomMediaPlayer.getStatus() == CustomMediaPlayer.Status.START) {
            mCustomMediaPlayer.pause();
            /*
             * 释放wifi锁(如果占有的话)
             */
            if (mWifiLock.isHeld()) {
                mWifiLock.release();
            }
            /*
             * 释放掉音频焦点,不妨碍别的app使用
             */
            if (mAudioFocusManager != null) {
                mAudioFocusManager.abandonAudioFocus();
            }
            /*
             * 发送事件
             */
            postEvent(AudioEvent.AudioEventStatus.PAUSE, null);
        }
    }

    /**
     * 恢复播放
     */
    public void resume() {
        /*
         * 暂停的状态下,才可以进行恢复
         */
        if (mCustomMediaPlayer.getStatus() == CustomMediaPlayer.Status.PAUSE) {
            mCustomMediaPlayer.start();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        /*
         * 释放播放器
         */
        if (mCustomMediaPlayer != null) {
            mCustomMediaPlayer.release();
            mCustomMediaPlayer = null;
        }
        /*
         * 释放wifilock
         */
        if (mWifiLock.isHeld()) {
            mWifiLock.release();
            mWifiLock = null;
        }
        /*
         * 释放音频焦点
         */
        if (mAudioFocusManager != null) {
            mAudioFocusManager.abandonAudioFocus();
            mAudioFocusManager = null;
        }
        /*
         * 发送事件
         */
        postEvent(AudioEvent.AudioEventStatus.RELEASE, null);
    }

    /**
     * 获取播放器的 状态
     *
     * @return 状态
     */
    public CustomMediaPlayer.Status getStatus() {
        if (mCustomMediaPlayer != null) {
            return mCustomMediaPlayer.getStatus();
        }
        /*
         * 否则返回空状态
         */
        return CustomMediaPlayer.Status.STOP;
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        /*
         * 发送事件
         */
        postEvent(AudioEvent.AudioEventStatus.COMPLETE, null);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        /*
         * 准备好了,那么就开始播放了
         */
        start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /*
         * 用来处理缓存进度
         */
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        /*
         * 发送事件
         */
        postEvent(AudioEvent.AudioEventStatus.ERROR, null);
        /*
         * return true 是自行处理,不需要播放器处理,并且不会回调comple回调
         */
        return true;
    }


    @Override
    public void onAudioFocusGain() {
        /*
         * 再次获得焦点,因为在瞬间失去焦点的时候会把音量减小
         */
        setVolume(1.0f, 1.0f);

        /*
         * 如果是因为外部因素导致失去的焦点的话,那么就进行继续播放,如果不是,那么就什么都不做
         */
        if (isAudioFocusLossTransient) {
            /*
             * 继续播放
             */
            resume();
        }
        isAudioFocusLossTransient = false;
    }

    @Override
    public void onAudioFocusLoss() {
        /*
         * 如果失去了焦点,那么就只有暂停播放了
         */
        pause();
    }

    @Override
    public void onAudioFocusLossTransient() {
        /*
         * 如果是暂时失去焦点,那么就暂停播放
         */
        pause();
        /*
         * 是否是外界导致的暂时失去焦点
         */
        isAudioFocusLossTransient = true;
    }

    @Override
    public void onAudioFocusLossTransientCanDuck() {
        /*
         * 瞬间失去焦点的话,那么就减小音量,不必要暂停
         */
        setVolume(0.5f, 0.5f);
    }
}
