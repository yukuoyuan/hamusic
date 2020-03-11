package cn.yu.lib_audio.views;

import android.app.Notification;
import android.widget.RemoteViews;

import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;

/**
 * Created on 2020-03-11
 * 这是一个自定义的音乐广播
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class NotificationHelper {

    private static NotificationHelper mNotificationHelper = null;
    /**
     * 通知
     */
    private Notification mNotification;

    /**
     * 当前播放歌曲的实体类
     */
    private AudioBean mAudioBean;

    private NotificationHelper() {
    }

    public static NotificationHelper getInstance() {
        synchronized (NotificationHelper.class) {
            if (mNotificationHelper == null) {
                mNotificationHelper = new NotificationHelper();
            }
        }
        return mNotificationHelper;
    }

    private void initNotification() {


        if (mNotification == null) {
            /*
             * 创建自定义布局,需要用到RemoteView
             */
            initRemoteView();
        }
    }

    /**
     * 初始化remoteView
     */
    private void initRemoteView() {
        int layoutId = R.layout.notification_diy_music;
        RemoteViews remoteViews = new RemoteViews(AudioHelper.getInstance().getContext().getPackageName(), layoutId);


    }
}
