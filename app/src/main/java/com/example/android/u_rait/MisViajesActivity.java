package com.example.android.u_rait;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MisViajesActivity extends AppCompatActivity implements View.OnClickListener{
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private static FirebaseUser user;
    private TextView rutaTextView;
    private TextView horaTextView;
    private TextView pasajerosTextView;
    private Button cancelarButton;
    static DatabaseReference datosUsuario;


    //defining a database reference
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_viajes);

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

        rutaTextView = (TextView) findViewById(R.id.textViewRuta2);
        horaTextView = (TextView) findViewById(R.id.textViewHora2);
        pasajerosTextView = (TextView) findViewById(R.id.textViewPasajeros2);
        cancelarButton = (Button) findViewById(R.id.cancelarButton);

        cancelarButton.setOnClickListener(this);
        this.cargarDatos();
    }

    private void cargarDatos(){
        datosUsuario = FirebaseDatabase.getInstance().getReference().child("viajesO").child(user.getUid());

        if(datosUsuario != null) {
            datosUsuario.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // actualizar datos

                            if (dataSnapshot.child("nombre").getValue() != null) {
                                rutaTextView.setText(dataSnapshot.child("ruta").getValue().toString());
                                horaTextView.setText(dataSnapshot.child("hora").getValue().toString());
                                pasajerosTextView.setText(dataSnapshot.child("pasajeros").getValue().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                         }
            });
        }
    }
    private void cancelarViaje(){
        //getting the current logged in user
        user = firebaseAuth.getCurrentUser();
        Map<String, Object> childUpdates = new HashMap<>();
        // guardas la informacion del usuario en la ruta
        childUpdates.put("/viajesO/"+user.getUid()+"/",null);
        //childUpdates.remove("/viajesO/"+user.getUid()+"/");
        databaseReference.updateChildren(childUpdates);
    }
    @Override
    public void onClick(View v) {
        if(v == cancelarButton){
            cancelarViaje();
            startActivity(new Intent(this,EleccionActivity.class));
        }
    }

    /*public static boolean viajeActivoExists(){
        datosUsuario = FirebaseDatabase.getInstance().getReference().child("viajesO").child(user.getUid());
        if(datosUsuario != null) {
            return true;
        }
        return false;
    }*/
}
