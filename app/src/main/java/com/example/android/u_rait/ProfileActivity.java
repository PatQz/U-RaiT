package com.example.android.u_rait;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;

    //defining a database reference
    private DatabaseReference databaseReference;

    //our new views
    private EditText editTextName, editTextCarrera, editTextEdad;
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
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //getting the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //getting the views from xml resource
        editTextCarrera = (EditText) findViewById(R.id.editTextCarrera);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEdad = (EditText) findViewById(R.id.editTextEdad);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //displaying logged in user name
        textViewUserEmail.setText("Bienvenido "+user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == buttonSave){
            saveUserInformation();
        }
    }

    private void saveUserInformation() {
        //Getting values from database

        String name = editTextName.getText().toString().trim();
        String carrera = editTextCarrera.getText().toString().trim();
        int edad = Integer.parseInt(editTextEdad.getText().toString().trim());
        //saving data to firebase database
        /*
        * first we are creating a new child in firebase with the
        * unique id of logged in user
        * and then for that user under the unique id we are saving data
        * for saving data we are using setvalue method this method takes a normal java object
        * */
      //  String key = databaseReference.child("usuarios").push().getKey();
        //creating a userinformation object
        UserInformation userInformation = new UserInformation(name,edad,carrera);
        //getting the current logged in user
        FirebaseUser user = firebaseAuth.getCurrentUser();
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