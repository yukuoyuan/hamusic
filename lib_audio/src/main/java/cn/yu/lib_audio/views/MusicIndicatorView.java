package cn.yu.lib_audio.views;

import android.animation.Animator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.adapters.MusicIndicatorViewPagerAdapter;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.events.AudioEvent;
import cn.yu.lib_audio.mediaplayer.control.AudioController;

/**
 * Created on 2020-03-13
 * 这是一个播放详情的唱针的控件
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicIndicatorView extends ConstraintLayout implements ViewPager.OnPageChangeListener {

    /**
     * viewpager
     */
    private ViewPager vpDiyViewMusicIndicatorView;
    /**
     * 适配器
     */
    private MusicIndicatorViewPagerAdapter mMusicIndicatorViewPagerAdapter;
    /**
     * 当前播放的
     */
    private AudioBean mAudioBean;

    public MusicIndicatorView(Context context) {
        this(context, null);
    }

    public MusicIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioEvent(AudioEvent audioEvent) {
        AudioEvent.AudioEventStatus audioEventStatus = audioEvent.getStatus();
        switch (audioEventStatus) {
            case START:
                showPlayStatus();
                break;
            case PAUSE:
                showPauseStatus();
                break;
            case LOAD:
                /*
                 * 翻页,不带动画效果的
                 */
                mAudioBean = audioEvent.getAudioBean();
                showLoadStatus();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化
     */
    private void init() {
        View.inflate(getContext(), R.layout.diy_view_music_indicator_view, this);
        /*
         * 初始化控件
         */
        initView();
        /*
         * 初始化监听
         */
        initListener();
        /*
         * 初始化数据
         */
        initData();
    }

    /**
     * 初始哈数据
     */
    private void initData() {
        mMusicIndicatorViewPagerAdapter = new MusicIndicatorViewPagerAdapter(AudioController.getInstance().getAudioBeanArrayList(), getContext());
        vpDiyViewMusicIndicatorView.setAdapter(mMusicIndicatorViewPagerAdapter);
        /*
         * 当前播放的
         */
        mAudioBean = AudioController.getInstance().getNowPlaying();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        vpDiyViewMusicIndicatorView.addOnPageChangeListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        vpDiyViewMusicIndicatorView = findViewById(R.id.vp_diy_view_music_indicator_view);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        /*
         * 指定播放某一首歌曲
         */
        AudioController.getInstance().setQueueIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        /*
         * 逻辑有问题,会导致没有翻页的情况下不播放动画了
         */
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
                showPlayStatus();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                showPauseStatus();
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
            default:
                break;
        }
    }

    /**
     * 选中某一页
     */
    private void showLoadStatus() {
        vpDiyViewMusicIndicatorView.setCurrentItem(AudioController.getInstance().getAudioBeanArrayList().indexOf(mAudioBean));
    }

    /**
     * 展示暂停状态
     */
    private void showPauseStatus() {
        if (mMusicIndicatorViewPagerAdapter != null) {
            Animator objectAnimator = mMusicIndicatorViewPagerAdapter.getAnimator(vpDiyViewMusicIndicatorView.getCurrentItem());
            objectAnimator.pause();
        }
    }

    /**
     * 展示播放的状态
     */
    private void showPlayStatus() {
        if (mMusicIndicatorViewPagerAdapter != null) {
            Animator objectAnimator = mMusicIndicatorViewPagerAdapter.getAnimator(vpDiyViewMusicIndicatorView.getCurrentItem());
            if (objectAnimator.isPaused()) {
                objectAnimator.resume();
            } else {
                objectAnimator.start();
            }
        }
    }
}
