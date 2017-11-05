package dev.olog.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import dev.olog.data.model.JourneyEntity;
import dev.olog.data.model.LocationEntity;
import dev.olog.data.model.StopEntity;

@Database(entities = {
        JourneyEntity.class,
        StopEntity.class,
        LocationEntity.class

}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract JourneyDao journeyDao();

}
