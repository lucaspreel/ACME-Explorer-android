package com.example.entregable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entregable1.resttypes.WeatherResponse;
import com.example.entregable1.resttypes.WeatherRetrofitInterface;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TripDetailsActivity extends AppCompatActivity {

    TextView tvDestination, tvPrice, tvStartDate, tvEndDate, tvTemp, tvWeather;
    ImageView imageView;
    Button buttonSelect, buttonShowOnMap;
    Boolean selected;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        imageView = findViewById(R.id.imageViewTripDetails);
        tvDestination = findViewById(R.id.textViewDestinationDetails);
        tvPrice = findViewById(R.id.textViewPrecio);
        tvStartDate = findViewById(R.id.textViewFechaSalida);
        tvEndDate = findViewById(R.id.textViewFechaLlegada);
        tvTemp = findViewById(R.id.textViewTemp);
        tvWeather = findViewById(R.id.textViewWeatherDescription);
        buttonShowOnMap = findViewById(R.id.buttonShowOnMap);
        buttonSelect = findViewById(R.id.buttonSelectTrip);

        retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        Picasso.get()
                .load(getIntent().getStringExtra("urlImage"))
                .placeholder(android.R.drawable.ic_dialog_map)
                .error(android.R.drawable.ic_dialog_alert)
                .into(imageView);

        tvDestination.setText(getIntent().getStringExtra("destination"));
        tvPrice.setText(String.valueOf(getIntent().getIntExtra("price",0)));
        tvStartDate.setText(getIntent().getStringExtra("startDate"));
        tvEndDate.setText(getIntent().getStringExtra("endDate"));

        WeatherRetrofitInterface service = retrofit.create(WeatherRetrofitInterface.class);
        Call<WeatherResponse> response = service.getCurrentWeather(getIntent().getStringExtra("destination"),
                getString(R.string.open_weather_map_api_key));
        response.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvTemp.setText(String.valueOf(response.body().getMain().getTemp()));
                    tvWeather.setText(response.body().getWeather().get(0).getDescription());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.i("TecnoMovil", "Error en la llamada al API REST : " + t.getMessage());
            }
        });

        buttonShowOnMap.setOnClickListener(view -> {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        });

        selected = getIntent().getBooleanExtra("isSelected",false);
        buttonSelect.setEnabled(!selected);
        buttonSelect.setOnClickListener(view1 -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("longitude", getIntent().getStringExtra("startPlaceLong"));
            intent.putExtra("latitude", getIntent().getStringExtra("startPlaceLat"));
            startActivity(intent);
        });


    }
}