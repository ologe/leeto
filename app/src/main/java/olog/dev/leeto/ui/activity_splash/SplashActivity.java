package olog.dev.leeto.ui.activity_splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;

import olog.dev.leeto.R;
import olog.dev.leeto.ui._activity_main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MapsInitializer.initialize(this);

        map = findViewById(R.id.map);
        map.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();

        Intent intent = new Intent(this, MainActivity.class);
        // flags clears the activity backStack
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }
}
