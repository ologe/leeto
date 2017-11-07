package olog.dev.leeto.ui._activity_detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import olog.dev.leeto.ui.fragment_journeys_map.JourneyMapFragment;
import olog.dev.leeto.ui.fragment_journeys_media.JourneyMediaFragment;
import olog.dev.leeto.ui.fragment_journeys_stops.JourneyStopsFragment;


public class JourneyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_COUNT = 3;

    public JourneyPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new JourneyMapFragment();
        } else if (position == 1){
            return new JourneyStopsFragment();
        } else {
            return new JourneyMediaFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
