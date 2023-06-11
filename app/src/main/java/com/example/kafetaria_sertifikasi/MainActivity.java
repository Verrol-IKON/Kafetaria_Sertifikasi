package com.example.kafetaria_sertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.kafetaria_sertifikasi.databinding.ActivityMainBinding;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.menu.HomeFragment;
import com.example.kafetaria_sertifikasi.menu.TransactionFragment;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ImageView cartImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        Intent intent = getIntent();

        cartImg = findViewById(R.id.cart);

        binding.btmNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.transaction) {
                replaceFragment(new TransactionFragment());
            }
            return true;
        });
        cartImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}