package com.example.kafetaria_sertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kafetaria_sertifikasi.adapter.CartRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.adapter.TransactionDetailRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;

import java.util.ArrayList;

public class TransactionDetailActivity extends AppCompatActivity {

    Integer id;
    TextView back;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TransactionDetailRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<CartItem> arr;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        recyclerView = findViewById(R.id.transactionDet);

        arr = new ArrayList<CartItem>();

        db = new DBHelper(this);

        id = Integer.valueOf(getIntent().getStringExtra("id"));

        Cursor test = db.getTransactionItems(7);

        while (test.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(Integer.valueOf(test.getString(0)));
            cartItem.setTransaction_id(Integer.valueOf(test.getString(1)));
            cartItem.setMenu_id(Integer.valueOf(test.getString(2)));
            cartItem.setTotal_item(Integer.valueOf(test.getString(3)));
            cartItem.setTotal_harga(Integer.valueOf(test.getString(4)));
            arr.add(cartItem);
        }
        recyclerViewAdapter = new TransactionDetailRecyclerViewAdapter(arr, db);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        back = findViewById(R.id.cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}