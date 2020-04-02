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
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MenuViewHolder.OnItemListener {

    @Inject
    MenuListPresenter menuListPresenter;

    private static long back_pressed;

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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
    };

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(this, ListEmotionsItemActivity.class);
                startActivity(intent);
            case 1:

        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Toast.makeText(getBaseContext(), getText(R.string.click_again_to_exit), Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}
