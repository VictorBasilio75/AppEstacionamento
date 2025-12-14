package com.example.estacionamento.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.R;
import com.example.estacionamento.controller.CarroController;
import com.example.estacionamento.controller.ErrorCallback;

public class CadastroCarroActivity extends AppCompatActivity {

    EditText edtModel, edtBrand, edtUserId;
    Button btnSalvar, btnEditar, btnExcluir;
    CarroController controller;
    String editCarId = null;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_cadastro_carro);

        edtUserId = findViewById(R.id.edtUserId);
        edtModel = findViewById(R.id.edtModel);
        edtBrand = findViewById(R.id.edtBrand);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        controller = new CarroController(this);

        // Se é edição
        if (getIntent() != null && getIntent().hasExtra("carId")) {
            editCarId = getIntent().getStringExtra("carId");
            edtModel.setText(getIntent().getStringExtra("model"));
            edtBrand.setText(getIntent().getStringExtra("brand"));
            edtUserId.setText(getIntent().getStringExtra("userId"));
            btnExcluir.setEnabled(true);
        } else {
            btnExcluir.setEnabled(false);
        }

        // SALVAR
        btnSalvar.setOnClickListener(v -> {
            String userId = edtUserId.getText().toString().trim();
            String model = edtModel.getText().toString().trim();
            String brand = edtBrand.getText().toString().trim();

            if (model.isEmpty() || brand.isEmpty() || userId.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.cadastrarCarro(
                    userId,
                    model,
                    brand,
                    new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CadastroCarroActivity.this, "Carro cadastrado!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                        }
                    },
                    new ErrorCallback() {
                        @Override
                        public void onError(final String erro) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CadastroCarroActivity.this, "Erro ao cadastrar: " + erro, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
            );
        });

        btnEditar.setOnClickListener(v -> {

            if (editCarId == null) {
                Toast.makeText(this, "Nenhum carro selecionado para edição!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = edtUserId.getText().toString().trim();
            String model = edtModel.getText().toString().trim();
            String brand = edtBrand.getText().toString().trim();

            if (model.isEmpty() || brand.isEmpty() || userId.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.editarCarro(
                    userId,
                    editCarId,
                    brand,
                    model,
                    () -> runOnUiThread(() -> {
                        Toast.makeText(this, "Carro atualizado!", Toast.LENGTH_SHORT).show();
                        finish();
                    }),
                    erro -> runOnUiThread(() ->
                            Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
                    )
            );
        });


        btnExcluir.setOnClickListener(v -> {

            if (editCarId == null) {
                Toast.makeText(this, "Nenhum carro selecionado para excluir!", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.excluirCarro(
                    editCarId,
                    () -> runOnUiThread(() -> {
                        Toast.makeText(this, "Carro removido!", Toast.LENGTH_SHORT).show();
                        finish();
                    }),
                    erro -> runOnUiThread(() ->
                            Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
                    )
            );
        });

    }
}
