package dev.olog.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "journeys",
        indices = @Index(value = "journey_id", unique = true)
)
public class JourneyEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "journey_id")
    @NonNull
    private final int id;

    @ColumnInfo(name = "journey_name")
    @NonNull
    private final String name;

    @ColumnInfo(name = "journey_description")
    @Nullable
    private final String description;

    public JourneyEntity(int id, @NonNull String name, @Nullable String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JourneyEntity that = (JourneyEntity) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
