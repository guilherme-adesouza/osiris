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

import com.example.osiris.Adapter.AdapterIrrigacoes;
import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Models.Irrigacao;
import com.example.osiris.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class IrrigacoesFragment extends Fragment {

    private IrrigacoesViewModel homeViewModel;
    private RecyclerView recyclerIrrigacoes;
    private AdapterIrrigacoes adapterAnuncios;
    private ArrayList<Irrigacao> listaAnuncios = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = ViewModelProviders.of(this).get(IrrigacoesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_irrigacoes, container, false);
        recyclerIrrigacoes = root.findViewById(R.id.recyclerIrrigacoes);


        try {
            recyclerIrrigacoes.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerIrrigacoes.setHasFixedSize(true);
            adapterAnuncios = new AdapterIrrigacoes(listaAnuncios, getActivity());
            recyclerIrrigacoes.setAdapter( adapterAnuncios );
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray irrigacoes = ApiConnection.makeGet(null, ApiConnection.TABLE_SCHEDULE);
        listaAnuncios.clear();

        try {
            for (int i = 0; i < irrigacoes.length(); i++) {
                JSONObject jsonObject = irrigacoes.getJSONObject(i);
                Irrigacao irrigacao = new Irrigacao();
                irrigacao.setCron(jsonObject.optString("cron").toString());
                irrigacao.setDescription(jsonObject.optString("description").toString());
                listaAnuncios.add( irrigacao );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.reverse( listaAnuncios );
        adapterAnuncios.notifyDataSetChanged();


        //final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
}