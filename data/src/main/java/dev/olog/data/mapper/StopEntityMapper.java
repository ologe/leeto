package dev.olog.data.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dev.olog.data.model.StopEntity;
import dev.olog.domain.model.Stop;

public class StopEntityMapper {

    private final LocationEntityMapper locationMapper;

    @Inject StopEntityMapper(LocationEntityMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public List<Stop> mapToDomain(List<StopEntity> entity){
        List<Stop> stopList = new ArrayList<>();
        for (StopEntity stopEntity : entity) {
            stopList.add(mapToDomainOne(stopEntity));
        }
        return stopList;
    }

    public List<StopEntity> mapToEntity(List<Stop> entity){
        List<StopEntity> stopList = new ArrayList<>();
        for (Stop stopEntity : entity) {
            stopList.add(mapToEntityOne(stopEntity));
        }
        return stopList;
    }

    private Stop mapToDomainOne(StopEntity entity){
        return new Stop(
                entity.getId(),
                entity.getJourneyId(),
                entity.getDate(),
                locationMapper.mapToDomain(entity.getLocation())
        );
    }

    private StopEntity mapToEntityOne(Stop entity){
        return new StopEntity(
                entity.getId(),
                entity.getJourneyId(),
                entity.getDate(),
                locationMapper.mapToEntity(entity.getLocation())
        );
    }

}
