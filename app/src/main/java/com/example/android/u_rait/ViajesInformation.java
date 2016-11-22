package com.example.android.u_rait;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pat on 20/11/2016.
 */

public class ViajesInformation {
    private String nombre;
    private String ruta;
    private String hora;
    private int pasajeros;

    public ViajesInformation(){

    }

    public ViajesInformation(String nombre, String ruta, String hora, int pasajeros) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.hora = hora;
        this.pasajeros = pasajeros;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nombre", this.nombre);
        result.put("ruta", this.ruta);
        result.put("hora", this.hora);
        result.put("pasajeros", this.pasajeros);

        return result;
    }
}
