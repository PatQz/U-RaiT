package com.example.android.u_rait;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;



public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    //view objects
    private TextView textViewUserEmail;
    private Spinner carrerasSpinner;
    //defining a database reference
    private DatabaseReference databaseReference;

    //our new views
    private EditText editTextName, editTextEdad;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
        //getting the views from xml resource
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEdad = (EditText) findViewById(R.id.editTextEdad);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        carrerasSpinner = (Spinner) findViewById(R.id.carreras);
        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        //displaying logged in user name
        textViewUserEmail.setText("Bienvenido "+user.getEmail());
        //adding listener to button
        buttonSave.setOnClickListener(this);
        this.cargarDatos();
    }

    private void cargarDatos(){
        DatabaseReference datosUsuario;
        datosUsuario = FirebaseDatabase.getInstance().getReference().child("usuarios").child(user.getUid());

        if(datosUsuario != null) {
            datosUsuario.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // actualizar datos

                            if (dataSnapshot.child("nombre").getValue() != null) {
                                textViewUserEmail.setText("Bienvenido " + dataSnapshot.child("nombre").getValue().toString());
                                editTextName.setText(dataSnapshot.child("nombre").getValue().toString());
                                editTextEdad.setText(dataSnapshot.child("edad").getValue().toString());
                                carrerasSpinner.setSelection( Integer.parseInt(dataSnapshot.child("carreraId").getValue().toString() ));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

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

    @Override
    public void onClick(View view) {

        if(view == buttonSave){
            saveUserInformation();
        }
    }

    private void saveUserInformation() {
        //Getting values from database

        String name = editTextName.getText().toString().trim();
        String carrera = carrerasSpinner.getSelectedItem().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString().trim());
        int carreraId = carrerasSpinner.getSelectedItemPosition();
        //saving data to firebase database
        /*
        * first we are creating a new child in firebase with the
        * unique id of logged in user
        * and then for that user under the unique id we are saving data
        * for saving data we are using setvalue method this method takes a normal java object
        * */
      //  String key = databaseReference.child("usuarios").push().getKey();
        //creating a userinformation object
        UserInformation userInformation = new UserInformation(name,edad,carrera,carreraId);
        //getting the current logged in user
        user = firebaseAuth.getCurrentUser();
        Map<String, Object> postValues = userInformation.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        // guardas la informacion del usuario en la ruta
        childUpdates.put("/usuarios/"+user.getUid()+"/", postValues);
        // actualizas
        databaseReference.updateChildren(childUpdates);

        Toast.makeText(this, "Informacion salvada...", Toast.LENGTH_LONG).show();
        //displaying a success toast
    }
}