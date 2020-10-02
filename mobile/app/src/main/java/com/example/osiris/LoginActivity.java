package com.example.osiris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.osiris.Class.Embedded;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //declaracao de componentes
    TextView textEmail;
    TextView textPassword;
    Button btnAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //referenciar componentes
        textEmail = (TextView) findViewById(R.id.text_email);
        textPassword = (TextView) findViewById(R.id.text_password);
        btnAccess = (Button) findViewById(R.id.btn_access);

        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(
                        validateAccess(
                                textEmail.getText().toString(),
                                textPassword.getText().toString()
                        )
                ){
                    String token = "mytoken";
                    startActivityHome(token);
                }
            }
        });
    }

    private boolean validateAccess(String email, String password){
        boolean access = false;

        if ( true ) {
            access = true;
        }
        //Log.e("RETORNO DO VALIDDATE", " ############################################ " + access);
        return access;
    }

    private void startActivityHome(String token){
        Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
        homeActivity.putExtra("token", token);
        startActivity(homeActivity);
        finish();
    }
}
