package cn.yu.lib_base.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * Created on 2020-03-16
 * 这是一个基础的viewHolder
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 初始化数据到界面
     *
     * @param position 索引
     */
    public abstract void initData2View(int position);
}
