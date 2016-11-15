package com.example.android.u_rait;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapaActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    private Button buttonOfrecerViaje;
    private Button buttonBuscarViaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //getting current user
        user = firebaseAuth.getCurrentUser();
        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

            //finish();
            //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        buttonOfrecerViaje = (Button) findViewById(R.id.ofrecerViaje);
        buttonBuscarViaje = (Button) findViewById(R.id.buscarViaje);

        buttonBuscarViaje.setOnClickListener(this);
        buttonOfrecerViaje.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_urait, menu);
        return true;
    }

    public void onClick(View view){
        if(view == buttonOfrecerViaje){
            startActivity(new Intent(this, OfrecerViajeActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_perfil:
                //start the profile activity
                finish();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.cerrar_sesion:
                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
