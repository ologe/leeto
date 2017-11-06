package olog.dev.leeto.ui.activity_add_journey;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import olog.dev.leeto.BuildConfig;
import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
import olog.dev.leeto.databinding.ActivityAddJourneyBinding;
import olog.dev.leeto.location.DisabledLocationException;
import olog.dev.leeto.location.LocationManager;
import olog.dev.leeto.location.LocationModel;
import olog.dev.leeto.utility.DateUtils;
import olog.dev.leeto.utility.RxUtils;

public class AddJourneyActivity extends AbsMorphActivity {

    private DatePickerDialog datePickerDialog;

    @Inject AddJourneyActivityViewModel viewModel;
    @Inject Calendar calendar;
    @Inject Provider<LocationModel> mockData;
    @Inject LocationManager locationManager;

    private ActivityAddJourneyBinding binding;

    private Disposable saveJourneyDisposable;
    private Disposable locationRequestDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = (ActivityAddJourneyBinding) viewDataBinding;

        // butterKnife already bound in superclass
        setupCalendar();

        LiveDataReactiveStreams.fromPublisher(
                Flowable.combineLatest(
                        isTextViewEmpty(binding.journeyName), isTextViewEmpty(binding.locationName),
                        isTextViewEmpty(binding.locationAddress), isTextViewEmpty(binding.locationLatitude),
                        isTextViewEmpty(binding.locationLongitude),
                        (aBoolean, aBoolean2, aBoolean3, aBoolean4, aBoolean5) -> aBoolean || aBoolean2 || aBoolean3 || aBoolean4 || aBoolean5
                ).map(alLeastOneIsEmpty -> !alLeastOneIsEmpty)
        ).observe(this, binding.save::setEnabled);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        saveJourneyDisposable = RxView.clicks(binding.save)
                .filter(o -> binding.save.isEnabled())
                .firstOrError()
                .flatMapCompletable(o -> viewModel.createJourney(
                        binding.journeyName.getText().toString(),
                        binding.journeyDescription.getText().toString(),
                        calendar.getTime(),
                        binding.locationName.getText().toString(),
                        binding.locationLatitude.getText().toString(),
                        binding.locationLongitude.getText().toString(),
                        binding.locationAddress.getText().toString(),
                        binding.locationDescription.getText().toString()
                ))
                .doOnComplete(() -> setResult(RESULT_OK))
                .subscribe(this::dismiss, Throwable::printStackTrace);

        binding.journeyDate.setOnTouchListener((view, motionEvent) -> {
            datePickerDialog.show();
            return false;
        });
        binding.journeyBigHeader.setOnClickListener(view -> {
            if (BuildConfig.DEBUG){
                LocationModel location = mockData.get();

                binding.journeyName.setText("Trip to " + location.getName());
                binding.setLocation(location);
            }
        });
        binding.locationHeader.setOnClickListener(view -> requestLocation());
        binding.locationHeaderHint.setOnClickListener(view -> requestLocation());
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribe(saveJourneyDisposable);
        binding.journeyDate.setOnTouchListener(null);
        binding.journeyBigHeader.setOnClickListener(null);
        binding.locationHeader.setOnClickListener(null);
        binding.locationHeaderHint.setOnClickListener(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        RxUtils.unsubscribe(locationRequestDisposable);
    }

    public void requestLocation(){
        RxUtils.unsubscribe(locationRequestDisposable);

        locationRequestDisposable = locationManager.request()
                .subscribe(binding::setLocation, throwable -> {
                    int messageId;
                    if (throwable instanceof DisabledLocationException){
                        messageId = R.string.enable_location;
                    } else {
                        throwable.printStackTrace();
                        messageId = R.string.location_not_found;
                    }
                    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
                });
    }

    private void setupCalendar(){

        // mandatory to initialize the view with some date
        binding.journeyDate.setText(DateUtils.toString(new Date()));

        datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {

            calendar.set(year, month, day);
            binding.journeyDate.setText(DateUtils.toString(calendar.getTime()));

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        // keyboard will not appear
        binding.journeyDate.setInputType(InputType.TYPE_NULL);
    }

    private Flowable<Boolean> isTextViewEmpty(TextView editText){
        return RxTextView.afterTextChangeEvents(editText)
                .map(o -> o.view().getText().toString())
                .map(TextUtils::isEmpty)
                .toFlowable(BackpressureStrategy.LATEST);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_journey;
    }

}
