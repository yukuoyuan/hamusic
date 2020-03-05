package cn.yu.lib_base.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import cn.yu.lib_base.utils.StatusBarUtil;

/**
 * Created on 2020-02-26
 * 这是一个基础的活动界面
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 设置布局
         */
        setContentView(getLayout());
        /*
         * 注解绑定
         */
        ButterKnife.bind(this);
        /*
         * 初始化toolbar
         */
        initToolBar();
        /*
         * 初始化数据
         */
        initData(getIntent().getExtras());
    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        StatusBarUtil.statusBarLightMode(this);
    }

    /**
     * 初始化数据
     *
     * @param bundle 传递的数据,可能为空,请判断
     */
    protected abstract void initData(Bundle bundle);

    /**
     * 获取布局
     *
     * @return 布局
     */
    protected abstract int getLayout();
}
