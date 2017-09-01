package olog.dev.leeto.data.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import olog.dev.leeto.utility.AppConstants;

@Value
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
public class Journey implements HasId {

    long id;
    String name;
    String shortDescription;
    List<Stop> stopList;

    public Journey(@NonNull String name,
                   @NonNull String shortDescription) {
        if(TextUtils.isEmpty(shortDescription))
            shortDescription = AppConstants.NO_DESCRIPTION;

        this.id = System.nanoTime();
        this.name = name;
        this.shortDescription = shortDescription;

        stopList = new ArrayList<>();
    }

    public void addStop(@NonNull Date date, @NonNull Location location){
        stopList.add(new Stop(date, location));
    }

    @NonNull
    public Stop getFirstStop(){
        return stopList.get(0);
    }


}
