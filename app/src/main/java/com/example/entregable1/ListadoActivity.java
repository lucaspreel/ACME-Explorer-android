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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListadoActivity extends AppCompatActivity implements EventListener<QuerySnapshot> {

    static final int FILTER=1;
    RecyclerView recyclerView;
    private List<Trip> tripList;
    private ListenerRegistration listenerRegistration;
    private DataChangedListener mDataChangedListener;
    private ItemErrorListener mErrorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        this.tripList = new ArrayList<>();
        listenerRegistration = FirestoreService.getServiceInstance().getTrips(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new TripAdapter(tripList));
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

                List<Trip> filteredTripList = tripList
                        .stream()
                        .filter(trip -> trip.getPrice() > Integer.valueOf(filter_priceMin))
                        .filter(trip -> trip.getPrice() < Integer.valueOf(filter_priceMax))
                        .collect(Collectors.toList());

                recyclerView.setAdapter(new TripAdapter(filteredTripList));
                recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            }

        }
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        if (error != null) {
            mErrorListener.onItemError(error);
        }
        tripList.clear();
        if (value != null) {
            tripList.addAll(value.toObjects(Trip.class));
        }
        mDataChangedListener.onDataChanged();
        recyclerView.setAdapter(new TripAdapter(tripList));
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
    }

    public void setErrorListener (ItemErrorListener itemErrorListener) {
        mErrorListener = itemErrorListener;
    }

    public interface ItemErrorListener {
        void onItemError(FirebaseFirestoreException error);
    }

    void setDataChangedListener (DataChangedListener dataChangedListener) {
        mDataChangedListener = dataChangedListener;
    }

    public interface DataChangedListener {
        void onDataChanged();
    }
}