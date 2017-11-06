package dev.olog.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.ArrayList;
import java.util.List;

import dev.olog.data.model.JourneyEntity;
import dev.olog.data.model.JourneyWithStopsEntity;
import dev.olog.data.model.StopEntity;
import io.reactivex.Flowable;

@Dao
public abstract class JourneyDao {

    @Query("SELECT * FROM journeys")
    @Transaction
    public abstract Flowable<List<JourneyWithStopsEntity>> loadAll();

    @Insert
    abstract long insertJourney(JourneyEntity journeyEntity);

    @Insert
    abstract void insertStopList(List<StopEntity> stopEntityList);

    @Transaction
    public int insert(JourneyWithStopsEntity journeyWithStopsEntity){
        JourneyEntity journey = journeyWithStopsEntity.journey;
        List<StopEntity> stopList = journeyWithStopsEntity.stopList;
        long journeyId = insertJourney(journey);
        List<StopEntity> stopListWithForeignKey = new ArrayList<>();
        for (StopEntity stopEntity : stopList) {
            stopListWithForeignKey.add(new StopEntity(
                    stopEntity.getId(),
                    (int) journeyId,
                    stopEntity.getDate(),
                    stopEntity.getLocation()
            ));
        }
        insertStopList(stopListWithForeignKey);

        return -1;
    }

}
