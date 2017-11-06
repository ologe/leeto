package dev.olog.domain.model;

import java.util.List;

public class Journey {

    private final int id;
    private final String name;
    private final String description;
    private final List<Stop> stopList;

    public Journey(int id, String name, String description, List<Stop> stopList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stopList = stopList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Stop> getStopList() {
        return stopList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (id != journey.id) return false;
        if (name != null ? !name.equals(journey.name) : journey.name != null) return false;
        if (description != null ? !description.equals(journey.description) : journey.description != null)
            return false;
        return stopList != null ? stopList.equals(journey.stopList) : journey.stopList == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (stopList != null ? stopList.hashCode() : 0);
        return result;
    }
}
