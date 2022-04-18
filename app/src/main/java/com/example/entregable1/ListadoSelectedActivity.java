package com.example.entregable1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregable1.Entity.Trip;
import com.example.entregable1.adapter.TripAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListadoSelectedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Trip> selectedTripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_selected);

        selectedTripList = Trip.listaViajes()
                .stream()
                .filter(trip -> trip.isSelected())
                .collect(Collectors.toList());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TripAdapter(selectedTripList));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }
}