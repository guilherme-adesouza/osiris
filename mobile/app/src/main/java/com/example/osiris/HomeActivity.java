package com.example.osiris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Models.Equipamento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Spinner spiEquipamento;
    Button btnEntrar;
    ArrayList<Equipamento> listaEquipamentos = new ArrayList<>();
    static Activity context;
    Equipamento equipamento = new Equipamento();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        JSONArray dispositivos = ApiConnection.makeGet(null, ApiConnection.TABLE_DEVICE);

        try {
            for (int i = 0; i < dispositivos.length(); i++) {
                JSONObject jsonObject = dispositivos.getJSONObject(i);
                Equipamento equip = new Equipamento();

                equip.setId(jsonObject.optString("id").toString());
                equip.setMacAdress(jsonObject.optString("macAddress").toString());
                equip.setDisplayName(jsonObject.optString("displayName").toString());
                listaEquipamentos.add(equip) ;
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

        //Função da combo Bairro
        spiEquipamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                equipamento = (Equipamento) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(context, MainActivity.class);
                mainActivity.putExtra("deviceId", equipamento.getId().toString());
                startActivity(mainActivity);
            }
        });
    }
}