package cn.yu.lib_audio.activitys;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.yu.lib_audio.R;
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
    private LinearLayout llMusicPlayerActivityBottomTop;
    private ImageView ivMusicPlayerActivityBottomTopFavourite;
    private ConstraintLayout clMusicPlayerActivityBottomCenter;
    private TextView tvMusicPlayerActivityBottomCenterStartTime;
    private TextView tvMusicPlayerActivityBottomCenterEndTime;
    private SeekBar sbMusicPlayerActivityBottomCenterProgress;
    private ConstraintLayout clMusicPlayerActivityBottomBottom;
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
    }

    private void initListener() {
        ivMusicPlayerActivityTitleLeftBack.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_music_player;
    }


    private void initView() {
        ivMusicPlayerActivityTitleLeftBack = findViewById(R.id.iv_music_player_activity_title_left_back);
        ivMusicPlayerActivityTitleRightShare = findViewById(R.id.iv_music_player_activity_title_right_share);
        tvMusicPlayerActivityTitleDescription = findViewById(R.id.tv_music_player_activity_title_description);
        tvMusicPlayerActivityTitleAuthorName = findViewById(R.id.tv_music_player_activity_title_author_name);
        llMusicPlayerActivityBottomTop = findViewById(R.id.ll_music_player_activity_bottom_top);
        ivMusicPlayerActivityBottomTopFavourite = findViewById(R.id.iv_music_player_activity_bottom_top_favourite);
        clMusicPlayerActivityBottomCenter = findViewById(R.id.cl_music_player_activity_bottom_center);
        tvMusicPlayerActivityBottomCenterStartTime = findViewById(R.id.tv_music_player_activity_bottom_center_start_time);
        tvMusicPlayerActivityBottomCenterEndTime = findViewById(R.id.tv_music_player_activity_bottom_center_end_time);
        sbMusicPlayerActivityBottomCenterProgress = findViewById(R.id.sb_music_player_activity_bottom_center_progress);
        clMusicPlayerActivityBottomBottom = findViewById(R.id.cl_music_player_activity_bottom_bottom);
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
        } else if (id == R.id.iv_music_player_activity_bottom_top_favourite) {
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_play_mode) {
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_previous) {
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_play_pause) {
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_next) {
        } else if (id == R.id.iv_music_player_activity_bottom_bottom_list) {
        }
    }
}
