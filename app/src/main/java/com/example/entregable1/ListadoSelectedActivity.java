package com.example.entregable1;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entregable1.Entity.Trip;
import com.example.entregable1.adapter.TripAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListadoSelectedActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Trip> selectedTripList;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_selected);

        startLocationService();
        selectedTripList = Trip.listaViajes()
                .stream()
                .filter(trip -> trip.isSelected())
                .collect(Collectors.toList());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TripAdapter(selectedTripList, location));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }

    @SuppressLint("MissingPermission")
    private void startLocationService() {
        FusedLocationProviderClient locationServices = LocationServices.getFusedLocationProviderClient(this);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(10);
        locationServices.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null || locationResult.getLastLocation() == null || !locationResult.getLastLocation().hasAccuracy()) {
                return;
            }
            location = locationResult.getLastLocation();
        }
    };
}