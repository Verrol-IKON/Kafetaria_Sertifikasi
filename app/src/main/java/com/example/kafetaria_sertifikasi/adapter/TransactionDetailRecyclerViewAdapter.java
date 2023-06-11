package com.example.kafetaria_sertifikasi.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kafetaria_sertifikasi.R;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Menu;

import java.util.ArrayList;

public class TransactionDetailRecyclerViewAdapter extends RecyclerView.Adapter<TransactionDetailRecyclerViewAdapter.MyViewHolder> {

    ArrayList<CartItem> arr = new ArrayList<CartItem>();
    DBHelper db;

    public TransactionDetailRecyclerViewAdapter(ArrayList<CartItem> arr, DBHelper db) {
        this.arr = arr;
        this.db = db;
    }

    @NonNull
    @Override
    public TransactionDetailRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_list_layout, parent, false);
        TransactionDetailRecyclerViewAdapter.MyViewHolder myViewHolder = new TransactionDetailRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDetailRecyclerViewAdapter.MyViewHolder holder, int position) {
        Cursor test = db.getMenuItem(arr.get(position).getMenu_id());
        Menu menu = new Menu();
        while (test.moveToNext()) {
            menu.setJenis(test.getString(2));
            menu.setNama(test.getString(3));
        }
        holder.name.setText(menu.getNama());
        holder.jenis.setText(menu.getJenis());
        holder.total.setText("X" + arr.get(position).getTotal_item().toString());
        holder.harga.setText("Rp."+arr.get(position).getTotal_harga().toString());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView jenis;
        TextView harga;
        TextView total;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama);
            jenis = itemView.findViewById(R.id.jenis);
            harga = itemView.findViewById(R.id.harga);
            total = itemView.findViewById(R.id.total);
        }
    }
}
