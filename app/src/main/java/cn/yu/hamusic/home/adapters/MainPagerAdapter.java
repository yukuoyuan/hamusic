package cn.yu.hamusic.home.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import cn.yu.hamusic.discovery.fragments.DiscoveryFragment;
import cn.yu.hamusic.home.beans.CHANNEL;
import cn.yu.hamusic.home.fragments.HomeFragment;
import cn.yu.hamusic.friend.fragments.FriendFragment;

/**
 * Created on 2020-02-26
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private CHANNEL[] mChannels;

    public MainPagerAdapter(FragmentManager fm, CHANNEL[] channels) {
        super(fm);
        this.mChannels = channels;
    }

    @Override
    public Fragment getItem(int i) {
        int type = mChannels[i].getValue();
        switch (type) {
            case CHANNEL.DISCORY_ID:
                return DiscoveryFragment.newInstance();
            case CHANNEL.FRIEND_ID:
                return FriendFragment.newInstance();
            case CHANNEL.MINE_ID:
            default:
                return HomeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return mChannels == null ? 0 : mChannels.length;
    }
}
