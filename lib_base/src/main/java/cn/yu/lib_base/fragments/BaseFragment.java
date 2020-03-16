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
    /**
     * 根布局
     */
    public View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayout(), null);
        if (isUsedButterKnife()) {
            mUnbinder = ButterKnife.bind(this, mView);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
         * 初始化控件
         */
        initView();
        /*
         * 初始化监听
         */
        initListener();
        /*
         * 初始化数据
         */
        initData(getArguments());
    }

    /**
     * 是否使用butterknife注解框架
     *
     * @return 默认使用
     */
    protected boolean isUsedButterKnife() {
        return true;
    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }

    /**
     * 初始化控件
     */
    protected void initView() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isUsedButterKnife()) {
            mUnbinder.unbind();
        }
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
