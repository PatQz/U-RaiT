package com.example.android.u_rait;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OlvidarContrasenaActivity extends AppCompatActivity implements View.OnClickListener{

    //defining views
    private Button buttonReenvio;
    private EditText editTextEmail;
    private TextView textViewOlvidar;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidar_contrasena);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonReenvio = (Button) findViewById(R.id.ReenvioButton);
        textViewOlvidar = (TextView) findViewById(R.id.textViewOlvidar);
        progressDialog = new ProgressDialog(this);

        //attaching click listener
        buttonReenvio.setOnClickListener(this);
        textViewOlvidar.setOnClickListener(this);
    }


    //method for user login
    private void reenvio(){
        final String email = editTextEmail.getText().toString().trim();
        final String password = "password";
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor, llene el campo de email",Toast.LENGTH_LONG).show();
            return;
        }//else{
         //   if( !(isEmailValid(email) )){
         //       Toast.makeText(this,"Email no valido! Ejemplo: a212206115@alumnos.uson.mx o a212206115@alumnos.unison.mx",Toast.LENGTH_LONG).show();
        //        return;
        //    }
       // }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Reenviando correo de confirmacion...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        firebaseAuth.sendPasswordResetEmail(email);
                        firebaseAuth.signOut();
                        Toast.makeText(OlvidarContrasenaActivity.this,"Por favor, verifica tu correo",Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                });

    }

    @Override
    public void onClick(View view) {
        // boton de inicio de sesion
        if(view == buttonReenvio){
            reenvio();
        }else if(view == textViewOlvidar){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }

    public static boolean isEmailValid(String email) {
        String expressionAlumnos = "^a[0-9.%+-]{9,9}@alumnos\\.((uson)|(unison))\\.mx$";
        String expressionMestros = "^[A-Za-z0-9+_.-]+@[a-z.-]+((uson)|(unison))\\.mx$";

        if(email.matches(expressionAlumnos)){
            return true;
        }else if(email.matches(expressionMestros)){
            return true;
        }

        return false;
    }
}
