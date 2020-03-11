package cn.yu.lib_audio.servicess;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.events.AudioEvent;
import cn.yu.lib_audio.mediaplayer.control.AudioController;
import cn.yu.lib_audio.receivers.NotificationReceiver;
import cn.yu.lib_audio.views.NotificationHelper;

/**
 * Created on 2020-03-11
 * 这是一个播放音乐的后台服务
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicService extends Service {
    /**
     * key
     */
    private static final String DATA_AUDIOS = "AUDIOS";
    /**
     * 意图
     */
    private static final String ACTION_START = "ACTION_START";
    /**
     * 用来交互通知的通知
     */
    private NotificationReceiver mReceiver;
    /**
     * 播放列表
     */
    private ArrayList<AudioBean> mAudioBeans;

    /**
     * 提供给外部使用打开服务
     *
     * @param audioBeans 数据集合
     */
    public static void startMusicService(ArrayList<AudioBean> audioBeans) {
        Intent intent = new Intent(AudioHelper.getInstance().getContext(), MusicService.class);
        intent.setAction(ACTION_START);
        //还需要传list数据进来
        intent.putExtra(DATA_AUDIOS, audioBeans);
        AudioHelper.getInstance().getContext().startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
         * 得到播放列表
         */
        mAudioBeans = (ArrayList<AudioBean>) intent.getSerializableExtra(DATA_AUDIOS);
        /*
         * 初始化通知
         */
        NotificationHelper.getInstance().init();
        /*
         * 控制器设置数据并开始播放
         */
        AudioController.getInstance().setAudioBeanArrayList(mAudioBeans);
        AudioController.getInstance().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        /*
         * 注册广播,用来监听通知里的按钮点击事件
         */
        registerBroadcastReceiver();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioEvent(AudioEvent audioEvent) {
        switch (audioEvent.getStatus()) {
            case PAUSE:
                /*
                 * 暂停
                 */
                NotificationHelper.getInstance().showPauseStatus();
                break;
            case START:
                /*
                 * 开始播放
                 */
                NotificationHelper.getInstance().showPlayStatus();
                break;
            case LOAD:
                /*
                 * 展示加载的状态
                 */
                AudioBean audioBean = audioEvent.getAudioBean();
                NotificationHelper.getInstance().showLoadStatus(audioBean);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unRegisterBroadcastReceiver();
    }

    private void registerBroadcastReceiver() {
        if (mReceiver == null) {
            mReceiver = new NotificationReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(NotificationReceiver.ACTION_STATUS_BAR);
            registerReceiver(mReceiver, filter);
        }
    }

    private void unRegisterBroadcastReceiver() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
