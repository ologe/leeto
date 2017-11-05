package dev.olog.domain.model;

import java.util.Date;

public class Stop {

    private final Date date;
    private final Location location;

    public Stop(Date date, Location location) {
        this.date = date;
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }
}
