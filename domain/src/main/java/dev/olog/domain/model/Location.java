package dev.olog.domain.model;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Location {

    private final int id;
    @NonNull
    private final String name;
    private final double latitude;
    private final double longitude;
    @NonNull
    private final String address;
    @Nullable
    private final String description;

    public Location(int id, @NonNull String name,
                    double latitude, double longitude,
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

        Location location = (Location) o;

        if (id != location.id) return false;
        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;
        if (!name.equals(location.name)) return false;
        if (!address.equals(location.address)) return false;
        return description != null ? description.equals(location.description) : location.description == null;
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


