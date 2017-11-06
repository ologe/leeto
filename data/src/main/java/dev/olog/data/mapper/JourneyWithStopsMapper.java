package dev.olog.data.mapper;

import javax.inject.Inject;

import dev.olog.data.model.JourneyEntity;
import dev.olog.data.model.JourneyWithStopsEntity;
import dev.olog.domain.model.Journey;
import dev.olog.shared.mapper.FlowableMapper;

public class JourneyWithStopsMapper
        extends FlowableMapper<JourneyWithStopsEntity, Journey> {

    private final StopEntityMapper stopMapper;

    @Inject JourneyWithStopsMapper(StopEntityMapper stopMapper) {
        this.stopMapper = stopMapper;
    }

    @Override
    public Journey map(JourneyWithStopsEntity entity) {
        JourneyEntity journey = entity.journey;

        return new Journey(
                journey.getId(),
                journey.getName(),
                journey.getDescription(),
                stopMapper.mapToDomain(entity.stopList)
        );
    }

    public JourneyWithStopsEntity toEntity(Journey journey){
        return JourneyWithStopsEntity.from(
                new JourneyEntity(
                        journey.getId(),
                        journey.getName(),
                        journey.getDescription(),
                        System.currentTimeMillis()
                ), stopMapper.mapToEntity(journey.getStopList())
        );
    }


}
