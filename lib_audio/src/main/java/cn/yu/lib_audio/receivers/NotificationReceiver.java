package cn.yu.lib_audio.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.mediaplayer.control.AudioController;

/**
 * Created on 2020-03-11
 * 通知广播,用来处理通知里的点击事件
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class NotificationReceiver extends BroadcastReceiver {
    /**
     * action
     */
    public static final String ACTION_STATUS_BAR = AudioHelper.getInstance().getContext().getPackageName() + ".NOTIFICATION_ACTIONS";
    /**
     * 传递的数据key
     */
    public static final String EXTRA = "extra";
    /**
     * 点击播放按钮
     */
    public static final String EXTRA_PLAY = "play_pause";
    /**
     * 下一首的按钮
     */
    public static final String EXTRA_NEXT = "play_next";
    /**
     * 上一首的按钮
     */
    public static final String EXTRA_PRE = "play_previous";
    /**
     * 收藏的按钮
     */
    public static final String EXTRA_FAV = "play_favourite";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        String extra = intent.getStringExtra(EXTRA);
        switch (extra) {
            case EXTRA_PLAY:
                /*
                 * 播放或者暂停
                 */
                AudioController.getInstance().playOrPause();
                break;
            case EXTRA_NEXT:
                /*
                 * 下一首
                 */
                AudioController.getInstance().nextMusic();
                break;
            case EXTRA_PRE:
                /*
                 * 上一曲
                 */
                AudioController.getInstance().previousMusic();
                break;
            default:
                break;
        }

    }
}
