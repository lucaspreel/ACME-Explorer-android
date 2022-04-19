package com.example.entregable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TripDetailsActivity extends AppCompatActivity {

    TextView tvDestination, tvPrice, tvStartDate, tvEndDate, tvStartPlace;
    ImageView imageView;
    Button buttonSelect;
    Boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);


        imageView = findViewById(R.id.imageViewTripDetails);
        tvDestination = findViewById(R.id.textViewDestinationDetails);
        tvPrice = findViewById(R.id.textViewPrecio);
        tvStartDate = findViewById(R.id.textViewFechaSalida);
        tvEndDate = findViewById(R.id.textViewFechaLlegada);
        tvStartPlace = findViewById(R.id.textViewLugarSalida);
        buttonSelect = findViewById(R.id.buttonSelectTrip);

        Picasso.get()
                .load(getIntent().getStringExtra("urlImage"))
                .placeholder(android.R.drawable.ic_dialog_map)
                .error(android.R.drawable.ic_dialog_alert)
                .into(imageView);

        tvDestination.setText(getIntent().getStringExtra("destination"));
        tvPrice.setText(String.valueOf(getIntent().getIntExtra("price",0)));
        tvStartDate.setText(getIntent().getStringExtra("startDate"));
        tvEndDate.setText(getIntent().getStringExtra("endDate"));
        tvStartPlace.setText(getIntent().getStringExtra("startPlace"));

        selected = getIntent().getBooleanExtra("isSelected",false);
        buttonSelect.setEnabled(!selected);
        buttonSelect.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });


    }
}