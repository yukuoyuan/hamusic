package cn.yu.hamusic;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.yu.lib_audio.AudioHelper;

/**
 * Created on 2020-03-06
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class HaMusicApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*
         * 初始化音乐组件
         */
        AudioHelper.getInstance().setContext(this);
        /*
         * 初始化ARouter
         */
        ARouter.init(this);
    }
}
