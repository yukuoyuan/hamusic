package cn.yu.lib_base.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.yu.lib_base.utils.ListUtils;

/**
 * Created on 2020-03-16
 * 这是一个基础的适配器
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public abstract class BaseAdapter<M, H extends BaseViewHolder> extends RecyclerView.Adapter<H> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据列表
     */
    private List<M> mDataList;

    public BaseAdapter(Context context) {
        super();
        this.mContext = context;
    }

    /**
     * 设置数据源
     *
     * @param data 数据原
     */
    public void setData(List<M> data) {
        this.mDataList = data;
        notifyDataSetChanged();
    }

    /**
     * 添加数据集合到集合中
     *
     * @param datas 数据源
     */
    public void addData(List<M> datas) {
        if (ListUtils.getInstance().isEmpty(mDataList)) {
            mDataList = new ArrayList<>();
        }
        if (!ListUtils.getInstance().isEmpty(datas)) {
            this.mDataList.addAll(datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 获取条目数据
     *
     * @param position 索引
     * @return 条目数据
     */
    public M getItem(int position) {
        return mDataList.get(position);
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getItemLayoutId(), viewGroup, false);
        return onCreateDefaultViewHolder(view);
    }

    /**
     * 获取条目资源id
     *
     * @return 资源id
     */
    protected abstract int getItemLayoutId();

    /**
     * 创建默认的holder
     *
     * @param view 自定义控件
     * @return holder
     */
    protected abstract H onCreateDefaultViewHolder(View view);

    @Override
    public void onBindViewHolder(@NonNull H viewHolder, int position) {
        /*
         * 初始化数据到界面
         */
        viewHolder.initData2View(position);
    }

    @Override
    public int getItemCount() {
        if (ListUtils.getInstance().isEmpty(mDataList)) {
            return 0;
        }
        return mDataList.size();
    }
}
