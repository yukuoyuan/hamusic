package cn.yu.lib_audio.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.mediaplayer.control.AudioController;
import cn.yu.lib_base.utils.ListUtils;
import cn.yu.lib_imageloader.ImageLoadManger;

/**
 * Created on 2020-03-13
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MusicIndicatorViewPagerAdapter extends PagerAdapter {
    /**
     * 数据列表
     */
    private ArrayList<AudioBean> mAudioBeans;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 用来缓存动画
     */
    private SparseArray<ObjectAnimator> mObjectAnimatorSparseArray = new SparseArray<>();

    public MusicIndicatorViewPagerAdapter(ArrayList<AudioBean> audioBeans, Context context) {
        mAudioBeans = audioBeans;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (ListUtils.getInstance().isEmpty(mAudioBeans)) {
            return 0;
        }
        return mAudioBeans.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_music_indicator_view_page, null);
        /*
         * 得到背景
         */
        ImageView ivDiyViewMusicIndicatorViewBg = rootView.findViewById(R.id.iv_diy_view_music_indicator_view_bg);
        AudioBean audioBean = mAudioBeans.get(position);
        ImageLoadManger.getInstance().loadCircleImage2ImageView(ivDiyViewMusicIndicatorViewBg, audioBean.albumPic);
        container.addView(rootView);
        ObjectAnimator objectAnimator = createAnimator(ivDiyViewMusicIndicatorViewBg);
        if (AudioController.getInstance().getQueueIndex() == position && AudioController.getInstance().isStartStatus()) {
            objectAnimator.start();
        }
        mObjectAnimatorSparseArray.put(position, objectAnimator);
        return rootView;
    }

    /**
     * 创建旋转的属性动画
     *
     * @param ivDiyViewMusicIndicatorViewBg 控件
     * @return 动画
     */
    private ObjectAnimator createAnimator(ImageView ivDiyViewMusicIndicatorViewBg) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivDiyViewMusicIndicatorViewBg, View.ROTATION.getName(), 0, 360);
        /*
         * 时间
         */
        objectAnimator.setDuration(10000);
        /*
         *平稳的动画
         */
        objectAnimator.setInterpolator(new LinearInterpolator());
        /*
         * 无限循环
         */
        objectAnimator.setRepeatCount(-1);
        return objectAnimator;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 根据索引获得动画
     *
     * @param pos 索引
     * @return 动画
     */
    public ObjectAnimator getAnimator(int pos) {
        return mObjectAnimatorSparseArray.get(pos);
    }
}
