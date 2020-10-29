package com.example.osiris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    Spinner spiEquipamento;
    Button btnEntrar;
    //ArrayList<Equipamentos> listaEquipamentos;
    static Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spiEquipamento = findViewById(R.id.spiEquipamentos);
        btnEntrar = findViewById(R.id.btnEntrar_Equipamento);

        context = HomeActivity.this;

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainActivity = new Intent(context, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }
}