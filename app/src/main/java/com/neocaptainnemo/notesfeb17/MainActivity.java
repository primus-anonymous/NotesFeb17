package com.neocaptainnemo.notesfeb17;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.neocaptainnemo.notesfeb17.ui.AuthFragment;
import com.neocaptainnemo.notesfeb17.ui.InfoFragment;
import com.neocaptainnemo.notesfeb17.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            openNotes();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_list:
                        openNotes();

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

        getSupportFragmentManager().setFragmentResultListener(AuthFragment.KEY_AUTHORIZED, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                openNotes();
            }
        });
    }

    private void openNotes() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new AuthFragment())
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, new ListFragment())
                    .commit();
        }
    }
}