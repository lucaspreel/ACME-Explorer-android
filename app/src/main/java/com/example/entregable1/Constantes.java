package com.example.entregable1;

import com.example.entregable1.Entity.Trip;

public final class Constantes {
    public final static Trip[] viajes = {
            new Trip("Paris", "Viaje Paris",
                    800, "25 apr 2022", "02 may 2022", "Sevilla",
                    "https://png.pngtree.com/element_pic/17/09/23/891e71ffa7e5efe9f5440513fa069add.jpg",
                    false),
            new Trip("Berlin", "Viaje Berlin",
                    730, "18 apr 2022", "24 may 2022", "Sevilla",
                    "https://png.pngtree.com/element_pic/17/04/20/e29789b631107bd82df67d3f46112f0e.jpg",
                    true),
            new Trip("Dublin", "Viaje Dublin",
                    1270, "03 june 2022", "12 june 2022", "Madrid",
                    "https://png.pngtree.com/element_pic/20/16/01/3156adb71123719.jpg",
                    false),
            new Trip("Milan", "Viaje Milan",
                    1740, "14 may 2022", "28 may 2022", "Valencia",
                    "https://png.pngtree.com/element_pic/30/03/20/1656fbd4b4641fc.jpg",
                    true),
            new Trip("Lisboa", "Viaje Lisboa",
                    540, "14 august 2022", "18 august 2022", "Sevilla",
                    "https://png.pngtree.com/element_pic/00/00/00/0056a3602a2cf41.jpg",
                    false),
            new Trip("Londres", "Viaje Londres",
                    1320, "25 july 2022", "04 august 2022", "Madrid",
                    "https://png.pngtree.com/element_our/sm/20180416/sm_5ad452dbaaf09.png",
                    false),
    };

    public final static String filtroPreferences = "Filtro", fechaInicio = "FechaInicio", fechaFin = "FechaFin";
}
