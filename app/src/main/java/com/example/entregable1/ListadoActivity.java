package com.example.entregable1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.entregable1.Entity.Trip;
import com.example.entregable1.adapter.TripAdapter;

import java.util.List;
import java.util.stream.Collectors;

public class ListadoActivity extends AppCompatActivity {

    static final int FILTER=1;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TripAdapter(Trip.listaViajes()));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }

    public void filtro(View view) {
        Intent intent =new Intent(this,FilterActivity.class);
        startActivityForResult(intent,FILTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILTER) {
            if (resultCode == RESULT_OK) {
                String filter_priceMin = data.getStringExtra("filter_price_min");
                String filter_priceMax = data.getStringExtra("filter_price_max");

                List<Trip> filteredTripList = Trip.listaViajes()
                        .stream()
                        .filter(trip -> trip.getPrice() > Integer.valueOf(filter_priceMin))
                        .filter(trip -> trip.getPrice() < Integer.valueOf(filter_priceMax))
                        .collect(Collectors.toList());

                recyclerView.setAdapter(new TripAdapter(filteredTripList));
                recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            }

        }
    }
}