package dev.olog.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "locations",
        indices = @Index(value = "location_id", unique = true))
public class LocationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "location_id")
    @NonNull
    private final int id;

    @ColumnInfo(name = "location_name")
    @NonNull
    private final String name;

    @ColumnInfo(name = "location_lat")
    @NonNull
    private final double latitude;

    @ColumnInfo(name = "location_long")
    @NonNull
    private final double longitude;

    @ColumnInfo(name = "location_adress")
    @NonNull
    private final String address;

    @ColumnInfo(name = "location_description")
    @Nullable
    private final String description;

    public LocationEntity(int id, String name, double latitude, double longitude,
                          @NonNull String address, @Nullable String description) {
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

    @NonNull
    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationEntity that = (LocationEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (!name.equals(that.name)) return false;
        if (!address.equals(that.address)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + address.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
