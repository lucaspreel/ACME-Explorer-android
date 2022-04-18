package com.example.entregable1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entregable1.Entity.Enlace;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new EnlaceAdapter(this, Enlace.listaEnlaces()));
    }
}

class EnlaceAdapter extends BaseAdapter {

    List<Enlace> enlaces;
    Context context;

    public EnlaceAdapter(Context contexto, List<Enlace> enlaceList) {
        this.context = contexto;
        this.enlaces = enlaceList;
    }

    @Override
    public int getCount() {
        return enlaces.size();
    }

    @Override
    public Object getItem(int i) {
        return enlaces.get(i);
    }

    @Override
    public long getItemId(int i) {
        return enlaces.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Enlace enlace = enlaces.get(i);

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.enlace_item, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView2);
        CardView cardView = view.findViewById(R.id.cardView);

        textView.setText(enlace.getDescripcion());
        imageView.setImageResource(enlace.getRecursoImageView());
        cardView.setOnClickListener(view1 -> {
            context.startActivity(new Intent(context, enlace.getClase()));
        });

        return view;
    }
}