package com.example.estacionamento.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estacionamento.R;
import com.example.estacionamento.model.Carro;
import com.example.estacionamento.view.CadastroCarroActivity;

import java.util.List;

public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.ViewHolder> {

    private final List<Carro> lista;
    private final Context ctx;

    public CarroAdapter(Context ctx, List<Carro> lista) {
        this.lista = lista;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public CarroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carro, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarroAdapter.ViewHolder holder, int position) {
        Carro c = lista.get(position);
        holder.txtId.setText("ID: " + (c.getId()==null?"-":c.getId()));
        holder.txtModel.setText("Modelo: " + (c.getModel()==null?"-":c.getModel()));
        holder.txtBrand.setText("Marca: " + (c.getBrand()==null?"-":c.getBrand()));

        holder.itemView.setOnClickListener(v -> {
            // abrir edição: envia id model brand userId via Intent
            Intent it = new Intent(ctx, CadastroCarroActivity.class);
            it.putExtra("carId", c.getId());
            it.putExtra("model", c.getModel());
            it.putExtra("brand", c.getBrand());
            it.putExtra("userId", c.getUserId());
            ctx.startActivity(it);
        });

        holder.itemView.setOnLongClickListener(v -> {
            Toast.makeText(ctx, "Clique longo em: " + (c.getModel()==null?"":c.getModel()), Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtModel, txtBrand;
        public ViewHolder(@NonNull View v) {
            super(v);
            txtId = v.findViewById(R.id.txtId);
            txtModel = v.findViewById(R.id.txtModel);
            txtBrand = v.findViewById(R.id.txtBrand);
        }
    }
}
