package olog.dev.leeto.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;
import olog.dev.leeto.model.pojo.Stop;

public class JourneyRepository {

    private static final String JOURNEY_LIST_FILE_NAME = "journeysList.json";

    private static JourneyRepository instance = null;

    private List<Journey> journeyList;
    private ReplaySubject<List<Journey>> publisher;

    private static final Object LOCK = new Object();

    public synchronized static JourneyRepository getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = new JourneyRepository(context);
                }
            }
        }
        return instance;
    }

    private JourneyRepository(Context context){
        publisher = ReplaySubject.create();

        journeyList = new ArrayList<>();

        loadFromJson(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        journeys -> publisher.onNext(journeys),
                        throwable -> {
                            // craete mock data if no data was found
                            throwable.printStackTrace();
                            createMockData(context)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            list -> publisher.onNext(list),
                                            t -> {
                                                publisher.onNext(Collections.emptyList());
                                                t.printStackTrace();
                                            }
                                    );
                        }
                );
    }

    public Observable<List<Journey>> getObservable(){
        return publisher;
    }

    private Single<List<Journey>> loadFromJson(Context context){
        return Single.create( emitter -> {

            InputStream inputStream = context.openFileInput(JOURNEY_LIST_FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String received;
            StringBuilder stringBuilder = new StringBuilder();

            while ((received = bufferedReader.readLine()) != null){
                stringBuilder.append(received);
            }
            inputStream.close();

            String jsonOutput = stringBuilder.toString();

            Gson loadedData = new Gson();
            journeyList = loadedData.fromJson(jsonOutput, new TypeToken<List<Journey>>(){}.getType());

            emitter.onSuccess(journeyList);

        });
    }

    private Single<List<Journey>> createMockData(Context context){
        System.out.println("mock data");

        return Single.create(emitter -> {
            Date date = new Date(System.currentTimeMillis());

            Location location = new Location(
                    "New York",
                    40.718552,
                    -74.002608,
                    "Broadway",
                    "Descrizione corta");

            Journey journey = new Journey(
                    "Viaggio a New York",
                    "Descrizione lunga lunga lunga lunga lunga lunga lunga lunga lunga lunga ," +
                            "lunga lunga lunga lunga lunga lunga lunga lunga sperando sia più righe tipo 2 ",
                    date, location);


            journey.addStop(date, new Location(
                    "New York",
                    40.725820,
                    -73.981609,
                    "East Village",
                    "Descrizione lunga lunga lunga lunga lunga lunga lunga lunga lunga lunga ," +
                            "lunga su 2 righe"));
            journey.addStop(date,  new Location(
                    "New York",
                    40.810009,
                    -73.946772,
                    "Harlem",
                    "Descrizione lunga lunga lunga lunga lunga lunga lunga lunga lunga lunga ," +
                            "lunga lunga lunga lunga sperando sia più righe tipo 2 "));
            journey.addStop(date,  new Location(
                    "New York",
                    40.778998,
                    -73.968334,
                    "Central Park",
                    "Descrizione corta"));

            journeyList.add(journey);
            journeyList.add(journey);
            journeyList.add(journey);
            journeyList.add(journey);
            journeyList.add(journey);
            journeyList.add(journey);

            saveJourneysList(context)
                    .subscribeOn(Schedulers.io())
                    .subscribe(aBoolean -> {}, Throwable::printStackTrace);

            emitter.onSuccess(journeyList);
        });
    }

    public void addJourney(@NonNull Context context,@NonNull Journey journey){
        journeyList.add(journey);

        publisher.onNext(journeyList);

        saveJourneysList(context)
                .subscribeOn(Schedulers.io())
                .subscribe(aBoolean -> {}, Throwable::printStackTrace);
    }

    private Single<Boolean> saveJourneysList(Context context){
        return Single.create(emitter -> {

            final String json = new Gson().toJson(journeyList, new TypeToken<List<Journey>>(){}.getType());

            OutputStreamWriter outputStream = new OutputStreamWriter(
                    context.openFileOutput(JOURNEY_LIST_FILE_NAME, Context.MODE_PRIVATE));
            outputStream.write(json);
            outputStream.close();

            emitter.onSuccess(true);
        });
    }

    public void addStopToJourney(Context context, Journey j, Stop stop){
        Single.create(e -> {
            int index = -1;
            for (int i = 0; i < journeyList.size(); i++) {
                if(j.getName().equals(journeyList.get(i).getName())){
                    index = i;
                    break;
                }
            }

            Journey journey = journeyList.get(index);
            journey.addStop(stop);

            saveJourneysList(context)
                    .subscribeOn(Schedulers.io())
                    .subscribe(v -> {}, Throwable::printStackTrace);

            e.onSuccess(true);

        }).observeOn(Schedulers.io())
                .subscribe(v -> {}, Throwable::printStackTrace);

    }
}
