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
import com.example.osiris.Class.ShowToast;
import com.example.osiris.Models.Equipamento;
import com.example.osiris.Models.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    //declaracao de componentes
    TextView textEmail;
    TextView textPassword;
    Button btnAccess;
    ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //referenciar componentes
        textEmail = (TextView) findViewById(R.id.text_email);
        textPassword = (TextView) findViewById(R.id.text_password);
        btnAccess = (Button) findViewById(R.id.btn_access);

        textEmail.setText("andersoncaye");
        textPassword.setText("abc123");

        //teste do get

        JSONArray usuarios = null;
        try {
            usuarios = ApiConnection.makeGet(null, ApiConnection.TABLE_USER);
            for (int i = 0; i < usuarios.length(); i++) {
                JSONObject jsonObject = usuarios.getJSONObject(i);
                Usuario usu = new Usuario();

                usu.setId(jsonObject.optString("id").toString());
                usu.setLogin(jsonObject.optString("login").toString());
                usu.setPassword(jsonObject.optString("password").toString());
                listaUsuarios.add(usu) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAccess(textEmail.getText().toString(), textPassword.getText().toString())) {
                    {
                        ShowToast.showToast(getApplicationContext(), "Logado com sucesso!", "i");
                        String token = "mytoken";
                        startActivityHome(token);
                    }
                } else {
                    ShowToast.showToast(getApplicationContext(), "Usuário ou senha incorretos!", "e");
                }
            }
        });
    }

    private boolean validateAccess(String email, String password){
        boolean access = false;

        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (email.equals(listaUsuarios.get(i).getLogin()) && password.equals(listaUsuarios.get(i).getPassword())) {
                access = true;
            }
        }
        return access;
    }

    private void startActivityHome(String token){
        Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
        homeActivity.putExtra("token", token);
        startActivity(homeActivity);
        finish();
    }

    /*
        //teste do put
        JSONObject json = new JSONObject();
        String login = "elias 2";
        String password = "elias #########";
        String mobileId = "123";
        try {
            json.put("login", login);
            json.put("password", password);
            json.put("mobileId", mobileId);
            ApiConnection.makePut(ApiConnection.TABLE_USER, "2", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //teste delete
        ApiConnection.makeDelete(ApiConnection.TABLE_USER, "4");
*/
}
