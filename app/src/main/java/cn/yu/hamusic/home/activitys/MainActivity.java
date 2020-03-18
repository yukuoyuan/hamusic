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

import com.alibaba.android.arouter.facade.annotation.Route;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.yu.hamusic.R;
import cn.yu.hamusic.home.adapters.MainPagerAdapter;
import cn.yu.hamusic.home.beans.CHANNEL;
import cn.yu.hamusic.home.iviews.IMainView;
import cn.yu.lib_audio.AudioHelper;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_base.activitys.BaseActivity;
import cn.yu.lib_common_ui.views.ScaleTransitionPagerTitleView;


/**
 * 这是一个首页
 *
 * @author yukuoyuan
 * @date
 */
@Route(path = "/app/main/main_activity")
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
     * 播放列表
     */
    private ArrayList<AudioBean> mLists = new ArrayList<>();

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

        initMusicList();
    }

    private void initMusicList() {

        mLists.add(new AudioBean("100001", "http://sp-sycdn.kuwo.cn/resource/n2/85/58/433900159.mp3",
                "以你的名字喊我", "周杰伦", "七里香",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698076304&di=e6e99aa943b72ef57b97f0be3e0d2446&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201401%2F04%2F20140104170315_XdG38.jpeg"
                , "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典"));
        mLists.add(
                new AudioBean("100002", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3", "勇气",
                        "梁静茹", "勇气",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698193627&di=711751f16fefddbf4cbf71da7d8e6d66&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D213168965%2C1040740194%26fm%3D214%26gp%3D0.jpg"
                        , "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典"));
        mLists.add(
                new AudioBean("100003", "http://sp-sycdn.kuwo.cn/resource/n2/52/80/2933081485.mp3", "灿烂如你",
                        "汪峰", "春天里",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698239736&di=3433a1d95c589e31a36dd7b4c176d13a&imgtype=0&src=http%3A%2F%2Fpic.zdface.com%2Fupload%2F201051814737725.jpg"
                        , "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典"));
        mLists.add(
                new AudioBean("100004", "http://sr-sycdn.kuwo.cn/resource/n2/33/25/2629654819.mp3", "小情歌",
                        "五月天", "小幸运",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698289780&di=5146d48002250bf38acfb4c9b4bb6e4e&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20131220%2Fbki-20131220170401-1254350944.jpg"
                        , "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典"));

        AudioHelper.getInstance().startMusicService(mLists);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public final void initIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);

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
        midMainIndicator.setBackgroundColor(Color.WHITE);

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
