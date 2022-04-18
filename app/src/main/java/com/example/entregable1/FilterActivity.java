package com.example.entregable1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {

    EditText precioMinimo, precioMaximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        precioMinimo = findViewById(R.id.editTextPrecioMinimo);
        precioMaximo = findViewById(R.id.editTextPrecioMaximo);
    }

    public void aplicar(View view) {
        Intent intent = new Intent();
        intent.putExtra("filter_price_min", precioMinimo.getText().toString());
        intent.putExtra("filter_price_max", precioMaximo.getText().toString());

        setResult(RESULT_OK, intent);

        finish();
    }
}