package com.example.osiris.Listagens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.osiris.Adapter.AdapterAgendamentos;
import com.example.osiris.Cadastros.CadAgendamentosActivity;
import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Class.ItemClickSupport;
import com.example.osiris.Class.RecyclerItemClickListener;
import com.example.osiris.Class.ShowToast;
import com.example.osiris.Models.Agendamento;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.osiris.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaAgendamentosActivity extends AppCompatActivity {

    FloatingActionButton fabCadAgendamento;
    RecyclerView revListaAgendamentos;
    ArrayList<Agendamento> listaAgendamentos = new ArrayList<>();
    Context context;
    Boolean aux = true;
    String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_agendamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        deviceId = intent.getStringExtra("deviceId");

        context = ListaAgendamentosActivity.this;

        revListaAgendamentos = findViewById(R.id.revListaPesagens);

        fabCadAgendamento = findViewById(R.id.fabListaAgendamentos);
        fabCadAgendamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent(context, CadAgendamentosActivity.class);
                cadastro.putExtra("deviceId", deviceId);
                startActivity(cadastro);
            }
        });

        revListaAgendamentos.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        System.out.println("The RecyclerView is not scrolling");
                        fabCadAgendamento.show();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        System.out.println("Scrolling now");
                        fabCadAgendamento.hide();
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        System.out.println("Scroll Settling");
                        fabCadAgendamento.hide();
                        break;
                }
            }
        });

//        ItemClickSupport.addTo(revListaAgendamentos)
//                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        if (aux) {
//                            Intent cadastro = new Intent(getApplicationContext(), CadAgendamentosActivity.class);
//                            cadastro.putExtra("id", listaAgendamentos.get(position).getId());
//                            cadastro.putExtra("deviceId", deviceId);
//                            startActivity(cadastro);
//                        }
//                        aux = true;
//                    }
//
//                    @Override
//                    public void onItemDoubleClicked(RecyclerView recyclerView, int position, View v) {
//                        Toast.makeText(context, "Você clicou duas vezes", Toast.LENGTH_SHORT).show();
//                    }
//                });

        //Adiciona evento de clique no recyclerview
        revListaAgendamentos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        revListaAgendamentos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent cadastro = new Intent(getApplicationContext(), CadAgendamentosActivity.class);
                                cadastro.putExtra("id", listaAgendamentos.get(position).getId());
                                cadastro.putExtra("deviceId", deviceId);
                                startActivity(cadastro);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                ApiConnection.makeDelete(ApiConnection.TABLE_SCHEDULE, listaAgendamentos.get(position).getId());
                                ShowToast.showToast(context, "Agendamento das " + listaAgendamentos.get(position).getCron() + " excluído com sucesso!","i");
                                //aux = false;
                                atualizarAgendamentos();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            }
                        }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizarAgendamentos();
    }

    private void atualizarAgendamentos(){
        try{

            listaAgendamentos.clear();
            JSONArray agendamentos = ApiConnection.makeGet(null, ApiConnection.TABLE_SCHEDULE);
            try {
                for (int i = 0; i < agendamentos.length(); i++) {
                    JSONObject jsonObject = agendamentos.getJSONObject(i);
                    Agendamento agendamento = new Agendamento();

                    agendamento.setId(jsonObject.optString("id").toString());
                    agendamento.setCron(jsonObject.optString("cron").toString());
                    agendamento.setDescription(jsonObject.optString("description").toString());
                    listaAgendamentos.add(agendamento);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            AdapterAgendamentos adapter = new AdapterAgendamentos(listaAgendamentos, context);
            revListaAgendamentos.setAdapter(adapter);
            revListaAgendamentos.setHasFixedSize(true);
            revListaAgendamentos.setLayoutManager(new LinearLayoutManager(context));
            revListaAgendamentos.setItemAnimator(new DefaultItemAnimator());


        }catch (Exception ex){
            ShowToast.showToast(context, "Erro ao buscar agendamentos", "e");
        }
    }
}