package com.example.osiris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osiris.Class.ApiConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Spinner spiEquipamento;
    Button btnEntrar;
    ArrayList<String> listaEquipamentos = new ArrayList<>();
    static Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        JSONArray dispositivos = ApiConnection.makeGet(null, ApiConnection.TABLE_DEVICE);

        try {
            for (int i = 0; i < dispositivos.length(); i++) {
                JSONObject jsonObject = dispositivos.getJSONObject(i);
                String name = jsonObject.optString("displayName").toString();
                listaEquipamentos.add(name) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        spiEquipamento = findViewById(R.id.spiEquipamentos);
        btnEntrar = findViewById(R.id.btnEntrar_Equipamento);

        context = HomeActivity.this;

        try{

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaEquipamentos);
            spiEquipamento.setAdapter(adapter);

        }catch (Exception e) {
            Toast.makeText(context,  e.getMessage(), Toast.LENGTH_LONG);
        }

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainActivity = new Intent(context, MainActivity.class);
                mainActivity.putExtra("deviceId", spiEquipamento.getSelectedItem().toString());
                startActivity(mainActivity);
            }
        });
    }
}