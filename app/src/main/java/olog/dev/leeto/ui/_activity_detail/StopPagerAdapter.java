package olog.dev.leeto.ui._activity_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import dev.olog.domain.model.Stop;
import olog.dev.leeto.ui.fragment_map.MapFragment;


public class StopPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Stop> dataSet;

    public StopPagerAdapter(List<Stop> dataSet, FragmentManager fragmentManager){
        super(fragmentManager);
        this.dataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new MapFragment();
        }
        return new FakeFragment();
//        return StopFragment.newInstance(position);
    }

    @Override
    public int getCount() {
//        return dataSet.size();
        return 5;
    }
}
