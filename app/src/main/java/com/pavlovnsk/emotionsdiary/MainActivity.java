package com.pavlovnsk.emotionsdiary;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsFragment;
import com.pavlovnsk.emotionsdiary.Fragments.StatisticsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmotionsFragment()).commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_emotion:
                    selectedFragment = new EmotionsFragment();
                    break;
                case R.id.navigation_statistic:
                    selectedFragment = new StatisticsFragment();
                    break;
                case R.id.navigation_info:
                    break;
                default:
                    break;
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        }
    };
}
