package dev.olog.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.olog.data.db.JourneyDao;
import dev.olog.data.mapper.JourneyWithStopsMapper;
import dev.olog.data.model.JourneyWithStopsEntity;
import dev.olog.domain.model.Journey;
import dev.olog.domain.repository.JourneyDataStore;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Singleton
public class JourneyDataStoreImpl implements JourneyDataStore {

    private final JourneyDao dao;
    private final JourneyWithStopsMapper mapper;

    @Inject JourneyDataStoreImpl(
            JourneyDao dao,
            JourneyWithStopsMapper mapper) {

        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Journey>> getAll() {
        return observeAll().firstOrError();
    }

    @Override
    public Single<Journey> getByParam(Void aVoid) {
        return null;
    }

    @Override
    public Flowable<List<Journey>> observeAll() {
        return dao.loadAll()
                .flatMapSingle(mapper);
    }

    @Override
    public Completable insert(Journey journey) {
        JourneyWithStopsEntity journeyWithStopsEntity = mapper.toEntity(journey);
        return Completable.fromCallable(() -> dao.insert(journeyWithStopsEntity));
    }
}
