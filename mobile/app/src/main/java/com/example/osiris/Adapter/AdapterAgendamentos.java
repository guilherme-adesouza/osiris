package com.example.osiris.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.osiris.Models.Agendamento;
import com.example.osiris.R;

import java.util.ArrayList;

/**
 * Created by jamiltondamasceno
 */

public class AdapterAgendamentos extends RecyclerView.Adapter<AdapterAgendamentos.MyViewHolder> {

    private ArrayList<Agendamento> agendamentos;
    private Context context;

    public AdapterAgendamentos(ArrayList<Agendamento> dados, Context context) {
        this.agendamentos = dados;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_agendamentos, parent, false);
        return new MyViewHolder( item );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Agendamento agendamento = agendamentos.get(position);
        holder.hora.setText(agendamento.getCron());
        holder.descricao.setText(agendamento.getDescription());

    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView hora;
        TextView descricao;

        public MyViewHolder(View itemView) {
            super(itemView);

            hora = itemView.findViewById(R.id.txtCron);
            descricao  = itemView.findViewById(R.id.txtDescription);

        }
    }

}
