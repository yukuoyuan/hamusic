package cn.yu.lib_common_ui.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.List;

import cn.yu.lib_common_ui.R;
import cn.yu.lib_imageloader.ImageLoadManger;

/**
 * Created on 2020-03-16
 * 这是一个自定义显示图片列表的viewgroup
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MultiImageViewLayout extends LinearLayout {
    /**
     * 每行的最多数量
     */
    private int rowMaxCount = 3;
    /**
     * 横向的间隙,默认是0
     */
    private int rowSpaceWidth;
    /**
     * 竖向的间隙,默认是0
     */
    private int columnSpaceWidth;
    /**
     * 图片集合
     */
    private List<String> imgList;
    /**
     * 总的宽度
     */
    public int layoutWidth = 0;

    /**
     * 每个imageView的宽度和高度
     */
    public int imgLayoutWidth = 0;
    /**
     * 条目点击监听
     */
    private OnItemClickListener onItemClickListener;

    public MultiImageViewLayout(Context context) {
        super(context);
    }

    public MultiImageViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /*
         * 初始化自定义属性
         */
        initCustomAttributes(context, attrs);
    }

    public MultiImageViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /*
         * 初始化自定义属性
         */
        initCustomAttributes(context, attrs);
    }

    /**
     * 设置图片集合
     *
     * @param imgList 图片集合
     */
    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
        initData2View();
    }

    /**
     * 初始化所有的控件
     */
    private void initView() {
        if (imgList.size() == 0) {
            return;
        }
        /*
         * 竖向的
         */
        setOrientation(VERTICAL);
        /*
         * 有多少行数据
         */
        int totalColumn = imgList.size() / rowMaxCount;
        if (totalColumn == 0) {
            /*
             * 只有一行的话
             */
            for (int i = 0; i < imgList.size(); i++) {
                LinearLayout linearLayout = createRowLinearLayout();
                ImageView imageView = createImageView(i);
                /*
                 *如果不是第一个,也不是最后一个的话
                 */
                if (i != 0) {
                    LinearLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                    layoutParams.leftMargin = rowSpaceWidth;
                    Log.d("添加控件1", String.valueOf(rowSpaceWidth));
                    imageView.setLayoutParams(layoutParams);
                }
                linearLayout.addView(imageView);
            }
            return;
        }
        if (imgList.size() % rowMaxCount > 0) {
            totalColumn = totalColumn + 1;
        }
        for (int i = 0; i < totalColumn; i++) {
            /*
             * 每一行的线性布局
             */
            LinearLayout linearLayout = createRowLinearLayout();
            /*
             *不是第一行的话,距上的margin
             */
            if (i != 0) {
                LinearLayout.LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
                layoutParams.topMargin = columnSpaceWidth;
                linearLayout.setLayoutParams(layoutParams);
            }


            int rowCount = rowMaxCount;
            if (i == totalColumn - 1) {
                /*
                 * 最后一行
                 */
                if (imgList.size() % rowMaxCount > 0) {
                    rowCount = imgList.size() % rowMaxCount;
                }
            }
            for (int j = 0; j < rowCount; j++) {
                int position = i * rowMaxCount + j;
                ImageView imageView = createImageView(position);
                /*
                 *如果不是第一个,也不是最后一个的话
                 */
                if (j != 0) {
                    LinearLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                    layoutParams.leftMargin = rowSpaceWidth;
                    Log.d("添加控件1", String.valueOf(rowSpaceWidth));
                    imageView.setLayoutParams(layoutParams);
                }
                linearLayout.addView(imageView);
                Log.d("添加控件2", String.valueOf(i * rowMaxCount + j));
            }
            Log.d("添加控件3", String.valueOf(i));
            addView(linearLayout);
        }
    }

    /**
     * 创建横向的linearlayout
     */
    private LinearLayout createRowLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        return linearLayout;
    }

    /**
     * 创建一个imageView
     *
     * @param position 列表索引
     * @return imageView
     */
    private ImageView createImageView(final int position) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imgLayoutWidth, imgLayoutWidth);
        imageView.setLayoutParams(layoutParams);
        String imgUrl = imgList.get(position);
        imageView.setId(imgUrl.hashCode());
        ImageLoadManger.getInstance().loadImage2ImageView(imageView, imgUrl);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        return imageView;
    }

    /**
     * 初始化自定义属性
     *
     * @param context 上下文
     * @param attrs   属性集合
     */
    private void initCustomAttributes(Context context, AttributeSet attrs) {
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiImageViewLayout);
            /*
             * 获取每行最多的个数
             */
            rowMaxCount = typedArray.getInt(R.styleable.MultiImageViewLayout_rowMaxCount, rowMaxCount);
            /*
             * 获取横向的间隙
             */
            rowSpaceWidth = (int) typedArray.getDimension(R.styleable.MultiImageViewLayout_rowSpaceWidth, rowSpaceWidth);
            /*
             * 竖向的间隙
             */
            columnSpaceWidth = (int) typedArray.getDimension(R.styleable.MultiImageViewLayout_columnSpaceWidth, columnSpaceWidth);
            /*
             * 记得释放
             */
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layoutWidth == 0) {
            int width = measureWidth(widthMeasureSpec);
            if (width > 0) {
                layoutWidth = width;
                ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
                int leftMargin = layoutParams.leftMargin;
                int rightMargin = layoutParams.rightMargin;
                layoutWidth = layoutWidth - leftMargin - rightMargin;
                initData2View();
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void initData2View() {
        if (layoutWidth > 0) {
            /*
             *计算出每一个的宽高
             */
            imgLayoutWidth = (layoutWidth - (rowMaxCount * rowSpaceWidth)) / rowMaxCount;
            /*
             * 初始化所有的控件
             */
            initView();
        }
    }

    /**
     * 测绘出宽度
     *
     * @param measureSpec 宽度
     * @return 宽度值
     */
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            /*
             *            相当于我们设置为wrap_content
             */
            result = Math.min(result, specSize);
        } else if (specMode == MeasureSpec.EXACTLY) {
            /*
             *相当于我们设置为match_parent或者为一个具体的值
             */
            result = specSize;

        }
        return result;
    }

    /**
     * 设置条目点击监听
     *
     * @param onItemClickListener 设置监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击事件回调
     */
    public interface OnItemClickListener {
        /**
         * 条目点击的回调方法
         *
         * @param position 索引
         */
        void onItemClick(int position);
    }
}
