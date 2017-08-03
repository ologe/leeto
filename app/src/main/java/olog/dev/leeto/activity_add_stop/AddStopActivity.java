package olog.dev.leeto.activity_add_stop;

import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import olog.dev.leeto.R;
import olog.dev.leeto.base.AbsMorphActivity;
import olog.dev.leeto.databinding.ActivityAddStopBinding;
import olog.dev.leeto.model.pojo.Location;
import olog.dev.leeto.model.pojo.Stop;

public class AddStopActivity extends AbsMorphActivity implements AddStopContract.View{

    private ActivityAddStopBinding binding;
    private AddStopContract.Presenter presenter;

    private int year = 1970;
    private int month = 1;
    private int day = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_stop);

        super.onCreate(savedInstanceState);

        binding.journeyDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));


        presenter = new AddStopPresenter(this);

        binding.setPresenter(presenter);
//        binding.setLocation(new Location());

        setupCalendar();
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

            Stop stop = new Stop(calendar.getTime(), binding.getLocation());

//            save(stop);
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

    @Override
    public void updateLocation(Location location) {
        binding.setLocation(location);
    }

    private boolean checkNull(){
        return !binding.locationName.getText().toString().equals("") &&
                !binding.locationLatitude.getText().toString().equals("") &&
                !binding.locationLongitude.getText().toString().equals("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_stop;
    }
}
