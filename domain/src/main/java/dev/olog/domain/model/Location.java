package dev.olog.domain.model;

public class Location {

    private final String name;
    private final long latitude;
    private final long longitude;
    private final String address;
    private final String description;

    public Location(String name, long latitude, long longitude, String address, String description) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.description = description;
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


