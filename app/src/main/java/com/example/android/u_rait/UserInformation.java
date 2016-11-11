package com.example.android.u_rait;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.author;

/**
 * Created by Pat on 09/11/2016.
 */

public class UserInformation {

    private String name;
    private String carrera;
    private int edad;

    public UserInformation(){

    }

    public UserInformation(String name,int edad ,String carrera) {
        this.name = name;
        this.edad = edad;
        this.carrera = carrera;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("edad", this.edad);
        result.put("nombre", this.name);
        result.put("carrera", this.carrera);

        return result;
    }
}