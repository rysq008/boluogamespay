package com.game.helper.adapter.home;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.game.helper.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    ArrayList<BaseFragment> list;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager, ArrayList<BaseFragment> list) {
        super(fragmentManager);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<BaseFragment> getList() {
        return list;
    }

    public void setList(ArrayList<BaseFragment> list) {
        this.list = list;
    }

    @Override
    public BaseFragment getItem(int position) {

        return list.get(position % list.size());
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragment f = (BaseFragment) super.instantiateItem(container, position);
        return f;
    }
}
