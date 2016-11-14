package com.example.android.u_rait;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseUser;


public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignin;
    private TextView textViewSignup;
    private TextView textViewOlvidar;

    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignup = (TextView) findViewById(R.id.textViewSignUp);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        textViewOlvidar = (TextView) findViewById(R.id.textViewOlvidar);
        progressDialog = new ProgressDialog(this);
        //attaching listener to button
        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewOlvidar.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor, llene el campo de email",Toast.LENGTH_LONG).show();
            return;
        }//else{
            //checking if email is valid
           // if(!isEmailValid(email)){
            //    Toast.makeText(this,"Por favor, ingrese un correo perteneciente a la Universidad de Sonora",Toast.LENGTH_LONG).show();
            //    return;
           // }
        //}

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor, llene el campo de password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registrando...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user!=null) user.sendEmailVerification();
                            Toast.makeText(SigninActivity.this,"Para completar el registro, verifica tu correo",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }else{
                            //display some message here
                            Toast.makeText(SigninActivity.this,"Error al registrar",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignin){
            registerUser();
        }else if(view == textViewSignup){
            //open login activity when user taps on the already registered textview
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else if(view == textViewOlvidar){
            finish();
            startActivity(new Intent(this, OlvidarContrasenaActivity.class));
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
