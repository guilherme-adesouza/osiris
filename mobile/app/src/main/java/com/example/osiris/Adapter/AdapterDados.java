package com.example.osiris.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osiris.Models.Dado;
import com.example.osiris.R;

import java.util.ArrayList;

/**
 * Created by jamiltondamasceno
 */

public class AdapterDados extends RecyclerView.Adapter<AdapterDados.MyViewHolder> {

    private ArrayList<Dado> dados;
    private Context context;

    public AdapterDados(ArrayList<Dado> dados, Context context) {
        this.dados = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dados, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Dado dado = dados.get(position);
        holder.luminosidade.setText("Luminosidade: " + dado.getLuminosity() );
        holder.humidade.setText("Humidade: " + dado.getHumidity() );

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView luminosidade;
        TextView humidade;

        public MyViewHolder(View itemView) {
            super(itemView);

            luminosidade = itemView.findViewById(R.id.txtLuminosity);
            humidade  = itemView.findViewById(R.id.txtHumidity);

        }
    }

}
