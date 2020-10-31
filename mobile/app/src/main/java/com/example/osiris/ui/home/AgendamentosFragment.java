package com.example.osiris.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osiris.Adapter.AdapterAgendamentos;
import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Models.Agendamento;
import com.example.osiris.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class AgendamentosFragment extends Fragment {

    private AgendmaneotsViewModel agendamentosViewModel;
    private RecyclerView recyclerAgendamentos;
    private AdapterAgendamentos adapterAgendamentos;
    private ArrayList<Agendamento> listaAgendamentos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        agendamentosViewModel = ViewModelProviders.of(this).get(AgendmaneotsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agendamentos, container, false);
        recyclerAgendamentos = root.findViewById(R.id.recyclerAgendamentos);


        try {
            recyclerAgendamentos.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerAgendamentos.setHasFixedSize(true);
            adapterAgendamentos = new AdapterAgendamentos(listaAgendamentos, getActivity());
            recyclerAgendamentos.setAdapter(adapterAgendamentos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray agendamentos = ApiConnection.makeGet(null, ApiConnection.TABLE_SCHEDULE);
        listaAgendamentos.clear();

        try {
            for (int i = 0; i < agendamentos.length(); i++) {
                JSONObject jsonObject = agendamentos.getJSONObject(i);
                Agendamento agendamento = new Agendamento();

                agendamento.setCron(jsonObject.optString("cron").toString());
                agendamento.setDescription(jsonObject.optString("description").toString());
                listaAgendamentos.add(agendamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse( listaAgendamentos );
        adapterAgendamentos.notifyDataSetChanged();


        //final TextView textView = root.findViewById(R.id.text_home);

        agendamentosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}