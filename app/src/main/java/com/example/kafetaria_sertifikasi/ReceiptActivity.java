package com.example.kafetaria_sertifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReceiptActivity extends AppCompatActivity {

    TextView status;
    Button home;
    String uang, total_harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        status = findViewById(R.id.status);
        home = findViewById(R.id.home);

        uang = getIntent().getStringExtra("uang");
        total_harga = getIntent().getStringExtra("total_harga");

        if (uang == total_harga) {
            status.setText("You have no change");
        } else if (Integer.parseInt(uang) > Integer.parseInt(total_harga)) {
            status.setText("Your change is Rp."+String.valueOf(Integer.parseInt(uang)-Integer.parseInt(total_harga)));
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}