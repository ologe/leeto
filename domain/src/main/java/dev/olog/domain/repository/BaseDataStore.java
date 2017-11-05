package dev.olog.domain.repository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface BaseDataStore <Model, Param> {

    Single<List<Model>> getAll();

    Single<Model> getByParam(Param param);

    Flowable<List<Model>> observeAll();

}
