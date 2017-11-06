package dev.olog.domain.model;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

public class Journey {

    private final int id;
    @NonNull
    private final String name;
    @Nullable
    private final String description;
    @NonNull
    private final List<Stop> stopList;

    public Journey(int id, @NonNull String name,
                   @Nullable String description, @NonNull List<Stop> stopList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stopList = stopList;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public List<Stop> getStopList() {
        return stopList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (id != journey.id) return false;
        if (!name.equals(journey.name)) return false;
        if (description != null ? !description.equals(journey.description) : journey.description != null)
            return false;
        return stopList.equals(journey.stopList);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + stopList.hashCode();
        return result;
    }
}
