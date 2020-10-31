package com.example.osiris.Cadastros;

import android.os.Bundle;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.osiris.Class.ApiConnection;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.osiris.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CadAgendamentosActivity extends AppCompatActivity {

    private EditText cadCron, cadDescription;
    private Button btnAgendar;
    private JSONObject jsonSchedule;
    private JSONObject jsonDevice = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_agendamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();

        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    jsonDevice.put("id", 1);
                    jsonSchedule = new JSONObject();
                    jsonSchedule.put("device", jsonDevice);
                    jsonSchedule.put("cron", cadCron.getText().toString());
                    jsonSchedule.put("description", cadDescription.getText().toString());

                    ApiConnection.makePost(ApiConnection.TABLE_SCHEDULE, null, jsonSchedule);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                {
//                    "device": {
//                    "id": 1
//                },
//                    "cron": "TIME_20:00",
//                        "description": "Regar às 8 da tarde"
//                }
            }
        });


    }

    private void inicializarComponentes(){

        cadCron = findViewById(R.id.txtCadCron);
        cadDescription = findViewById(R.id.txtCadDescription);
        btnAgendar = findViewById(R.id.btnAgendar);

    }
}