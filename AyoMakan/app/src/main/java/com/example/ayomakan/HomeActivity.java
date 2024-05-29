package com.example.ayomakan;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.ayomakan.fragment.FavoriteFragment;
import com.example.ayomakan.fragment.HomeFragment;
import com.example.ayomakan.fragment.ProfileFragment;
import com.example.ayomakan.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        Fragment fragment = fragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (!(fragment instanceof HomeFragment)) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, homeFragment)
                    .commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.navbarmenu);
        bottomNav.inflateMenu(R.menu.bottom_nav_menu);
        selectedFragment = new HomeFragment();
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homebtn) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.searchbtn) {
                selectedFragment = new SearchFragment();
            } else if (item.getItemId() == R.id.favbtn) {
                selectedFragment = new FavoriteFragment();
            } else if (item.getItemId() == R.id.profilebtn) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, selectedFragment)
                        .commit();
                return true;
            } else {
                return false;
            }
        });
    }
}