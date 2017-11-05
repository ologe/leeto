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
}
