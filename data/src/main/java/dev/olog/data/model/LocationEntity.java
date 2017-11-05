package dev.olog.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "locations",
        indices = @Index(value = "id", unique = true))
public class LocationEntity {

    @PrimaryKey(autoGenerate = true)
    private final int id;

    private final String name;

    private final long latitude;

    private final long longitude;

    private final String address;

    private final String description;

    public LocationEntity(int id,
                          String name,
                          long latitude,
                          long longitude,
                          String address,
                          String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
