package com.neocaptainnemo.notesfeb17;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.neocaptainnemo.notesfeb17.ui.InfoFragment;
import com.neocaptainnemo.notesfeb17.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new ListFragment())
                    .commit();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_list:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new ListFragment())
                                .commit();

                        return true;

                    case R.id.action_info:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new InfoFragment())
                                .commit();

                        return true;

                }
                return false;
            }
        });
    }
}