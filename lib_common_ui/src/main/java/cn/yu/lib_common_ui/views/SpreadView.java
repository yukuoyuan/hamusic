package cn.yu.lib_common_ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

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
    private int mSpreadDelayTime;
    /**
     * 扩散的圆的个数
     */
    private int mSpreadCount;

    /**
     * 中心圆的画笔
     */
    private Paint mCenterPaint;
    /**
     * 扩散圆的画笔
     */
    private Paint mSpreadPaint;
    /**
     * 中间圆的x和y
     */
    private float centerX, centerY;
    /**
     * 扩散圆的层级数量,每个元素代表扩散的距离
     */
    private List<Integer> spreadRadius = new ArrayList<>();
    /**
     * 对应每层圆的透明度
     */
    private List<Integer> alphas = new ArrayList<>();
    /**
     * 距离的间距
     */
    private int mSpreadSpace;
    /**
     * 渐变的间距
     */
    private int mSpreadAlphaSpace = 5;

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
        /*
         * 初始化画笔
         */
        initPaint();
        /*
         * 初始化容器
         */
        initSpreadArray();
    }

    /**
     * 初始化容器
     */
    private void initSpreadArray() {
        /*
         * 第一个外层圆距离内部为0
         */
        spreadRadius.add(0);
        /*
         * 第一个外层圆的透明度是完全不透明
         */
        alphas.add(255);
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
            mSpreadDelayTime = typedArray.getInt(R.styleable.SpreadView_spreadDelayTime, 0);
            /*
             *扩散的距离
             */
            mSpreadCount = typedArray.getInt(R.styleable.SpreadView_spreadCount, 0);

            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //圆心位置
        centerX = w / 2;
        centerY = h / 2;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        /*
         * 中心圆的画笔
         */
        mCenterPaint = new Paint();
        mCenterPaint.setAntiAlias(true);
        mCenterPaint.setColor(mCenterCircleColor);

        /*
         * 扩散圆的画笔
         */
        mSpreadPaint = new Paint();
        mSpreadPaint.setAntiAlias(true);
        /*
         * 只是画边
         */
        mSpreadPaint.setStyle(Paint.Style.STROKE);
        /*
         * 设置完全不透明
         */
        mSpreadPaint.setAlpha(255);
        mSpreadPaint.setColor(mCenterCircleColor);
        /*
         * 计算出扩散的距离和渐变扩散的度
         */
        mSpreadSpace = (int) (mMaxCircleRadius / mSpreadCount);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
         * 绘制中心圆
         */
        canvas.drawCircle(centerX, centerY, mCenterCircleRadius, mCenterPaint);

        /*
         * 循环每一层的圆进行绘制
         */
        for (int i = 0; i < spreadRadius.size(); i++) {
            /*
             * 设置画笔的透明度
             */
            int alpha = alphas.get(i);
            mSpreadPaint.setAlpha(alpha);
            /*
             * 扩散圆距离中心圆的距离
             */
            int width = spreadRadius.get(i);
            //绘制扩散的圆
            canvas.drawCircle(centerX, centerY, mCenterCircleRadius + width, mSpreadPaint);
            /*
             * 每次扩散圆距离递增,透明度递减
             */
            if (alpha > 0 && width < (mMaxCircleRadius * 3)) {
                alpha = alpha - mSpreadAlphaSpace > 0 ? alpha - mSpreadAlphaSpace : 1;
                alphas.set(i, alpha);
                spreadRadius.set(i, width + mSpreadSpace);
            }
        }
        /*
         * 如果扩散圆超过最大距离了,那么就再次增加一个
         */
        if (spreadRadius.get(spreadRadius.size() - 1) > mMaxCircleRadius) {
            /*
             * 外层圆距离内部为0
             */
            spreadRadius.add(0);
            /*
             * 外层圆的透明度是完全不透明
             */
            alphas.add(255);
        }
        /*
         * 如果增加了八个圆,就要移除第一个
         */
        if (spreadRadius.size() > mSpreadCount) {
            Log.d("SpreadView", "移除");
            spreadRadius.remove(0);
            alphas.remove(0);
        }

        /*
         * 延迟更新视图
         */
        postInvalidateDelayed(mSpreadDelayTime);

    }
}
