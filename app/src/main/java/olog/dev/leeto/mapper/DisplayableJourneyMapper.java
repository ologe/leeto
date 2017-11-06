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

    private static final long QUARTER_THRESHOLD = 1000 * 60 * 15;

    @Inject DisplayableJourneyMapper() {
    }

    @Override
    public DisplayableItem<DisplayableJourney> map(Journey journey) {
        Stop firstStop = journey.getStopList().get(0);

        boolean isRecent = (System.currentTimeMillis() - firstStop.getDate().getTime()) < QUARTER_THRESHOLD;

        DisplayableJourney displayableJourney = new DisplayableJourney(
                journey.getId(),
                journey.getName(),
                DateUtils.toString(firstStop.getDate()),
                firstStop.getLocation().getName(),
                journey.getDescription(),
                isRecent);

        return new DisplayableItem<>(
                R.layout.item_journey,
                displayableJourney
        );
    }
}
