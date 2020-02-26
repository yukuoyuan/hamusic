package cn.yu.lib_base.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created on 2020-02-26
 * 这是一个基础的碎片界面
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), null);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
         * 初始化数据
         */
        initData(getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 初始化数据
     *
     * @param arguments 传递的数据
     */
    protected abstract void initData(Bundle arguments);

    /**
     * 获取布局
     *
     * @return 布局
     */
    protected abstract int getLayout();
}
