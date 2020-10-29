package com.example.osiris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.StrictMode;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.osiris.Class.ApiConnection;

public class LoginActivity extends AppCompatActivity {

    //declaracao de componentes
    TextView textEmail;
    TextView textPassword;
    Button btnAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ApiConnection.makeGet(null, ApiConnection.TABLE_USER);

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
