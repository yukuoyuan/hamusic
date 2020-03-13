package cn.yu.lib_audio.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.yu.lib_audio.R;
import cn.yu.lib_audio.bean.AudioBean;
import cn.yu.lib_audio.utils.ListUtils;
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

    public MusicIndicatorViewPagerAdapter(ArrayList<AudioBean> audioBeans, Context context) {
        mAudioBeans = audioBeans;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (ListUtils.getInstance().isEmoty(mAudioBeans)) {
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
        return rootView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
