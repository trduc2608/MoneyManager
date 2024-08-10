package com.example.moneymanager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RelativeLayout btnHome, btnStatistic, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHome = findViewById(R.id.btn_home);
        btnStatistic = findViewById(R.id.btn_statistic);
        btnProfile = findViewById(R.id.btn_profile);

        btnHome.setBackgroundColor(Color.parseColor("#DB6619"));
        btnStatistic.setBackgroundColor(Color.parseColor("#EF9D65"));
        btnProfile.setBackgroundColor(Color.parseColor("#EF9D65"));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new InputFragment())
                .commit();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();

                btnHome.setBackgroundColor(Color.parseColor("#DB6619"));
                btnStatistic.setBackgroundColor(Color.parseColor("#EF9D65"));
                btnProfile.setBackgroundColor(Color.parseColor("#EF9D65"));
            }
        });

        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new InputFragment())
                        .commit();
                btnHome.setBackgroundColor(Color.parseColor("#EF9D65"));
                btnStatistic.setBackgroundColor(Color.parseColor("#DB6619"));
                btnProfile.setBackgroundColor(Color.parseColor("#EF9D65"));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .commit();

                btnHome.setBackgroundColor(Color.parseColor("#EF9D65"));
                btnStatistic.setBackgroundColor(Color.parseColor("#EF9D65"));
                btnProfile.setBackgroundColor(Color.parseColor("#DB6619"));
            }
        });
    }
}