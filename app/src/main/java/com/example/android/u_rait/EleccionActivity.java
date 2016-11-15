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


public class EleccionActivity extends AppCompatActivity implements View.OnClickListener{

    private Button crearViajeButton;
    private Button buscarViajeButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            //finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        crearViajeButton = (Button) findViewById(R.id.CrearViajeButton);
        buscarViajeButton = (Button) findViewById(R.id.BuscarViajeButton);

        crearViajeButton.setOnClickListener(this);
        buscarViajeButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_urait, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mi_perfil:
                //start the profile activity
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

    public void onClick(View view) {
        // boton de inicio de sesion
        if(view == crearViajeButton){
            startActivity(new Intent(this, MapaActivity.class));
        }else if(view == buscarViajeButton){
            startActivity(new Intent(this, MapaActivity.class));
        }
    }
}
