package cn.yu.lib_audio.activitys;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.bean.FavoriteBean;
import cn.yu.lib_audio.dbs.MusicDapHelper;
import cn.yu.lib_audio.events.AudioEvent;
import cn.yu.lib_audio.mediaplayer.control.AudioController;
import cn.yu.lib_audio.mediaplayer.core.CustomMediaPlayer;
import cn.yu.lib_audio.utils.TimeUtils;
import cn.yu.lib_base.activitys.BaseActivity;

/**
 * Created on 2020-03-11
 * 这是一个播放详情界面
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicPlayerActivity extends BaseActivity implements View.OnClickListener {


    private ImageView ivMusicPlayerActivityTitleLeftBack;
    private ImageView ivMusicPlayerActivityTitleRightShare;
    private TextView tvMusicPlayerActivityTitleDescription;
    private TextView tvMusicPlayerActivityTitleAuthorName;
    private ImageView ivMusicPlayerActivityBottomTopFavourite;
    private TextView tvMusicPlayerActivityBottomCenterStartTime;
    private TextView tvMusicPlayerActivityBottomCenterEndTime;
    private SeekBar sbMusicPlayerActivityBottomCenterProgress;
    private ImageView ivMusicPlayerActivityBottomBottomPlayMode;
    private ImageView ivMusicPlayerActivityBottomBottomPrevious;
    private ImageView ivMusicPlayerActivityBottomBottomPlayPause;
    private ImageView ivMusicPlayerActivityBottomBottomNext;
    private ImageView ivMusicPlayerActivityBottomBottomList;

    @Override
    protected void initData(Bundle bundle) {
        /*
         * 初始化控件
         */
        initView();
        /*
         * 初始化监听
         */
        initListener();
        /*
         * 初始化数据到界面
         */
        initData2View();
    }

    private void initData2View() {
        AudioBean audioBean = AudioController.getInstance().getNowPlaying();
        /*
         * 专辑描述信息
         */
        tvMusicPlayerActivityTitleDescription.setText(audioBean.albumInfo);
        /*
         * 作者名字
         */
        tvMusicPlayerActivityTitleAuthorName.setText(audioBean.authorName);
        /*
         * 是否收藏
         */
        FavoriteBean favoriteBean = MusicDapHelper.getInstance().searchFavoriteBean(audioBean);
        showIsFavouriteView(favoriteBean != null);
        /*
         * 展示 播放方式
         */
        showPlayModeView();
    }

    /**
     * 设置播放模式的标志
     */
    private void showPlayModeView() {
        AudioController.PlayMode playMode = AudioController.getInstance().getPlayMode();
        switch (playMode) {
            case LOOP:
                /*
                 * 循环列表
                 */
                ivMusicPlayerActivityBottomBottomPlayMode.setImageResource(R.drawable.icon_music_player_play_mode_cycle);
                break;
            case RANDOM:
                /*
                 * 随机
                 */
                ivMusicPlayerActivityBottomBottomPlayMode.setImageResource(R.drawable.icon_music_player_play_mode_random);
                break;
            case REPEAT:
                /*
                 * 单曲循环
                 */
                ivMusicPlayerActivityBottomBottomPlayMode.setImageResource(R.drawable.icon_music_player_play_mode_once);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isUsedEventBus() {
        return true;
    }

    private void initListener() {
        ivMusicPlayerActivityTitleLeftBack.setOnClickListener(this);
        ivMusicPlayerActivityTitleRightShare.setOnClickListener(this);
        ivMusicPlayerActivityBottomTopFavourite.setOnClickListener(this);
        ivMusicPlayerActivityBottomBottomPlayMode.setOnClickListener(this);
        ivMusicPlayerActivityBottomBottomPrevious.setOnClickListener(this);
        ivMusicPlayerActivityBottomBottomPlayPause.setOnClickListener(this);
        ivMusicPlayerActivityBottomBottomNext.setOnClickListener(this);
        ivMusicPlayerActivityBottomBottomList.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_music_player;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioEvent(AudioEvent audioEvent) {
        AudioEvent.AudioEventStatus audioEventStatus = audioEvent.getStatus();
        switch (audioEventStatus) {
            case LOAD:
                initData2View();
                break;
            case PAUSE:
                /*
                 * 展示暂停的状态
                 */
                showPlayOrPauseView(false);
                break;
            case START:
                /*
                 * 展示播放的状态
                 */
                showPlayOrPauseView(true);
                break;
            case FAVORITE:
                /*
                 * 收藏
                 */
                showIsFavouriteView(true);
                break;
            case PROGRESS:
                /*
                 * 改变进度
                 */
                setTimeData2View(audioEvent.getCurrentPosition(), audioEvent.getDuration());
                break;
            case CANCEL_FAVORITE:
                /*
                 * 取消收藏
                 */
                showIsFavouriteView(false);
                break;
            case PLAY_MODE:
                /*
                 * 播放模式
                 */
                showPlayModeView();
                break;
            default:
                break;
        }
    }

    /**
     * 展示开始或者暂停的按钮
     *
     * @param isPlay 是否播放
     */
    private void showPlayOrPauseView(boolean isPlay) {
        if (isPlay) {
            ivMusicPlayerActivityBottomBottomPlayPause.setImageResource(R.drawable.icon_music_player_play);
        } else {
            ivMusicPlayerActivityBottomBottomPlayPause.setImageResource(R.drawable.icon_music_player_pause);
        }
    }

    /**
     * 设置时间数据到界面
     *
     * @param currentPosition 选择的时长
     * @param duration        总的时长
     */
    private void setTimeData2View(int currentPosition, int duration) {
        /*
         * 设置时间显示
         */
        tvMusicPlayerActivityBottomCenterStartTime.setText(TimeUtils.getInstance().msConvertMinutesAndSeconds(currentPosition));
        tvMusicPlayerActivityBottomCenterEndTime.setText(TimeUtils.getInstance().msConvertMinutesAndSeconds(duration));
        /*
         * 设置进度条
         */
        sbMusicPlayerActivityBottomCenterProgress.setProgress(currentPosition);
        sbMusicPlayerActivityBottomCenterProgress.setMax(duration);
        /*
         * 设置播放状态
         */
        CustomMediaPlayer.Status status = AudioController.getInstance().getStatus();
        if (status == CustomMediaPlayer.Status.PAUSE) {
            showPlayOrPauseView(false);
        } else {
            showPlayOrPauseView(true);
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ivMusicPlayerActivityTitleLeftBack = findViewById(R.id.iv_music_player_activity_title_left_back);
        ivMusicPlayerActivityTitleRightShare = findViewById(R.id.iv_music_player_activity_title_right_share);
        tvMusicPlayerActivityTitleDescription = findViewById(R.id.tv_music_player_activity_title_description);
        tvMusicPlayerActivityTitleAuthorName = findViewById(R.id.tv_music_player_activity_title_author_name);
        ivMusicPlayerActivityBottomTopFavourite = findViewById(R.id.iv_music_player_activity_bottom_top_favourite);
        tvMusicPlayerActivityBottomCenterStartTime = findViewById(R.id.tv_music_player_activity_bottom_center_start_time);
        tvMusicPlayerActivityBottomCenterEndTime = findViewById(R.id.tv_music_player_activity_bottom_center_end_time);
        sbMusicPlayerActivityBottomCenterProgress = findViewById(R.id.sb_music_player_activity_bottom_center_progress);
        ivMusicPlayerActivityBottomBottomPlayMode = findViewById(R.id.iv_music_player_activity_bottom_bottom_play_mode);
        ivMusicPlayerActivityBottomBottomPrevious = findViewById(R.id.iv_music_player_activity_bottom_bottom_previous);
        ivMusicPlayerActivityBottomBottomPlayPause = findViewById(R.id.iv_music_player_activity_bottom_bottom_play_pause);
        ivMusicPlayerActivityBottomBottomNext = findViewById(R.id.iv_music_player_activity_bottom_bottom_next);
        ivMusicPlayerActivityBottomBottomList = findViewById(R.id.iv_music_player_activity_bottom_bottom_list);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_music_player_activity_title_left_back) {
            /*
             * 返回
             */
            onBackPressed();
        } else if (id == R.id.iv_music_player_activity_title_right_share) {
            /*
             * 分享弹窗
             */
        } else if (id == R.id.iv_music_player_activity_bottom_top_favourite) {
            /*
             * 是否收藏音乐
             */
            AudioController.getInstance().isFavoriteMusic();
            /*
             * 展示动画
             */
            showFavoriteImgAnimation();
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_play_mode) {
            /*
             * 改变播放模式
             */
            changePlayMode();
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_previous) {
            /*
             * 上一曲
             */
            AudioController.getInstance().previousMusic();

        } else if (id == R.id.iv_music_player_activity_bottom_bottom_play_pause) {
            /*
             *开始或者暂停
             */
            AudioController.getInstance().playOrPause();
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_next) {
            /*
             *下一曲
             */
            AudioController.getInstance().nextMusic();
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_list) {
            /*
             * 音乐列表弹窗
             */
        }
    }

    private void showFavoriteImgAnimation() {
        /*
         * 组合动画
         */
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(ivMusicPlayerActivityBottomTopFavourite, View.SCALE_X.getName(), 1.0f, 1.2f, 1.0f);
        /*
         * 匀速的
         */
        objectAnimatorX.setInterpolator(new LinearInterpolator());
        /*
         * 动画时长
         */
        objectAnimatorX.setDuration(300);

        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(ivMusicPlayerActivityBottomTopFavourite, View.SCALE_Y.getName(), 1.0f, 1.2f, 1.0f);
        /*
         * 匀速的
         */
        objectAnimatorY.setInterpolator(new LinearInterpolator());
        /*
         * 动画时长
         */
        objectAnimatorY.setDuration(300);
        /*
         * 同时的
         */
        animatorSet.play(objectAnimatorX).with(objectAnimatorY);
        /*
         * 开始动画
         */
        animatorSet.start();
    }

    /**
     * 改变播放模式
     */
    private void changePlayMode() {
        AudioController.PlayMode playMode = AudioController.getInstance().getPlayMode();
        //切换播放模式
        switch (playMode) {
            case LOOP:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.RANDOM);
                break;
            case RANDOM:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.REPEAT);
                break;
            case REPEAT:
                AudioController.getInstance().setPlayMode(AudioController.PlayMode.LOOP);
                break;
            default:
                break;
        }
    }

    /**
     * 是否收藏
     *
     * @param isFavourite 是否收藏
     */
    private void showIsFavouriteView(boolean isFavourite) {
        if (isFavourite) {
            ivMusicPlayerActivityBottomTopFavourite.setImageResource(R.drawable.icon_music_player_favourite);
        } else {
            ivMusicPlayerActivityBottomTopFavourite.setImageResource(R.drawable.icon_music_player_unfavourite);
        }
    }
}
