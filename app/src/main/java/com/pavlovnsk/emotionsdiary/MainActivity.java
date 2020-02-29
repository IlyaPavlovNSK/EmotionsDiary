package com.pavlovnsk.emotionsdiary;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pavlovnsk.emotionsdiary.Adapters.MenuList.MenuAdapter;
import com.pavlovnsk.emotionsdiary.Adapters.MenuList.MenuListPresenter;
import com.pavlovnsk.emotionsdiary.Adapters.MenuList.MenuViewHolder;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsFragment;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsHistoryFragment;
import com.pavlovnsk.emotionsdiary.Fragments.StatisticsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MenuViewHolder.OnItemListener {

    @Inject
    MenuListPresenter menuListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EmotionsFragmentComponent component = DaggerEmotionsFragmentComponent.builder().globalModule(new GlobalModule(this)).build();
        component.inject(this);

        MenuAdapter menuAdapter = new MenuAdapter(menuListPresenter, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmotionsFragment()).commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RecyclerView menuRecyclerView = findViewById(R.id.menu_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        menuRecyclerView.setLayoutManager(layoutManager);
        menuRecyclerView.setAdapter(menuAdapter);
        menuRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_add_emotions:
                startActivity(new Intent(MainActivity.this, ListEmotionsItemActivity.class));
                break;
            case R.id.menu_language:
                break;
            case R.id.menu_share:
                break;
            case R.id.menu_about:
                break;
        }
        return super.onOptionsItemSelected(item);
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
                    selectedFragment = new EmotionsHistoryFragment();
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

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(this, AddEmotion.class);
                startActivity(intent);
        }

    }
}
