package com.example.kafetaria_sertifikasi.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kafetaria_sertifikasi.MainActivity;
import com.example.kafetaria_sertifikasi.R;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Menu;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MenuRecyclerViewAdapter extends RecyclerView.Adapter<MenuRecyclerViewAdapter.MyViewHolder> {


    ArrayList<Menu> arr = new ArrayList<Menu>();
    DBHelper db;

    public MenuRecyclerViewAdapter(ArrayList<Menu> arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public MenuRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_layout, parent, false);
        MenuRecyclerViewAdapter.MyViewHolder myViewHolder = new MenuRecyclerViewAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.name.setText(arr.get(position).getNama());
        holder.jenis.setText(arr.get(position).getJenis());
        holder.harga.setText("Rp."+arr.get(position).getHarga().toString());

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add to Cart");
                LayoutInflater inflater = LayoutInflater.from(v.getContext());
                View view = inflater.inflate(R.layout.dialog_add_to_cart, null);

                builder.setView(view);

                holder.name = view.findViewById(R.id.nama);
                holder.name.setText(arr.get(position).getNama());
                holder.jenis = view.findViewById(R.id.jenis);
                holder.jenis.setText(arr.get(position).getJenis());
                holder.harga = view.findViewById(R.id.harga);
                holder.harga.setText("Rp."+arr.get(position).getHarga().toString());
                holder.amount = view.findViewById(R.id.amount);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CartItem cartItem = new CartItem();
                        cartItem.setTotal_item(Integer.valueOf(holder.amount.getText().toString()));
                        cartItem.setMenu_id(arr.get(position).getId());
                        cartItem.setTotal_harga(cartItem.getTotal_item() * arr.get(position).getHarga());
                        db = new DBHelper(view.getContext());
                        Boolean checkInsertData = db.addToCart(cartItem);
                        if (checkInsertData == true) {
                            Toast.makeText(view.getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), "Failed adding to cart", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView jenis;
        TextView harga;
        LinearLayout menu;
        TextInputEditText amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama);
            jenis = itemView.findViewById(R.id.jenis);
            harga = itemView.findViewById(R.id.harga);

            menu = itemView.findViewById(R.id.menu);
        }
    }
}
