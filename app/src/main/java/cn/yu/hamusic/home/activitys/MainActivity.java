package cn.yu.hamusic.home.activitys;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;


import butterknife.BindView;
import butterknife.OnClick;
import cn.yu.hamusic.R;
import cn.yu.hamusic.home.adapters.MainPagerAdapter;
import cn.yu.hamusic.home.beans.CHANNEL;
import cn.yu.hamusic.home.iviews.IMainView;
import cn.yu.lib_base.activitys.BaseActivity;
import cn.yu.lib_common_ui.indicators.ScaleTransitionPagerTitleView;


/**
 * @author yukuoyuan
 * @date
 */
public class MainActivity extends BaseActivity implements IMainView {


    @BindView(R.id.iv_main_title_left)
    ImageView ivMainTitleLeft;
    @BindView(R.id.iv_main_title_right)
    ImageView ivMainTitleRight;
    @BindView(R.id.mid_main_indicator)
    MagicIndicator midMainIndicator;
    @BindView(R.id.cl_main_title)
    ConstraintLayout clMainTitle;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    /**
     * 指针列表
     */
    private static final CHANNEL[] CHANNELS = {CHANNEL.MY, CHANNEL.DISCOVERY, CHANNEL.FRIEND};

    /**
     * 初始化数据
     *
     * @param extras 上个界面传递的数据
     */
    @Override
    protected void initData(Bundle extras) {
        /*
         * 初始化Viewpager
         */
        initViewPager();
        /*
         * 初始化indicator
         */
        initIndicator();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public final void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        CommonNavigatorAdapter commonNavigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS == null ? 0 : CHANNELS.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                /*
                 * 设置文本
                 */
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                /*
                 * 设置字体内容
                 */
                simplePagerTitleView.setText(CHANNELS[index].getKey());
                /*
                 * 设置字体大小
                 */
                simplePagerTitleView.setTextSize(19);
                /*
                 * 设置样式
                 */
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                /*
                 * 设置正常的样式
                 */
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                /*
                 * 设置选中的样式
                 */
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                /*
                 * 设置点击事件
                 */
                simplePagerTitleView.setOnClickListener(v -> vpMain.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0F;
            }
        };
        /*
         * 设置指针的适配器
         */
        commonNavigator.setAdapter(commonNavigatorAdapter);
        /*
         * 设置指针
         */
        midMainIndicator.setNavigator(commonNavigator);
        /*
         * 绑定viewPager
         */
        ViewPagerHelper.bind(midMainIndicator, vpMain);
    }

    @Override
    public void initViewPager() {
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), CHANNELS);
        vpMain.setAdapter(mainPagerAdapter);
    }

    @OnClick({R.id.iv_main_title_left, R.id.iv_main_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_title_left:
                if (dlMain.isDrawerOpen(Gravity.LEFT)) {
                    dlMain.closeDrawer(Gravity.LEFT);
                } else {
                    dlMain.openDrawer(Gravity.LEFT);
                }
                break;
            case R.id.iv_main_title_right:
                break;
            default:
                break;
        }
    }
}
