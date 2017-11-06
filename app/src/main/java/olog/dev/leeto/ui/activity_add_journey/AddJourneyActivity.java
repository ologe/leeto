package olog.dev.leeto.ui.activity_add_journey;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.BuildConfig;
import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
import olog.dev.leeto.databinding.ActivityAddJourneyBinding;
import olog.dev.leeto.ui.LocationManager;
import olog.dev.leeto.ui.LocationModel;
import olog.dev.leeto.utility.DateUtils;
import olog.dev.leeto.utility.RxUtils;

public class AddJourneyActivity extends AbsMorphActivity {

    private DatePickerDialog datePickerDialog;

    @BindView(R.id.save) Button saveButton;

    @BindView(R.id.journeyDate) TextView journeyDate;
    @BindView(R.id.journeyName) TextInputEditText journeyName;
    @BindView(R.id.journeyDescription) TextInputEditText journeyDescription;

    @BindView(R.id.locationName) TextInputEditText locationName;
    @BindView(R.id.locationAddress) TextInputEditText locationAddress;
    @BindView(R.id.locationLatitude) TextInputEditText locationLatitude;
    @BindView(R.id.locationLongitude) TextInputEditText locationLongitude;
    @BindView(R.id.locationDescription) TextInputEditText locationDescription;

    @Inject AddJourneyActivityViewModel viewModel;
    @Inject Calendar calendar;
    @Inject Provider<LocationModel> mockData;
    @Inject LocationManager locationManager;

    private ActivityAddJourneyBinding binding;

    @OnTouch(R.id.journeyDate)
    public boolean showDatePicker(){
        datePickerDialog.show();
        return false;
    }

    private Disposable saveJourneyDisposable;
    private Disposable locationRequestDisposable;

    @OnClick(R.id.journeyBigHeader)
    public void generateMockData(){
        if (BuildConfig.DEBUG){
            LocationModel location = mockData.get();

            journeyName.setText("Trip to " + location.getName());
            binding.setLocation(location);
        }
    }

    @OnClick({R.id.locationHeader, R.id.locationHeaderHint})
    public void requestLocation(){
        RxUtils.unsubscribe(locationRequestDisposable);

        locationRequestDisposable = locationManager.request()
                .subscribe(binding::setLocation, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(this, R.string.location_not_found, Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = (ActivityAddJourneyBinding) viewDataBinding;

        // butterKnife already bound in superclass
        setupCalendar();

        LiveDataReactiveStreams.fromPublisher(
                Flowable.combineLatest(
                        isTextViewEmpty(journeyName), isTextViewEmpty(locationName),
                        isTextViewEmpty(locationAddress), isTextViewEmpty(locationLatitude),
                        isTextViewEmpty(locationLongitude),
                        (aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5) -> aBoolean || aBoolean2 || aBoolean3 || aBoolean4 || aBoolean5
                ).map(alLeastOneIsEmpty -> !alLeastOneIsEmpty)
        ).observe(this, saveButton::setEnabled);
    }

    @Override
    protected void onResume() {
        super.onResume();

        saveJourneyDisposable = RxView.clicks(saveButton)
                .filter(o -> saveButton.isEnabled())
                .firstOrError()
                .flatMapCompletable(o -> viewModel.createJourney(
                        journeyName.getText().toString(),
                        journeyDescription.getText().toString(),
                        calendar.getTime(),
                        locationName.getText().toString(),
                        locationLatitude.getText().toString(),
                        locationLongitude.getText().toString(),
                        locationAddress.getText().toString(),
                        locationDescription.getText().toString()
                ))
                .doOnComplete(() -> setResult(RESULT_OK))
                .subscribe(this::dismiss, Throwable::printStackTrace);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribe(saveJourneyDisposable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxUtils.unsubscribe(locationRequestDisposable);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_journey;
    }

    private void setupCalendar(){

        // mandatory to initialize the view with some date
        journeyDate.setText(DateUtils.toString(new Date()));

        datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {

            calendar.set(year, month, day);
            journeyDate.setText(DateUtils.toString(calendar.getTime()));

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // keyboard will not appear
        journeyDate.setInputType(InputType.TYPE_NULL);
    }

    private Flowable<Boolean> isTextViewEmpty(TextView editText){
        return RxTextView.afterTextChangeEvents(editText)
                .map(o -> o.view().getText().toString())
                .map(TextUtils::isEmpty)
                .toFlowable(BackpressureStrategy.LATEST);
    }

}
