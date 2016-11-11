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
    //private String uid;

    public UserInformation(){

    }

    public UserInformation(String name, String carrera) {
        //this.uid = uid;
        this.name = name;
        this.carrera = carrera;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("uid", this.name);
        result.put("nombre", this.name);
        result.put("carrera", this.carrera);

        return result;
    }
}