package dev.olog.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "journey_stops",
        indices = @Index(value = "id", unique = true),
        foreignKeys = @ForeignKey(
                entity = JourneyEntity.class,
                parentColumns = "journey_id",
                childColumns = "journey_id_fk",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE,
                deferred = true
        )
)
public class StopEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stop_id")
    private final int id;

    @ColumnInfo(name = "journey_id_fk")
    private final int journeyId;

    @ColumnInfo(name = "stop_date")
    private final Date date;

    @Embedded
    private final LocationEntity location;

    public StopEntity(int id, int journeyId, Date date, LocationEntity location) {
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
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + journeyId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
