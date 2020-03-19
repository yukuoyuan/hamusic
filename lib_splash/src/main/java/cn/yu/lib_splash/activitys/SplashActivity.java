package cn.yu.lib_splash.activitys;

import android.os.Bundle;
import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.yu.lib_base.activitys.BaseActivity;
import cn.yu.lib_base.config.Constants;
import cn.yu.lib_splash.R;

/**
 * Created on 2020-03-18
 * 这是一个过度界面
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void initData(Bundle bundle) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*
                 * 跳转首页
                 */
                ARouter.getInstance().build(Constants.Router.MainActivity).navigation();
            }
        }, 3000L);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }
}
