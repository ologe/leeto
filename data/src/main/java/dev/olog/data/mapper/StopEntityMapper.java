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

    public List<Stop> map(List<StopEntity> entity){
        List<Stop> stopList = new ArrayList<>();
        for (StopEntity stopEntity : entity) {
            stopList.add(mapOne(stopEntity));
        }
        return stopList;
    }

    private Stop mapOne(StopEntity entity){
        return new Stop(
                entity.getDate(),
                locationMapper.map(entity.getLocation())
        );
    }

}
