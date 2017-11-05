package dev.olog.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import dev.olog.data.model.JourneyWithStopsEntity;
import io.reactivex.Flowable;

@Dao
public abstract class JourneyDao {

    @Query("SELECT * FROM journeys")
    @Transaction
    public abstract Flowable<List<JourneyWithStopsEntity>> loadAll();

}
