package com.example.kafetaria_sertifikasi.menu;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kafetaria_sertifikasi.MainActivity;
import com.example.kafetaria_sertifikasi.R;
import com.example.kafetaria_sertifikasi.adapter.MenuRecyclerViewAdapter;
import com.example.kafetaria_sertifikasi.db.DBHelper;
import com.example.kafetaria_sertifikasi.model.Menu;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    MaterialButtonToggleGroup toggleButton;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MenuRecyclerViewAdapter recyclerViewAdapter;

    ArrayList<Menu> arr;

    DBHelper db;

    public HomeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.menu);

        db = new DBHelper(getActivity());

        arr = new ArrayList<Menu>();

        Cursor test = db.getMenu(1);

        while (test.moveToNext()) {
            Menu menu = new Menu();
            menu.setId(Integer.valueOf(test.getString(0)));
            menu.setKafe_id(Integer.valueOf(test.getString(1)));
            menu.setJenis(test.getString(2));
            menu.setNama(test.getString(3));
            menu.setHarga(Integer.valueOf(test.getString(4)));
            arr.add(menu);
        }
        recyclerViewAdapter = new MenuRecyclerViewAdapter(arr);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);

        toggleButton = rootView.findViewById(R.id.toggleButton);
        toggleButton.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    arr.clear();
                    Cursor res = db.getMenu(1);
                    if (checkedId == R.id.tuku) {
                        res = db.getMenu(1);
                    } else if (checkedId == R.id.gotri) {
                        res = db.getMenu(2);
                    } else if (checkedId == R.id.madam) {
                        res = db.getMenu(3);
                    } else if (checkedId == R.id.kopte) {
                        res = db.getMenu(4);
                    }
                    while (res.moveToNext()) {
                        Menu menu = new Menu();
                        menu.setId(Integer.valueOf(res.getString(0)));
                        menu.setKafe_id(Integer.valueOf(res.getString(1)));
                        menu.setJenis(res.getString(2));
                        menu.setNama(res.getString(3));
                        menu.setHarga(Integer.valueOf(res.getString(4)));
                        arr.add(menu);
                    }
                    recyclerViewAdapter = new MenuRecyclerViewAdapter(arr);
                    layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setHasFixedSize(true);
                }
            }
        });
        return rootView;
    }
}