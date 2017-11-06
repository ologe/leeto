package dev.olog.domain.model;

import java.util.Date;

public class Stop {

    private final int id;
    private final int journeyId;
    private final Date date;
    private final Location location;

    public Stop(int id, int journeyId, Date date, Location location) {
        this.id = id;
        this.journeyId = journeyId;
        this.date = date;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public Date getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (id != stop.id) return false;
        if (date != null ? !date.equals(stop.date) : stop.date != null) return false;
        return location != null ? location.equals(stop.location) : stop.location == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
