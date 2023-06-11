package com.example.kafetaria_sertifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kafetaria_sertifikasi.adapter.CartRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.adapter.MenuRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Menu;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    TextView back, item, harga;
    TextInputEditText amount;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<CartItem> arr;

    DBHelper db;

    Button checkout;
    Integer total_item, total_harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart);

        total_harga = 0;
        total_item = 0;

        arr = new ArrayList<CartItem>();
        db = new DBHelper(this);
        Cursor test = db.getCart();

        while (test.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(Integer.valueOf(test.getString(0)));
            cartItem.setMenu_id(Integer.valueOf(test.getString(1)));
            cartItem.setTotal_item(Integer.valueOf(test.getString(2)));
            cartItem.setTotal_harga(Integer.valueOf(test.getString(3)));
            arr.add(cartItem);
            total_item += Integer.valueOf(test.getString(2));
            total_harga += Integer.valueOf(test.getString(3));
        }
        recyclerViewAdapter = new CartRecyclerViewAdapter(arr, db);
        layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        checkout = findViewById(R.id.checkout);
        if (arr.size() == 0) {
            checkout.setVisibility(View.GONE);
        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Payment");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View view = inflater.inflate(R.layout.dialog_payment, null);

                builder.setView(view);

                item = view.findViewById(R.id.totalItem);
                item.setText(total_item + " Item");
                harga = view.findViewById(R.id.totalHarga);
                harga.setText("Rp." + total_harga);
                amount = view.findViewById(R.id.amount);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (Integer.valueOf(String.valueOf(amount.getText())) < total_harga) {
                            Toast.makeText(v.getContext(), "Insufficient fund", Toast.LENGTH_SHORT).show();
                        } else {
                            long checkInsertData = db.makeTransaction(total_item, total_harga);
                            Boolean checkDeleteData = db.emptyCart();
                            Boolean checkInsertItem = false;
                            for (int i = 0; i < arr.size(); i++) {
                               checkInsertItem = db.makeTransactionItem(Integer.valueOf(String.valueOf(checkInsertData)), arr.get(i).getMenu_id(), arr.get(i).getTotal_item(), arr.get(i).getTotal_harga());
                            }
                            if (checkInsertData != -1 && checkDeleteData == true && checkInsertItem == true) {
                                Toast.makeText(v.getContext(), "Successfully checkout your cart", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ReceiptActivity.class);
                                intent.putExtra("uang", amount.getText());
                                intent.putExtra("total_harga", total_harga.toString());
                                startActivity(intent);
                            } else {
                                Toast.makeText(v.getContext(), "Failed to checkout your cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.create();
                builder.show();
            }
        });

        back = findViewById(R.id.cancel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}