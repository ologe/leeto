package olog.dev.leeto.data.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import olog.dev.leeto.utility.AppConstants;

public class Journey {

    private final long id;
    private final String name;
    private final String description;
    private final List<Stop> stopList;

    public Journey(
            @NonNull String name,
            @NonNull String description) {

        if(TextUtils.isEmpty(description)){
            description = AppConstants.NO_DESCRIPTION;
        }

        this.id = System.nanoTime();
        this.name = name;
        this.description = description;

        stopList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addStop(@NonNull Date date, @NonNull Location location){
        stopList.add(new Stop(date, location));
    }

    @NonNull
    public Stop getFirstStop(){
        return stopList.get(0);
    }


}
