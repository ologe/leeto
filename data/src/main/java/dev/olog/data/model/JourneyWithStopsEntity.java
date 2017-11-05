package dev.olog.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class JourneyWithStopsEntity {

    @Embedded
    public JourneyEntity journey;

    @Relation(parentColumn = "journey_id", entityColumn = "journey_id_fk")
    public List<StopEntity> stopList;

}
