package dev.olog.data.mapper;

import javax.inject.Inject;

import dev.olog.data.model.LocationEntity;
import dev.olog.domain.model.Location;

public class LocationEntityMapper {

    @Inject LocationEntityMapper() {
    }

    public Location mapToDomain(LocationEntity entity){
        return new Location(
                entity.getId(),
                entity.getName(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAddress(),
                entity.getDescription()
        );
    }

    public LocationEntity mapToEntity(Location location){
        return new LocationEntity(
                location.getId(),
                location.getName(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAddress(),
                location.getDescription()
        );
    }
}
