package cn.yu.hamusic.home.fragments;

import android.os.Bundle;

import cn.yu.hamusic.R;
import cn.yu.lib_base.fragments.BaseFragment;

/**
 * Created on 2020-02-26
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }
}
