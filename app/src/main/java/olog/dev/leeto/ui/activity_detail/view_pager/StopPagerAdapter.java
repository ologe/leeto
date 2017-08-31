package olog.dev.leeto.ui.activity_detail.view_pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import olog.dev.leeto.data.model.Stop;

public class StopPagerAdapter extends FragmentStatePagerAdapter {

    private List<Stop> dataSet;

    public StopPagerAdapter(List<Stop> dataSet, FragmentManager fragmentManager){
        super(fragmentManager);
        this.dataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
//        return StopFragment.newInstance(dataSet.get(position));
        return null;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }
}
