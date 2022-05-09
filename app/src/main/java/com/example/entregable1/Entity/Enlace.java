package com.example.entregable1.Entity;

import com.example.entregable1.FirebaseStor;
import com.example.entregable1.ListadoActivity;
import com.example.entregable1.ListadoSelectedActivity;
import com.example.entregable1.MainActivity;
import com.example.entregable1.R;

import java.util.ArrayList;
import java.util.List;

public class Enlace {
    private String descripcion;
    private Class clase;
    private int recursoImageView;

    public Enlace(String descripcion, Class clase, int recursoImageView) {
        this.descripcion = descripcion;
        this.clase = clase;
        this.recursoImageView = recursoImageView;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Class getClase() {
        return clase;
    }

    public void setClase(Class clase) {
        this.clase = clase;
    }

    public int getRecursoImageView() {
        return recursoImageView;
    }

    public void setRecursoImageView(int recursoImageView) {
        this.recursoImageView = recursoImageView;
    }

    public static List<Enlace> listaEnlaces() {
        List<Enlace> list = new ArrayList<>();
        list.add(new Enlace("Viajes disponibles", ListadoActivity.class, R.drawable.viajar));
        list.add(new Enlace("Viajes seleccionados", ListadoSelectedActivity.class, R.drawable.objetivo));
        list.add(new Enlace("Perfil", FirebaseStor.class, R.drawable.profile));
        return list;
    }
}
