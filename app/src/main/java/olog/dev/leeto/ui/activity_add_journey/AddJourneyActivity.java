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

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
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
    @Inject Provider<String> mockData;

    @OnTouch(R.id.journeyDate)
    public boolean showDatePicker(){
        datePickerDialog.show();
        return false;
    }

    private Disposable saveJourneyDisposable;

    @OnClick(R.id.journeyBigHeader)
    public void generateMockData(){
        String location = mockData.get();

        journeyName.setText("Trip to " + location);
        locationName.setText(location);
        locationAddress.setText(location + " address");
        locationLatitude.setText("" + (new Random().nextInt(90)));
        locationLongitude.setText("" + (new Random().nextInt(180)) );
    }

//    @OnClick(R.id.locationRequest)
//    public void requestLocation(View view){
//        presenter.onLocationRequestClick();
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // butterKnife already bound in superclass
        setupCalendar();

        LiveDataReactiveStreams.fromPublisher(
                Flowable.combineLatest(
                        isTextViewEmpty(journeyName),
                        isTextViewEmpty(locationName),
                        isTextViewEmpty(locationAddress),
                        isTextViewEmpty(locationLatitude),
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
                .flatMapCompletable(o -> viewModel.createJourney(
                        journeyName.getText().toString(),
                        journeyDescription.getText().toString(),
                        calendar.getTime(),
                        locationName.getText().toString(),
                        locationLatitude.getText().toString(),
                        locationLongitude.getText().toString(),
                        locationAddress.getText().toString(),
                        locationDescription.getText().toString()
                )).subscribe(this::dismiss, Throwable::printStackTrace);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribe(saveJourneyDisposable);
    }

    public void updateLocation() {
//        if(location == null) return;

//        locationName.setText(location.getName());
//        locationAddress.setText(location.getAddress());
//        locationLatitude.setText(String.valueOf(location.getLatitude()));
//        locationLongitude.setText(String.valueOf(location.getLongitude()));
//        locationDescription.setText(String.valueOf(location.getDescription()));
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
