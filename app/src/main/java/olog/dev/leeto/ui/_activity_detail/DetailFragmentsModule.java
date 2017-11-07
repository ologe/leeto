package olog.dev.leeto.ui._activity_detail;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import olog.dev.leeto.ui.fragment_journeys_map.JourneyMapFragment;
import olog.dev.leeto.ui.fragment_journeys_media.JourneyMediaFragment;
import olog.dev.leeto.ui.fragment_journeys_stops.JourneyStopsFragment;

@Module
public abstract class DetailFragmentsModule {

    @ContributesAndroidInjector
    abstract JourneyMapFragment mapFragment();

    @ContributesAndroidInjector
    abstract JourneyMediaFragment mediaFragment();

    @ContributesAndroidInjector
    abstract JourneyStopsFragment stopsFragment();

}
