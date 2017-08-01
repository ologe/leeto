package olog.dev.leeto.activity_add_journey;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
import olog.dev.leeto.databinding.ActivityAddJourneyBinding;
import olog.dev.leeto.model.pojo.Journey;
import olog.dev.leeto.model.pojo.Location;
import olog.dev.leeto.model.repository.JourneyRepository;

public class AddJourneyActivity extends AbsMorphActivity implements AddJourneyContract.View{

    private ActivityAddJourneyBinding binding;

    private AddJourneyContract.Presenter presenter;
    private int year = 1970;
    private int month = 1;
    private int day = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_journey);

        super.onCreate(savedInstanceState);

        binding.journeyDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

        presenter = new AddJourneyPresenter(this);

        setupCalendar();

        binding.setPresenter(presenter);

        binding.setJourney(new Journey());
        binding.setLocation(new Location());

    }

    private void setupCalendar(){
        Calendar calendar = Calendar.getInstance();

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {

            this.year = year;
            this.month = month;
            this.day = day;

            String date = day + "-" + month + "-" + year;
            binding.journeyDate.setText(date);

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        binding.journeyDate.setInputType(InputType.TYPE_NULL);
        binding.setDatePicker(datePickerDialog);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.save.setOnClickListener(view -> {

            if(!checkNull()) {
                Toast.makeText(this, "Fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            Journey journey = binding.getJourney();
            journey.addStop(calendar.getTime(), binding.getLocation());

            JourneyRepository.getInstance(this).addJourney(this, journey);

            save(null);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.save.setOnClickListener(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            presenter.onLocationRequestClick(this, binding.getLocation());
        }
    }

    private boolean checkNull(){
        return !binding.journeyName.getText().toString().equals("") &&
                !binding.journeyDescription.getText().toString().equals("") &&
                !binding.locationName.getText().toString().equals("") &&
                !binding.locationLatitude.getText().toString().equals("") &&
                !binding.locationLongitude.getText().toString().equals("");
    }

    @Override
    public void updateLocation(Location location) {
        binding.setLocation(location);
    }



    @Override
    protected View getContainerLayout() {
        return binding.container;
    }

    @Override
    protected View getRootLayout() {
        return binding.root;
    }

    @Override
    protected View getDiscardButton() {
        return binding.discard;
    }
}
