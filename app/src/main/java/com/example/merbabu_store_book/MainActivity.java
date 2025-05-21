package com.example.merbabu_store_book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.merbabu_store_book.ui.keranjang.KeranjangFragment;
import com.example.merbabu_store_book.ui.profile.ProfileFragment;
import com.example.merbabu_store_book.ui.produk.ProdukFragment;

import com.example.merbabu_store_book.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Default fragment saat pertama kali masuk
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, new ProdukFragment())
                .commit();

        navView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_produk) {
                selectedFragment = new ProdukFragment();
            } else if (item.getItemId() == R.id.navigation_keranjang) {
                selectedFragment = new KeranjangFragment();
            } else if (item.getItemId() == R.id.navigation_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }


    public static class LoginActivity {

    }

    public static class RegisterActivity {
    }
}