package olog.dev.leeto.model.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import olog.dev.leeto.model.pojo.Journey;

public interface RepositoryInterface {

    @NonNull
    Observable<List<Journey>> observeToData();

    void addJourney(@NonNull Journey journey);

    void deleteJourney(@NonNull Journey journey);
}
