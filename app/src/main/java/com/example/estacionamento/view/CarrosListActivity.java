package com.example.estacionamento.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estacionamento.R;
import com.example.estacionamento.adapter.CarroAdapter;
import com.example.estacionamento.controller.CarroController;

import java.util.List;

public class CarrosListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CarroController controller;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_lista_carros);

        recyclerView = findViewById(R.id.recyclerCarros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        controller = new CarroController(this);

        controller.getCarros(new CarroController.CarrosCallback() {
            @Override
            public void onSuccess(List<com.example.estacionamento.model.Carro> lista) {
                recyclerView.setAdapter(new CarroAdapter(CarrosListActivity.this, lista));
            }

            @Override
            public void onError(String erro) {
                Toast.makeText(CarrosListActivity.this, "Erro ao carregar: " + erro, Toast.LENGTH_LONG).show();
            }
        });
    }
}
