package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.macqurarieinterns.Fragments.HomeFragment;
import com.example.macqurarieinterns.Fragments.JobsFragment;
import com.example.macqurarieinterns.Fragments.MenuFragment;
import com.example.macqurarieinterns.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottom_navigation_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation_view = findViewById(R.id.bottom_navigation_view);
        bottom_navigation_view.setOnNavigationItemSelectedListener(this);
        bottom_navigation_view.setSelectedItemId(R.id.navigation_profile);
    }

    ProfileFragment profileFragment = new ProfileFragment();
    HomeFragment homeFragment = new HomeFragment();
    JobsFragment jobsFragment = new JobsFragment();
    MenuFragment menuFragment = new MenuFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                return true;

            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.navigation_job:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, jobsFragment).commit();
                return true;

            case R.id.navigation_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, menuFragment).commit();
                return true;
        }

        return false;
    }


}