//package olog.dev.leeto.ui._activity_detail;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import java.util.List;
//
//import olog.dev.leeto.data.model.Stop;
//import olog.dev.leeto.ui.fragment_stop.StopFragment;
//
//public class StopPagerAdapter extends FragmentStatePagerAdapter {
//
//    private List<Stop> dataSet;
//
//    public StopPagerAdapter(List<Stop> dataSet, FragmentManager fragmentManager){
//        super(fragmentManager);
//        this.dataSet = dataSet;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return StopFragment.newInstance(position);
//    }
//
//    @Override
//    public int getCount() {
//        return dataSet.size();
//    }
//}
