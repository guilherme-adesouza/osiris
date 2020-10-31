package com.example.osiris.ui.dados;

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

import com.example.osiris.Adapter.AdapterDados;
import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Models.Dado;
import com.example.osiris.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class DadosFragment extends Fragment {

    private DadosViewModel dadosViewModel;
    private RecyclerView recyclerDados;
    private AdapterDados adapterDados;
    private ArrayList<Dado> listaDados = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inicializa componentes básicos
        dadosViewModel = ViewModelProviders.of(this).get(DadosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dados, container, false);
        recyclerDados = root.findViewById(R.id.recyclerDados);

        //inicializa e vincula os componentes do recycler e adapater dos dados
        try {
            recyclerDados.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerDados.setHasFixedSize(true);
            adapterDados = new AdapterDados(listaDados, getActivity());
            recyclerDados.setAdapter(adapterDados);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //consulta dos agendamentos cadastrados e adição a lista
        JSONArray dados = ApiConnection.makeGet(null, ApiConnection.TABLE_DATA);
        listaDados.clear();
        try {
            for (int i = 0; i < dados.length(); i++) {
                JSONObject jsonObject = dados.getJSONObject(i);
                Dado dado = new Dado();

                dado.setLuminosity(jsonObject.optString("luminosity").toString());
                dado.setHumidity(jsonObject.optString("humidity").toString());
                listaDados.add(dado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //notifica a mudança para carregar novamente o addapter
        Collections.reverse(listaDados);
        adapterDados.notifyDataSetChanged();

        dadosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }
}