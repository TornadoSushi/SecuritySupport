package com.infinity.jerry.securitysupport.common.z_utils.z_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by root on 2/11/17.
 */

public class ZViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFrag;


    public ZViewPagerAdapter(FragmentManager fm , List<Fragment> listFrag) {
        super(fm);
        this.listFrag = listFrag;
    }

    @Override
    public Fragment getItem(int position) {
        if(listFrag != null){
            return listFrag.get(position);
        }
        return null;

    }

    @Override
    public int getCount() {
        if(listFrag != null){
            return listFrag.size();
        }
        return 0;
    }


}

