package com.example.kafetaria_sertifikasi.menu;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kafetaria_sertifikasi.R;
import com.example.kafetaria_sertifikasi.adapter.CartRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.adapter.TransactionRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.CartItem;
import com.example.kafetaria_sertifikasi.model.Transaction;

import java.util.ArrayList;

public class TransactionFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TransactionRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Transaction> arr;

    DBHelper db;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        recyclerView = rootView.findViewById(R.id.transaction);

        arr = new ArrayList<Transaction>();

        db = new DBHelper(getActivity());
        Cursor test = db.getTransactions();

        while (test.moveToNext()) {
            Transaction transaction = new Transaction();
            transaction.setId(Integer.valueOf(test.getString(0)));
            transaction.setTotal_item(Integer.valueOf(test.getString(1)));
            transaction.setTotal_harga(Integer.valueOf(test.getString(2)));
            arr.add(transaction);
        }
        recyclerViewAdapter = new TransactionRecyclerViewAdapter(arr);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }
}