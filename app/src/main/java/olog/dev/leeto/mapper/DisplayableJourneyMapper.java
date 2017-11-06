package olog.dev.leeto.mapper;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dev.olog.domain.model.Journey;
import dev.olog.domain.model.Stop;
import dev.olog.shared.mapper.FlowableMapper;
import olog.dev.leeto.model.DisplayableJourney;
import olog.dev.leeto.utility.DateUtils;

public class DisplayableJourneyMapper
        extends FlowableMapper<Journey, DisplayableJourney> {

    @Inject DisplayableJourneyMapper() {
    }

    @Override
    public DisplayableJourney map(Journey journey) {
        List<Stop> stopList = journey.getStopList();
        Date date;
        String locationName;
        if (stopList.isEmpty()){
            date = new Date();
            locationName = "";
        } else {
            Stop firstStop = journey.getStopList().get(0);
            date = firstStop.getDate();
            locationName = firstStop.getLocation().getName();
        }

        return new DisplayableJourney(
                journey.getId(),
                journey.getName(),
                DateUtils.toString(date),
                locationName,
                journey.getDescription()
        );
    }
}
