package olog.dev.leeto.ui.activity_add_journey;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dev.olog.domain.interactor.InsertJourneyUseCase;
import dev.olog.domain.model.Journey;
import dev.olog.domain.model.Location;
import dev.olog.domain.model.Stop;
import io.reactivex.Completable;
import olog.dev.leeto.utility.Preconditions;

import static olog.dev.leeto.utility.AppConstants.UNSET_ID;

public class AddJourneyActivityViewModel extends ViewModel {

    private static final double LATITUDE_UPPER_BOUND = 90;
    private static final double LATITUDE_LOWER_BOUND = -90;

    private static final double LONGITUDE_UPPER_BOUND = 180;
    private static final double LONGITUDE_LOWER_BOUND = -180;

    private final InsertJourneyUseCase insertJourneyUseCase;

    public AddJourneyActivityViewModel(InsertJourneyUseCase insertJourneyUseCase) {
        this.insertJourneyUseCase = insertJourneyUseCase;
    }

    public Completable createJourney(
            String journeyName,
            String journeyDescription,
            Date journeyDate,
            String locationName,
            String locationLatitude,
            String locationLongitude,
            String locationAddress,
            String locationDescription){

        Location location = new Location(
                UNSET_ID,
                locationName,
                checkCoordinate(locationLatitude, LATITUDE_LOWER_BOUND, LATITUDE_UPPER_BOUND),
                checkCoordinate(locationLongitude, LONGITUDE_LOWER_BOUND, LONGITUDE_UPPER_BOUND),
                locationAddress, locationDescription);

        Stop stop = new Stop(UNSET_ID, UNSET_ID, journeyDate, location);
        List<Stop> stopList = new ArrayList<>();
        stopList.add(stop);
        Journey journey = new Journey(UNSET_ID, journeyName, journeyDescription, stopList);
        return insertJourneyUseCase.execute(journey);
    }

    private double checkCoordinate(String locationLatitude, double lowerBound, double upperBound){
        try {
            double latitude = Double.parseDouble(locationLatitude);
            Preconditions.assertRange(latitude, lowerBound, upperBound);
            return latitude;
        } catch (NumberFormatException | AssertionError ex){
            throw new AssertionError("invalid coordinate " + locationLatitude + ":" +
                    " lower bound " + lowerBound + ", upper bound " + upperBound, ex);
        }
    }

}
