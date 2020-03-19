package cn.yu.lib_common_ui.views;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

/**
 * Created on 2020-02-26
 * 颜色渐变和缩放的指示器
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class ScaleTransitionPagerTitleView extends ColorTransitionPagerTitleView {
    /**
     * 缩放程度
     */
    private float mMinScale = 0.9f;

    public ScaleTransitionPagerTitleView(Context context) {
        super(context);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);
        /*
         * 指针进来的时候
         */
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);
        /*
         * 指针离开的时候
         */
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
    }
}
