package com.example.kafetaria_sertifikasi.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kafetaria_sertifikasi.R;
import com.example.kafetaria_sertifikasi.ReceiptActivity;
import com.example.kafetaria_sertifikasi.TransactionDetailActivity;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Menu;
import com.example.kafetaria_sertifikasi.model.Transaction;

import java.util.ArrayList;

public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.MyViewHolder> {

    ArrayList<Transaction> arr = new ArrayList<Transaction>();
    DBHelper db;

    public TransactionRecyclerViewAdapter(ArrayList<Transaction> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public TransactionRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_layout, parent, false);
        TransactionRecyclerViewAdapter.MyViewHolder myViewHolder = new TransactionRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.transaction.setText("Transaction "+(position+1));
        holder.total.setText(arr.get(position).getTotal_item().toString()+" Item");
        holder.harga.setText("Rp."+arr.get(position).getTotal_harga().toString());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), TransactionDetailActivity.class);
                intent.putExtra("id", arr.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView transaction, total, harga;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction = itemView.findViewById(R.id.transaction);
            harga = itemView.findViewById(R.id.harga);
            total = itemView.findViewById(R.id.total);

            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
