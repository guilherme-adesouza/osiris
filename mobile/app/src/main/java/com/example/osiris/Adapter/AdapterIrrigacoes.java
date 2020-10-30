package com.example.osiris.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osiris.Models.Irrigacao;
import com.example.osiris.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jamiltondamasceno
 */

public class AdapterIrrigacoes extends RecyclerView.Adapter<AdapterIrrigacoes.MyViewHolder> {

    private ArrayList<Irrigacao> irrigacoes;
    private Context context;

    public AdapterIrrigacoes(ArrayList<Irrigacao> irrigacoes, Context context) {
        this.irrigacoes = irrigacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_irrigacoes, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Irrigacao irrigacao = irrigacoes.get(position);
        holder.hora.setText( irrigacao.getCron() );
        holder.descricao.setText( irrigacao.getDescription() );

    }

    @Override
    public int getItemCount() {
        return irrigacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hora;
        TextView descricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            hora = itemView.findViewById(R.id.cron);
            descricao  = itemView.findViewById(R.id.description);

        }
    }

}
