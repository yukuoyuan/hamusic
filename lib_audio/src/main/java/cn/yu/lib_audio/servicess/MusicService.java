package cn.yu.lib_audio.servicess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created on 2020-03-11
 * 这是一个播放音乐的后台服务
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
