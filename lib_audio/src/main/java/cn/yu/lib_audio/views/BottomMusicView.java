package cn.yu.lib_audio.views;

import android.animation.ObjectAnimator;
import android.content.Context;

import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.events.AudioEvent;
import cn.yu.lib_audio.mediaplayer.control.AudioController;
import cn.yu.lib_base.config.Constants;
import cn.yu.lib_imageloader.ImageLoadManger;


/**
 * Created on 2020-03-06
 * 这是一个自定义的底部的控件
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class BottomMusicView extends ConstraintLayout implements View.OnClickListener {

    private ImageView ivDiyViewBottomMusicViewBanner;
    private TextView tvDiyViewBottomMusicViewTitle;
    private TextView tvDiyViewBottomMusicViewDesc;
    private ImageView ivDiyViewBottomMusicViewIsPlay;
    private ImageView ivDiyViewBottomMusicViewList;
    private AudioBean mAudioBean;

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
         * 初始化点击事件等
         */
        initListener();
        /*
         * 开启圆形banner的旋转动画
         */
        startBannerRotationAnimation();

    }

    /**
     * 设置点击事件
     */
    private void initListener() {
        ivDiyViewBottomMusicViewList.setOnClickListener(this);
        ivDiyViewBottomMusicViewIsPlay.setOnClickListener(this);
        setOnClickListener(this);
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
            case PAUSE:
                showPlayView();
                break;
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
         * banner
         */
        ImageLoadManger.getInstance().loadCircleImage2ImageView(ivDiyViewBottomMusicViewBanner, audioBean.albumPic);
        /*
         * 名字
         */
        tvDiyViewBottomMusicViewTitle.setText(audioBean.authorName);
        /*
         * 专辑名字
         */
        tvDiyViewBottomMusicViewDesc.setText(audioBean.albumName);
        /*
         * 展示播放状态
         */
        showPauseView();
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
     * 播放或者暂停
     */
    private void playOrPause() {
        AudioController.getInstance().playOrPause();
    }

    /**
     * 展示加载中的状态
     */
    public void showLoadingView() {
        /*
         * 设置名字和图片和专辑名字
         */
        if (mAudioBean != null) {
            /*
             * 图片
             */
            ImageLoadManger.getInstance().loadCircleImage2ImageView(ivDiyViewBottomMusicViewBanner, mAudioBean.albumPic);
            /*
             * 名字
             */
            tvDiyViewBottomMusicViewTitle.setText(mAudioBean.musicName);
            /*
             * 专辑名字
             */
            tvDiyViewBottomMusicViewDesc.setText(mAudioBean.authorName);
            /*
             * 设置状态图片
             */
            ivDiyViewBottomMusicViewIsPlay.setImageResource(R.drawable.icon_diy_view_bottom_music_view_pause);

        }
    }

    /**
     * 展示播放的状态
     */
    public void showPlayView() {
        if (ivDiyViewBottomMusicViewIsPlay != null) {
            ivDiyViewBottomMusicViewIsPlay.setImageResource(R.drawable.icon_diy_view_bottom_music_view_pause);
        }
    }

    /**
     * 展示暂停的状态
     */
    public void showPauseView() {
        if (ivDiyViewBottomMusicViewIsPlay != null) {
            ivDiyViewBottomMusicViewIsPlay.setImageResource(R.drawable.icon_diy_view_bottom_music_view_play);
        }
    }

    /**
     * view销毁的时候
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioStatusChangeEvent(AudioEvent event) {
        /*
         * 播放状态
         */
        switch (event.getStatus()) {
            case LOAD:
                mAudioBean = event.getAudioBean();
                showLoadingView();
                break;
            case ERROR:
                break;
            case PAUSE:
                showPauseView();
                break;
            case START:
                showPlayView();
                break;
            case COMPLETE:
                /*
                 * 下一首
                 */
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_diy_view_bottom_music_view_is_play) {
            /*
             * 播放或者暂停
             */
            playOrPause();
        } else if (id == R.id.iv_diy_view_bottom_music_view_list) {
            /*
             * 弹出播放列表
             */

        } else {
            /*
             * 跳转详情界面
             */
            ARouter.getInstance().build(Constants.Router.MusicPlayerActivityPath).navigation();
//            getContext().startActivity(new Intent(getContext(), MusicPlayerActivity.class));
        }
    }
}
