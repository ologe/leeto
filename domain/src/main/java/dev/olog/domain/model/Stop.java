package dev.olog.domain.model;

import java.util.Date;

import io.reactivex.annotations.NonNull;

public class Stop {

    private final int id;
    private final int journeyId;
    @NonNull
    private final Date date;
    @NonNull
    private final Location location;

    public Stop(int id, int journeyId, @NonNull Date date, @NonNull Location location) {
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

    @NonNull
    public Date getDate() {
        return date;
    }

    @NonNull
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stop stop = (Stop) o;

        if (id != stop.id) return false;
        if (journeyId != stop.journeyId) return false;
        if (!date.equals(stop.date)) return false;
        return location.equals(stop.location);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + journeyId;
        result = 31 * result + date.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}
