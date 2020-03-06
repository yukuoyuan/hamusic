package cn.yu.lib_audio.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.events.AudioEvent;


/**
 * Created on 2020-03-06
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class BottomMusicView extends ConstraintLayout {

    private ImageView ivDiyViewBottomMusicViewBanner;
    private TextView tvDiyViewBottomMusicViewTitle;
    private TextView tvDiyViewBottomMusicViewDesc;
    private ImageView ivDiyViewBottomMusicViewIsPlay;
    private ImageView ivDiyViewBottomMusicViewList;

    public BottomMusicView(Context context) {
        this(context, null);
    }

    public BottomMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        EventBus.getDefault().register(this);
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        View.inflate(getContext(), R.layout.diy_view_bottom_music_videw, this);
        /*
         * 初始化控件
         */
        initView();
        /*
         * 开启圆形banner的旋转动画
         */
        startBannerRotationAnimation();

    }

    /**
     * 开启圆形banner的旋转动画
     */
    private void startBannerRotationAnimation() {
        /*
         * 旋转一圈
         */
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivDiyViewBottomMusicViewBanner, View.ROTATION.getName(), 0, 360);
        /*
         * 旋转动画匀速
         */
        objectAnimator.setInterpolator(new LinearInterpolator());
        /*
         * 设置时长为10s转一圈
         */
        objectAnimator.setDuration(10000);
        /*
         * 永不停止
         */
        objectAnimator.setRepeatCount(-1);
        /*
         * 开始动画
         */
        objectAnimator.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioEvent(AudioEvent audioEvent) {
        switch (audioEvent.getStatus()) {
            case LOAD:
                /*
                 * 开始加载的时候
                 */
                initData2View(audioEvent.getAudioBean());
                break;
            default:
                break;
        }
    }

    /**
     * 初始化播放数据到界面
     *
     * @param audioBean 数据
     */
    private void initData2View(AudioBean audioBean) {

        /*
         * 展示播放状态
         */
        showPlayView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ivDiyViewBottomMusicViewBanner = findViewById(R.id.iv_diy_view_bottom_music_view_banner);
        tvDiyViewBottomMusicViewTitle = findViewById(R.id.tv_diy_view_bottom_music_view_title);
        tvDiyViewBottomMusicViewDesc = findViewById(R.id.tv_diy_view_bottom_music_view_desc);
        ivDiyViewBottomMusicViewIsPlay = findViewById(R.id.iv_diy_view_bottom_music_view_is_play);
        ivDiyViewBottomMusicViewList = findViewById(R.id.iv_diy_view_bottom_music_view_list);
    }

    /**
     * 展示播放的状态
     */
    public void showPlayView() {
        if (ivDiyViewBottomMusicViewIsPlay != null) {
            ivDiyViewBottomMusicViewIsPlay.setImageResource(R.drawable.icon_diy_view_bottom_music_view_play);
        }
    }

    /**
     * 展示暂停的状态
     */
    public void showPauseView() {
        if (ivDiyViewBottomMusicViewIsPlay != null) {
            ivDiyViewBottomMusicViewIsPlay.setImageResource(R.drawable.icon_diy_view_bottom_music_view_pause);
        }
    }

}
