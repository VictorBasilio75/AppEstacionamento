package com.example.estacionamento.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.R;
import com.example.estacionamento.controller.CarroController;
import com.example.estacionamento.controller.ErrorCallback;

public class EditCarroActivity extends AppCompatActivity {

    EditText edtCarId, edtUserId, edtModelo, edtMarca;
    Button btnEditar;
    CarroController controller;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_edit_carro);

        edtCarId = findViewById(R.id.edtCarId);
        edtUserId = findViewById(R.id.edtUserId);
        edtModelo = findViewById(R.id.edtModelo);
        edtMarca = findViewById(R.id.edtMarca);
        btnEditar = findViewById(R.id.btnEditarCarro);

        controller = new CarroController(this);

        btnEditar.setOnClickListener(v -> {
            String carId = edtCarId.getText().toString().trim();
            String userId = edtUserId.getText().toString().trim();
            String modelo = edtModelo.getText().toString().trim();
            String marca = edtMarca.getText().toString().trim();

            if (carId.isEmpty() || userId.isEmpty() || modelo.isEmpty() || marca.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.editarCarro(
                    userId,
                    carId,
                    modelo,
                    marca,
                    () -> runOnUiThread(() ->
                            Toast.makeText(EditCarroActivity.this, "Carro atualizado!", Toast.LENGTH_SHORT).show()),
                    erro -> runOnUiThread(() ->
                            Toast.makeText(EditCarroActivity.this, "Erro: " + erro, Toast.LENGTH_LONG).show())
            );
        });
    }
}
