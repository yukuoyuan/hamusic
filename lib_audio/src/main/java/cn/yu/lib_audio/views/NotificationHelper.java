package cn.yu.lib_audio.views;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;

import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.R;
import cn.yu.lib_audio.activitys.MusicPlayerActivity;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.mediaplayer.control.AudioController;
import cn.yu.lib_audio.receivers.NotificationReceiver;

/**
 * Created on 2020-03-11
 * 这是一个自定义的音乐广播
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class NotificationHelper {
    /**
     * 通知渠道id
     */
    private static final String CHANNEL_ID = "channel_id_audio";
    /**
     * 通知渠道名字
     */
    private static final String CHANNEL_NAME = "channel_id_name";
    /**
     * 当前对象
     */
    private static NotificationHelper mNotificationHelper = null;
    /**
     * 点击下一曲的请求码
     */
    private static final int nextPendingIntentRequestCode = 1;
    /**
     * 点击上一曲的请求码
     */
    private static final int previousPendingIntentRequestCode = 2;
    /**
     * 点击播放暂停的请求码
     */
    private static final int playOrPausePendingIntentRequestCode = 3;
    /**
     * 通知
     */
    private Notification mNotification;

    /**
     * 当前播放歌曲的实体类
     */
    private AudioBean mAudioBean;
    /**
     * 通知自定义Ui
     */
    private RemoteViews mRemoteViews;
    /**
     * 通知管理器
     */
    private NotificationManager mNotificationManager;


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

    public void init() {
        /*
         * 获得通知管理类
         */
        mNotificationManager = (NotificationManager) AudioHelper.getInstance().getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        /*
         *获取当前播放的数据
         */
        mAudioBean = AudioController.getInstance().getNowPlaying();
        /*
         * 初始化通知
         */
        initNotification();
    }

    /**
     * 初始化通知
     */
    private void initNotification() {

        if (mNotification == null) {
            /*
             * 创建自定义布局,需要用到RemoteView
             */
            initRemoteView();
            /*
             * 构建跳转的意图
             */
            Intent intent = new Intent(AudioHelper.getInstance().getContext(), MusicPlayerActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(AudioHelper.getInstance().getContext(), 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            /*
             * 然后构建notification,8.0新增渠道
             */
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                /*
                 * 渠道id,渠道名字,权限
                 */
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                /*
                 * 闪光灯关闭
                 */
                notificationChannel.enableLights(false);
                /*
                 * 震动关闭
                 */
                notificationChannel.enableVibration(false);
                mNotificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(AudioHelper.getInstance().getContext(), CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.icon_diy_view_bottom_music_view_list)
                    //大布局
                    .setCustomBigContentView(mRemoteViews)
                    //正常布局
                    .setContent(mRemoteViews);
            mNotification = builder.build();
        }
    }

    /**
     * 初始化remoteView
     */
    private void initRemoteView() {
        int layoutId = R.layout.notification_diy_music;
        mRemoteViews = new RemoteViews(AudioHelper.getInstance().getContext().getPackageName(), layoutId);
        /*
         * 歌曲名称
         */
        mRemoteViews.setTextViewText(R.id.tv_diy_view_bottom_music_view_title, mAudioBean.musicName);
        /*
         * 专辑名称
         */
        mRemoteViews.setTextViewText(R.id.tv_notification_diy_music_author_name, mAudioBean.albumName);
        /*
         * 是否收藏
         */
        mRemoteViews.setImageViewResource(R.id.iv_notification_diy_music_is_favourite, R.drawable.icon_favourite);
        /*
         * 点击上一曲
         */
        Intent nexIntent = new Intent(NotificationReceiver.ACTION_STATUS_BAR);
        nexIntent.putExtra(NotificationReceiver.EXTRA, NotificationReceiver.EXTRA_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(AudioHelper.getInstance().getContext(), nextPendingIntentRequestCode, nexIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_diy_view_bottom_music_next, nextPendingIntent);


        /*
         * 点击下一曲
         */
        Intent previousIntent = new Intent(NotificationReceiver.ACTION_STATUS_BAR);
        previousIntent.putExtra(NotificationReceiver.EXTRA, NotificationReceiver.EXTRA_NEXT);
        PendingIntent previousPendingIntent = PendingIntent.getBroadcast(AudioHelper.getInstance().getContext(), previousPendingIntentRequestCode, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_diy_view_bottom_music_previous, previousPendingIntent);


        /*
         * 点击播放或者暂停
         */
        Intent playOrPauseIntent = new Intent(NotificationReceiver.ACTION_STATUS_BAR);
        playOrPauseIntent.putExtra(NotificationReceiver.EXTRA, NotificationReceiver.EXTRA_NEXT);
        PendingIntent playOrPausePendingIntent = PendingIntent.getBroadcast(AudioHelper.getInstance().getContext(), previousPendingIntentRequestCode, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.iv_diy_view_bottom_music_play_pause, playOrPausePendingIntent);


    }
}
