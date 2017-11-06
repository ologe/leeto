package olog.dev.leeto.model;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class DisplayableJourney {

    private final long id;
    @NonNull
    private final String name;
    @NonNull
    private final String date;
    @NonNull
    private final String location;
    @Nullable
    private final String description;

    public DisplayableJourney(long id, @NonNull String name,
                              @NonNull String date, @NonNull String location,
                              @Nullable String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayableJourney that = (DisplayableJourney) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!date.equals(that.date)) return false;
        if (!location.equals(that.location)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
