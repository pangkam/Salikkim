package com.salikkim.bazar.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.salikkim.bazar.Fragments.AccountFragment;
import com.salikkim.bazar.Fragments.CategoryFragment;
import com.salikkim.bazar.Fragments.HomeFragment;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private ActivityMainBinding mainBinding;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
        mainBinding.toolbarMainActivity.setTitle("Home");
        setSupportActionBar(mainBinding.toolbarMainActivity);
        mainBinding.bottomNavigation.setOnItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_main, new HomeFragment())
                .commitNow();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.home:
                mainBinding.toolbarMainActivity.setTitle("Home");
                selectedFragment = new HomeFragment();
                flag = true;
                break;
            case R.id.category:
                mainBinding.toolbarMainActivity.setTitle("Category");
                selectedFragment = new CategoryFragment();
                flag = false;
                break;
            case R.id.account:
                mainBinding.toolbarMainActivity.setTitle("Account");
                selectedFragment = new AccountFragment();
                flag = false;
                break;
        }
        return loadFragment(selectedFragment);
    }

    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_main, selectedFragment)
                    .commitNow();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class)
                        .putExtra("query", s));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                break;
            case R.id.cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (flag) {
            finish();
        } else {
            mainBinding.bottomNavigation.setSelectedItemId(R.id.home);
        }
    }
}