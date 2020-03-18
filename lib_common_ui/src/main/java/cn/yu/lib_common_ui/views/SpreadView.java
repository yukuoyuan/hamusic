package cn.yu.lib_common_ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.yu.lib_common_ui.R;

/**
 * Created on 2020-03-18
 * 这是一个带扩散效果的view
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class SpreadView extends View {
    /**
     * 中心圆的半径
     */
    private float mCenterCircleRadius;
    /**
     * 中心圆的颜色
     */
    private int mCenterCircleColor;
    /**
     * 扩散的最大半径
     */
    private float mMaxCircleRadius;
    /**
     * 扩散延迟的时间
     */
    private int mSpreadDealyTime;

    public SpreadView(Context context) {
        this(context, null);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
         * 初始化自定义属性
         */
        initAttributes(context, attrs);
    }

    /**
     * 初始化自定义属性
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void initAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpreadView);
            /*
             * 中心圆的半径
             */
            mCenterCircleRadius = typedArray.getDimension(R.styleable.SpreadView_centerCircleRadius, 0);
            /*
             * 中心圆的颜色
             */
            mCenterCircleColor = typedArray.getColor(R.styleable.SpreadView_centerCircleColor, 0);
            /*
             * 扩散的最大半径
             */
            mMaxCircleRadius = typedArray.getDimension(R.styleable.SpreadView_maxCircleRadius, 0);
            /*
             * 扩散延迟的时间
             */
            mSpreadDealyTime = typedArray.getInt(R.styleable.SpreadView_spreadDealyTime, 0);

            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
