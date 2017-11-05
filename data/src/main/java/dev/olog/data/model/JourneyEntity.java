package dev.olog.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "journeys",
        indices = @Index(value = "journey_id", unique = true)
)
public class JourneyEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "journey_id")
    private final int id;

    @ColumnInfo(name = "journey_name")
    private final String name;

    @ColumnInfo(name = "journey_description")
    private final String description;

    public JourneyEntity(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

}
