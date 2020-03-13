package cn.yu.lib_audio.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.adapters.MusicIndicatorViewPagerAdapter;
import cn.yu.lib_audio.mediaplayer.control.AudioController;

/**
 * Created on 2020-03-13
 * 这是一个播放详情的唱针的控件
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicIndicatorView extends ConstraintLayout implements ViewPager.OnPageChangeListener {


    private ImageView ivDiyViewMusicIndicatorViewIndicator;
    private ViewPager vpDiyViewMusicIndicatorView;

    public MusicIndicatorView(Context context) {
        this(context, null);
    }

    public MusicIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
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
        MusicIndicatorViewPagerAdapter musicIndicatorViewPagerAdapter = new MusicIndicatorViewPagerAdapter(AudioController.getInstance().getAudioBeanArrayList(), getContext());
        vpDiyViewMusicIndicatorView.setAdapter(musicIndicatorViewPagerAdapter);
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
        ivDiyViewMusicIndicatorViewIndicator = findViewById(R.id.iv_diy_view_music_indicator_view_indicator);
        vpDiyViewMusicIndicatorView = findViewById(R.id.vp_diy_view_music_indicator_view);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        /*
         *
         */
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
