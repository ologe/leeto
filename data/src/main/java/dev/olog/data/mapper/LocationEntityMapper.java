package dev.olog.data.mapper;

import javax.inject.Inject;

import dev.olog.data.model.LocationEntity;
import dev.olog.domain.model.Location;

public class LocationEntityMapper {

    @Inject LocationEntityMapper() {
    }

    public Location map(LocationEntity entity){
        return new Location(
                entity.getName(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAddress(),
                entity.getDescription()
        );
    }
}
