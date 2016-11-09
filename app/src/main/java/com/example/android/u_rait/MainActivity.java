    package com.example.android.u_rait;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;

    public class MainActivity extends AppCompatActivity implements View.OnClickListener {

        private Button buttonSignUp;
        private Button buttonLogIn;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            buttonSignUp = (Button) findViewById(R.id.SigninButton);
            buttonLogIn = (Button) findViewById(R.id.LoginButton);

            buttonSignUp.setOnClickListener(this);
            buttonLogIn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == buttonSignUp){
                startActivity(new Intent(this,SigninActivity.class));
            }

            if(view == buttonLogIn){
                startActivity(new Intent(this,LoginActivity.class));
            }
        }
    }
