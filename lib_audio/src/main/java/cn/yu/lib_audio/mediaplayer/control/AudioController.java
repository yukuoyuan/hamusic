package cn.yu.lib_audio.mediaplayer.control;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.dbs.MusicDapHelper;
import cn.yu.lib_audio.events.AudioEvent;
import cn.yu.lib_audio.mediaplayer.core.AudioPlayer;
import cn.yu.lib_audio.mediaplayer.core.CustomMediaPlayer;
import cn.yu.lib_base.utils.ListUtils;

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
        /*
         * 注册事件
         */
        EventBus.getDefault().register(this);
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

    /**
     * 获取现在正在播放的数据bean
     *
     * @return 数据bean
     */
    public AudioBean getNowPlaying() {
        return getNowPlaying(mQueueIndex);
    }

    /**
     * 获取现在正在播放的数据bean
     *
     * @param queueIndex 索引
     * @return 数据bean
     */
    private AudioBean getNowPlaying(int queueIndex) {
        /*
         * 保证数据
         */
        if (!ListUtils.getInstance().isEmpty(mAudioBeanArrayList) && ListUtils.getInstance().indexIsEffective(queueIndex, mAudioBeanArrayList)) {
            return mAudioBeanArrayList.get(queueIndex);
        } else {
            Log.e("音乐控制器", "实在是无法得到");
            return null;
        }
    }

    /**
     * 获取下一首的数据bean
     *
     * @return 数据bean
     */
    private AudioBean getNextPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + 1) % mAudioBeanArrayList.size();
                break;
            case RANDOM:
                mQueueIndex = new Random().nextInt(mAudioBeanArrayList.size()) % mAudioBeanArrayList.size();
                break;
            default:
                break;
        }
        return getNowPlaying(mQueueIndex);
    }

    /**
     * 获取上一首的数据bean
     *
     * @return 数据bean
     */
    private AudioBean getPreviousPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex - 1 + mAudioBeanArrayList.size()) % mAudioBeanArrayList.size();
                break;
            case RANDOM:
                mQueueIndex = new Random().nextInt(mAudioBeanArrayList.size()) % mAudioBeanArrayList.size();
                break;
            default:
                break;
        }
        return getNowPlaying(mQueueIndex);
    }

    /**
     * 指定播放某一首歌曲
     *
     * @param mQueueIndex 索引
     */
    public void setQueueIndex(int mQueueIndex) {
        if (ListUtils.getInstance().isEmpty(mAudioBeanArrayList)) {
            return;
        }
        this.mQueueIndex = mQueueIndex;
        start();
    }

    /**
     * 获取当前播放的索引
     *
     * @return 索引
     */
    public int getQueueIndex() {
        return mQueueIndex;
    }

    /**
     * 点击进行是否收藏操作
     */
    public void isFavoriteMusic() {
        if (null != MusicDapHelper.getInstance().searchFavoriteBean(getNowPlaying())) {
            /*
             * 已经收藏的话移除收藏
             */
            MusicDapHelper.getInstance().removeFavoriteBean(getNowPlaying());
            EventBus.getDefault().post(new AudioEvent(AudioEvent.AudioEventStatus.CANCEL_FAVORITE, new AudioBean()));
        } else {
            /*
             * 没有收藏的话进行收藏
             */
            MusicDapHelper.getInstance().addFavoriteMusic(getNowPlaying());
            EventBus.getDefault().post(new AudioEvent(AudioEvent.AudioEventStatus.FAVORITE, new AudioBean()));
        }

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
        EventBus.getDefault().unregister(this);
    }

    /**
     * 下一首歌曲
     */
    public void nextMusic() {
        AudioBean audioBean = getNextPlaying();
        mAudioPlayer.load(audioBean);
    }

    /**
     * 上一首歌曲
     */
    public void previousMusic() {
        AudioBean audioBean = getPreviousPlaying();
        mAudioPlayer.load(audioBean);
    }

    /**
     * 播放或者暂停
     */
    public void playOrPause() {
        if (isStartStatus()) {
            pause();
        } else if (isPauseStatus()) {
            resume();
        }
    }

    /**
     * 添加audiobean到第一个位置
     *
     * @param audioBean 数据
     */
    public void addAudioBean2ArrayList(AudioBean audioBean) {
        addAudioBean2ArrayList(audioBean);
    }

    /**
     * 添加audiobean到某一个位置
     *
     * @param index     索引
     * @param audioBean 数据
     */
    public void addAudioBean2ArrayList(int index, AudioBean audioBean) {
        if (ListUtils.getInstance().isEmpty(mAudioBeanArrayList)) {
            Log.e("AudioController", "队列是空的");
            return;
        }
        mAudioBeanArrayList.add(index, audioBean);
    }

    /**
     * 获取播放列表
     *
     * @return 播放列表
     */
    public ArrayList<AudioBean> getAudioBeanArrayList() {
        return mAudioBeanArrayList == null ? new ArrayList<AudioBean>() : mAudioBeanArrayList;
    }

    /**
     * 添加列表到队列里
     *
     * @param audioBeanArrayList 列表
     */
    public void addAudioBeanArrayList(ArrayList<AudioBean> audioBeanArrayList) {
        addAudioBeanArrayList(0, audioBeanArrayList);
    }

    /**
     * 添加列表到队列里
     *
     * @param index              添加到哪一个位置
     * @param audioBeanArrayList 新的列表
     */
    public void addAudioBeanArrayList(int index, ArrayList<AudioBean> audioBeanArrayList) {
        if (mAudioBeanArrayList != null) {
            mAudioBeanArrayList.addAll(index, audioBeanArrayList);
        }
    }

    /**
     * 设置队列
     *
     * @param audioBeanArrayList 列表
     */
    public void setAudioBeanArrayList(ArrayList<AudioBean> audioBeanArrayList) {
        mAudioBeanArrayList = audioBeanArrayList;
    }

    /**
     * 是否是开始状态
     *
     * @return 是否是
     */
    public boolean isStartStatus() {
        return CustomMediaPlayer.Status.START == getStatus();
    }

    /**
     * 是否是暂停状态
     *
     * @return 是否是
     */
    public boolean isPauseStatus() {
        return CustomMediaPlayer.Status.PAUSE == getStatus();
    }

    /**
     * 获取播放的状态
     *
     * @return 状态
     */
    public CustomMediaPlayer.Status getStatus() {
        return mAudioPlayer.getStatus();
    }

    /**
     * 获得当前的播放模式
     *
     * @return 播放模式
     */
    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    /**
     * 设置播放模式
     *
     * @param playMode 播放模式
     */
    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
        EventBus.getDefault().post(new AudioEvent(AudioEvent.AudioEventStatus.PLAY_MODE, playMode));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioStatusChangeEvent(AudioEvent event) {
        /*
         * 播放完毕了
         */
        switch (event.getStatus()) {
            case COMPLETE:
                /*
                 * 下一首
                 */
                nextMusic();
                break;
            default:
                break;
        }

    }
}
