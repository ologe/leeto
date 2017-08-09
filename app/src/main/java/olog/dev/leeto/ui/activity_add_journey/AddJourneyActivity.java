package olog.dev.leeto.ui.activity_add_journey;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
import olog.dev.leeto.dagger.component.DaggerAddJourneyComponent;
import olog.dev.leeto.dagger.module.AddJourneyModule;
import olog.dev.leeto.model.permission.IPermissionHelper;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;
import olog.dev.leeto.utility.DateUtils;

public class AddJourneyActivity extends AbsMorphActivity implements AddJourneyContract.View {

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

    @Inject AddJourneyContract.Presenter presenter;
    @Inject IPermissionHelper permissionHelper;
    @Inject CompositeDisposable subscriptions;
    @Inject Calendar calendar;

    @OnTouch(R.id.journeyDate)
    public boolean showDatePicker(View view){
        datePickerDialog.show();
        return false;
    }

    @OnClick(R.id.locationRequest)
    public void requestLocation(View view){
        presenter.onLocationRequestClick();
    }

    public static void startActivity(@NonNull FloatingActionButton view){
        Context context = view.getContext();
        Intent intent = new Intent(context, AddJourneyActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) context, view, view.getTransitionName());
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DaggerAddJourneyComponent.builder()
                .appComponent(getAppComponent())
                .addJourneyModule(new AddJourneyModule(this))
                .build()
                .inject(this);

        // butterKnife already bound in superclass

        getLifecycle().addObserver(presenter);

        setupCalendar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveButton.setOnClickListener(view -> {

            Journey journey = new Journey(this,
                    journeyName.getText().toString(),
                    journeyDescription.getText().toString());

            Location location = new Location(this,
                    locationName.getText().toString(),
                    Double.parseDouble(locationLatitude.getText().toString()),
                    Double.parseDouble(locationLongitude.getText().toString()),
                    locationAddress.getText().toString(),
                    locationDescription.getText().toString()
            );

            journey.addStop(calendar.getTime(), location);

            presenter.addJourneyToRepository(journey);

            dismiss();
        });

        Disposable disposable = Observable.combineLatest(
                isTextViewEmpty(journeyName),
                isTextViewEmpty(locationName),
                isTextViewEmpty(locationAddress),
                isTextViewEmpty(locationLatitude),
                isTextViewEmpty(locationLongitude),
                (aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5) -> aBoolean || aBoolean2 || aBoolean3 || aBoolean4 || aBoolean5
        ).subscribe(allAreEmpty -> saveButton.setEnabled(!allAreEmpty));

        subscriptions.add(disposable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscriptions.clear();
        saveButton.setOnClickListener(null);
    }

    @Override
    public void updateLocation(@Nullable Location location) {
        if(location == null) return;

        locationName.setText(location.getName());
        locationAddress.setText(location.getAddress());
        locationLatitude.setText(String.valueOf(location.getLatitude()));
        locationLongitude.setText(String.valueOf(location.getLongitude()));
        locationDescription.setText(String.valueOf(location.getShortDescription()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_journey;
    }

    private void setupCalendar(){

        // mandatory to initialize the view with some date
        journeyDate.setText(olog.dev.leeto.utility.TextUtils.dateToString(new Date()));

        datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {

            calendar.set(year, month, day);
            journeyDate.setText(DateUtils.toString(calendar.getTime()));

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // keyboard will not appear
        journeyDate.setInputType(InputType.TYPE_NULL);
    }

    private Observable<Boolean> isTextViewEmpty(TextView editText){
        return RxTextView.afterTextChangeEvents(editText)
                .map(o -> o.view().getText().toString())
                .map(TextUtils::isEmpty);
    }

}