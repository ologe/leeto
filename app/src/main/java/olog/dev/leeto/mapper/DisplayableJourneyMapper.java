package olog.dev.leeto.mapper;

import javax.inject.Inject;

import dev.olog.domain.model.Journey;
import dev.olog.domain.model.Stop;
import dev.olog.shared.mapper.FlowableMapper;
import olog.dev.leeto.R;
import olog.dev.leeto.model.DisplayableItem;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.utility.DateUtils;

public class DisplayableJourneyMapper
        extends FlowableMapper<Journey, DisplayableItem<DisplayableJourney>> {

    @Inject DisplayableJourneyMapper() {
    }

    @Override
    public DisplayableItem<DisplayableJourney> map(Journey journey) {
        Stop firstStop = journey.getStopList().get(0);
//        Date date;
//        String locationName;
//        if (stopList.isEmpty()){
//            date = new Date();
//            locationName = "";
//        } else {
//            Stop firstStop = journey.getStopList().get(0);
//            date = firstStop.getDate();
//            locationName = firstStop.getLocation().getName();
//        }

        DisplayableJourney displayableJourney = new DisplayableJourney(
                journey.getId(),
                journey.getName(),
                DateUtils.toString(firstStop.getDate()),
                firstStop.getLocation().getName(),
                journey.getDescription()
        );

        return new DisplayableItem<>(
                R.layout.item_journey,
                displayableJourney
        );
    }
}
