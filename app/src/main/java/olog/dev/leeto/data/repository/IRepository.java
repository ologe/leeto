package olog.dev.leeto.data.repository;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.data.model.Journey;

public interface IRepository {

    @NonNull
    Observable<List<Journey>> observeToData();

    void addJourney(@NonNull Journey journey);

    void deleteJourney(@NonNull Journey journey);

    @NonNull
    Disposable registerToUpdates();

    @NonNull
    Journey getJourney(long journeyId);
}
