package com.example.android.u_rait;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfrecerViajeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button tiempo;
    private Button aceptar;
    Calendar mcurrentTime = Calendar.getInstance();
    private int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
    private int minute = mcurrentTime.get(Calendar.MINUTE);
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    //firebase database object
    private DatabaseReference databaseReference;
    EditText rutaViaje;
    Spinner numPasajeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofrecer_viaje);
        //Firebase.setAndroidContext(this);
        //mRef = new Firebase("https://u-rait.firebaseio.com/viajesO");

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            //finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        //getting current user
        user = firebaseAuth.getCurrentUser();
        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tiempo = (Button) findViewById(R.id.ButtonTime);
        aceptar = (Button) findViewById(R.id.ButtonAceptar);
        rutaViaje = (EditText)findViewById(R.id.editTextRuta);
        numPasajeros = (Spinner) findViewById(R.id.SpinnerPasajeros);

        // Spinner Drop down elements
        List<String> pasajeros_array= new ArrayList<>();
        pasajeros_array.add("1");
        pasajeros_array.add("2");
        pasajeros_array.add("3");
        pasajeros_array.add("4");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pasajeros_array);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        numPasajeros.setAdapter(dataAdapter);
        numPasajeros.setSelection(0);

        setTiempo(hour, minute);
        tiempo.setOnClickListener(this);
        aceptar.setOnClickListener(this);
        numPasajeros.setOnItemSelectedListener(this);
    }

    private void setTiempo(int selectedHour,int selectedMinute){
        String hourScheledule = "AM";
        String minutes;
        if(selectedHour >12){
            selectedHour= selectedHour %12;
            hourScheledule = "PM";
        }
        if(selectedHour == 0) selectedHour = 12;

        if(selectedMinute<10){
            minutes = "0" + selectedMinute;
        }else{
            minutes = String.valueOf(selectedMinute);
        }
        tiempo.setText( selectedHour + ":" + minutes + " " + hourScheledule);
    }

    @Override
    public void onClick(View view) {
        if(view == tiempo){
            setTiempo();
        }
        if(view==aceptar){
            saveViaje();
            startActivity(new Intent(this, EleccionActivity.class));
        }
    }

    private void setTiempo(){
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                setTiempo(selectedHour,selectedMinute);
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void saveViaje() {
        String nombre = "";//datosUsuario();
        String ruta = rutaViaje.getText().toString().trim();
        String hora = tiempo.getText().toString();
        int pasajeros = numPasajeros.getSelectedItemPosition();

        //creating a ViajesInformation object
        ViajesInformation viajesInformation = new ViajesInformation(nombre,ruta,hora,pasajeros);
        //getting the current logged in user
        user = firebaseAuth.getCurrentUser();
        Map<String, Object> postValues = viajesInformation.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        // guardas la informacion del usuario en la ruta
        childUpdates.put("/viajesO/"+user.getUid()+"/",postValues);
        // actualizas
        databaseReference.updateChildren(childUpdates);

        Toast.makeText(this, "Informacion salvada...", Toast.LENGTH_LONG).show();
        //displaying a success toast
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        //numPasajeros.setSelection(Integer);
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
