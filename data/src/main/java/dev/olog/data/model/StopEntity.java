package dev.olog.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "journey_stops",
        indices = @Index(value = "stop_id", unique = true),
        foreignKeys = @ForeignKey(
                entity = JourneyEntity.class,
                parentColumns = "journey_id",
                childColumns = "journey_id_fk",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
)
public class StopEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stop_id")
    @NonNull
    private final int id;

    @ColumnInfo(name = "journey_id_fk")
    @NonNull
    private final int journeyId;

    @ColumnInfo(name = "stop_date")
    @NonNull
    private final Date date;

    @Embedded
    @NonNull
    private final LocationEntity location;

    public StopEntity(int id, int journeyId, @NonNull Date date, @NonNull LocationEntity location) {
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
    public LocationEntity getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StopEntity that = (StopEntity) o;

        if (id != that.id) return false;
        if (journeyId != that.journeyId) return false;
        if (!date.equals(that.date)) return false;
        return location.equals(that.location);
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
