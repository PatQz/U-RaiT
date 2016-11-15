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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //defining views
    private Button buttonSignUp;
    private TextView textViewSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewOlvidar;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().isEmailVerified() ){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), MapaActivity.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignUp = (Button) findViewById(R.id.LoginButton);
        textViewSignIn  = (TextView) findViewById(R.id.SigninTextView);
        textViewOlvidar = (TextView) findViewById(R.id.textViewOlvidar);
        progressDialog = new ProgressDialog(this);
        //attaching click listener
        textViewSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
        textViewOlvidar.setOnClickListener(this);

    }

    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor, llene el campo de email",Toast.LENGTH_LONG).show();
            return;
        }//else{
        //checking if email is valid
        //   if(!isEmailValid(email)){
        //       Toast.makeText(this,"Por favor, ingrese un correo perteneciente a la Universidad de Sonora",Toast.LENGTH_LONG).show();
        //       return;
        //   }
        //}

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor, llene el campo de password",Toast.LENGTH_LONG).show();
            return;
        }



        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Iniciando sesion...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), EleccionActivity.class));
                        }else if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"estas registrado?",Toast.LENGTH_LONG).show();
                        }else{
                            firebaseAuth.signOut();
                            Toast.makeText(LoginActivity.this,"Verifica tu correo",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        // boton de inicio de sesion
        if(view == buttonSignUp){
            userLogin();
        }else if(view == textViewSignIn){
            finish();
            startActivity(new Intent(this, SigninActivity.class));
        }else if(view == textViewOlvidar){
            finish();
            startActivity(new Intent(this, ReenvioActivity.class));
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