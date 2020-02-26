package cn.yu.hamusic.discovery.fragments;

import android.os.Bundle;

import cn.yu.hamusic.R;
import cn.yu.lib_base.fragments.BaseFragment;

/**
 * Created on 2020-02-26
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class DiscoveryFragment extends BaseFragment {

    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_discovery;
    }
}
