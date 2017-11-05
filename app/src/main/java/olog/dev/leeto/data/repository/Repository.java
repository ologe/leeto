package olog.dev.leeto.data.repository;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dev.olog.shared.ApplicationContext;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.utility.collections.CollectionsUtils;

@Singleton
public class Repository implements IRepository {

    private static final int INSERT = 0;
    private static final int REMOVE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INSERT, REMOVE})
    private @interface OperationType {}

    private static final String JOURNEY_LIST_FILE_NAME = "journeysList.json";

    private Context context;
    private BehaviorSubject<List<Journey>> cache;
    private PublishProcessor<Pair<Journey, Integer>> operationQueue;

    @Inject Repository(@ApplicationContext Context context){
        this.context = context;
        cache = BehaviorSubject.create();
        operationQueue = PublishProcessor.create();

        Single.fromCallable(this::loadFromStorage)
                .subscribeOn(Schedulers.io())
                .subscribe(journeyList -> cache.onNext(journeyList));
    }

    @NonNull
    @Override
    public Observable<List<Journey>> observeToData(){
        return cache;
    }

    @NonNull
    @Override
    public Disposable registerToUpdates() {
        return operationQueue
                .onBackpressureBuffer()
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe(journeyTypePair -> {

                    int type = journeyTypePair.second;
                    Journey journey = journeyTypePair.first;

                    handleJourneyImpl(journey, type);
                });
    }

    @NonNull
    private List<Journey> loadFromStorage(){
        List<Journey> journeyList = new ArrayList<>();

        try (InputStream inputStream = context.openFileInput(JOURNEY_LIST_FILE_NAME)){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String received;
            StringBuilder stringBuilder = new StringBuilder();

            while ((received = bufferedReader.readLine()) != null){
                stringBuilder.append(received);
            }

            String jsonOutput = stringBuilder.toString();

            Gson loadedData = new Gson();
            journeyList = loadedData.fromJson(jsonOutput, new TypeToken<List<Journey>>(){}.getType());

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return journeyList;
    }

    @Override
    public void addJourney(@NonNull Journey journey){
        operationQueue.onNext(Pair.create(journey, INSERT));
    }

    @Override
    public void deleteJourney(@NonNull Journey journey) {
        operationQueue.onNext(Pair.create(journey, REMOVE));
    }

    private void handleJourneyImpl(@NonNull Journey journey, @OperationType int type){
        List<Journey> journeyList = new ArrayList<>(cache.getValue());

        if (type == REMOVE){
            journeyList.remove(journey);
        } else if(type == INSERT){
            journeyList.add(0, journey);
        }

        cache.onNext(journeyList);

        final String json = new Gson().toJson(journeyList, new TypeToken<List<Journey>>(){}.getType());

        try (OutputStreamWriter outputStream = new OutputStreamWriter(context.openFileOutput(JOURNEY_LIST_FILE_NAME, Context.MODE_PRIVATE))){

            outputStream.write(json);

        } catch (IOException ex){
            ex.printStackTrace();
        }

    }

    public void addStopToJourney(Context context, Journey j, Stop stop){
//        Single.create(e -> {
//            int index = -1;
//            for (int i = 0; i < journeyList.size(); i++) {
//                if(j.getName().equals(journeyList.get(i).getName())){
//                    index = i;
//                    break;
//                }
//            }
//
//            Journey journey = journeyList.get(index);
//            journey.addStop(stop);
//
//            saveJourneysList(context)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(v -> {}, Throwable::printStackTrace);
//
//            e.onSuccess(true);
//
//        }).observeOn(Schedulers.io())
//                .subscribe(v -> {}, Throwable::printStackTrace);

    }

    @NonNull
    @Override
    public Journey getJourney(long journeyId) {
        return CollectionsUtils.first(cache.getValue(), journey -> journey.getId() == journeyId);
    }
}
