package cn.yu.lib_video.videoplayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.yu.lib_video.R;

/**
 * Created on 2020-03-17
 * 这是一个自定义的视频控件
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class CustomVideoView extends RelativeLayout implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    /**
     * 显示视频的控件
     */
    private TextureView ttvDiyViewCustomVideoView;
    /**
     * 是否全屏的控件
     */
    private ImageView ivDiyViewCustomVideoViewFullScreen;
    /**
     * 创建surface
     */
    private Surface videoSurface;
    /**
     * 播放器
     */
    private MediaPlayer mMediaPlayer;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.diy_view_video_view, this);
        /*
         * 初始化控件
         */
        initView();
        ttvDiyViewCustomVideoView.setSurfaceTextureListener(this);
    }

    private void initView() {
        ttvDiyViewCustomVideoView = findViewById(R.id.ttv_diy_view_custom_video_view);
        ivDiyViewCustomVideoViewFullScreen = findViewById(R.id.iv_diy_view_custom_video_view_full_screen);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        videoSurface = new Surface(surface);

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 校验MediaPlayer的正确性
     */
    private synchronized void checkMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            /*
             * 注册监听
             */
            /*
             * 准备好的监听
             */
            mMediaPlayer.setOnPreparedListener(this);
            /*
             * 播放完毕的监听
             */
            mMediaPlayer.setOnCompletionListener(this);
            /*
             * 播放失败的监听
             */
            mMediaPlayer.setOnErrorListener(this);
            /*
             * 设置播放类型是流类型
             */
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        /*
         * 当播放器准备好的回调方法
         */
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        /*
         * 当视频播放完毕会回调此方法
         */

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        /*
         * 播放失败会回调此方法
         */
        return false;
    }
}
