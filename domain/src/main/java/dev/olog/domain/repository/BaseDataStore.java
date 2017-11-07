package dev.olog.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BaseDataStore <Model, Param> {

    Single<Model> getByParam(Param param);
    Single<List<Model>> getAll();

    Flowable<Model> observeByParam(Param param);
    Flowable<List<Model>> observeAll();

    Completable insert(Model model);

}
