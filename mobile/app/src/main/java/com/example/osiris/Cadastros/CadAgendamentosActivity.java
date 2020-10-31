package com.example.osiris.Cadastros;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Class.ShowToast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osiris.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CadAgendamentosActivity extends AppCompatActivity {

    private EditText cadCron, cadDescription;
    private Button btnAgendar;
    private JSONObject jsonSchedule;
    private JSONObject jsonDevice = new JSONObject();
    private String deviceId = "";
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agendamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();

        //recebendo o deviceId para cadastro e o id para alteração.
        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");
        id = intent.getStringExtra("id");


        try {
            //se possuí id, então temos que buscar os dados e considerar como alteração
            if (id != null) {
                String[] string = new String[1];
                string[0] = id;
                JSONObject json = ApiConnection.makeGetId(string, ApiConnection.TABLE_SCHEDULE);

                if (json != null) {
                    cadCron.setText(json.optString("cron"));
                    cadDescription.setText(json.optString("description"));
                    btnAgendar.setText("ALTERAR");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //possuindo id, é uma alteração
                    if (id != null) {
                        jsonDevice.put("id", deviceId);
                        jsonSchedule = new JSONObject();
                        jsonSchedule.put("device", jsonDevice);
                        jsonSchedule.put("cron", cadCron.getText().toString());
                        jsonSchedule.put("description", cadDescription.getText().toString());

                        JSONObject jsonRetorno = ApiConnection.makePut(ApiConnection.TABLE_SCHEDULE, id, jsonSchedule);
                        if (jsonRetorno == null) {
                            //Toast toast = Toast.makeText(getApplicationContext(), "Você clicou duas vezes", Toast.LENGTH_SHORT);
                            ShowToast.showToast(getApplicationContext(), "Agendamento alterado com sucesso", "s");
                            finish();
                        }
                    } else {
                        //sem id é uma inclusão
                        jsonDevice.put("id", deviceId);
                        jsonSchedule = new JSONObject();
                        jsonSchedule.put("device", jsonDevice);
                        jsonSchedule.put("cron", cadCron.getText().toString());
                        jsonSchedule.put("description", cadDescription.getText().toString());

                        JSONObject jsonRetorno = ApiConnection.makePost(ApiConnection.TABLE_SCHEDULE, jsonSchedule);
                        if (jsonRetorno == null) {
                            //Toast toast = Toast.makeText(getApplicationContext(), "Você clicou duas vezes", Toast.LENGTH_SHORT);
                            ShowToast.showToast(getApplicationContext(), "Agendamento incluído com sucesso", "s");
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void inicializarComponentes(){

        cadCron = findViewById(R.id.txtCadCron);
        cadDescription = findViewById(R.id.txtCadDescription);
        btnAgendar = findViewById(R.id.btnAgendar);

    }
}