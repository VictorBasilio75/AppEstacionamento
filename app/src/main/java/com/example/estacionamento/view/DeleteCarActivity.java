package com.example.estacionamento.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.controller.CarroController;
import com.example.estacionamento.R;

public class DeleteCarActivity extends AppCompatActivity {

    private EditText edtCarId;
    private Button btnDeleteCar;
    private CarroController carroController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);

        edtCarId = findViewById(R.id.edtCarId);
        btnDeleteCar = findViewById(R.id.btnDeleteCar);

        carroController = new CarroController(this);

        btnDeleteCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar();
            }
        });
    }

    private void deleteCar() {
        String carId = edtCarId.getText().toString().trim();

        if (carId.isEmpty()) {
            Toast.makeText(this, "Informe o ID do carro", Toast.LENGTH_SHORT).show();
            return;
        }

        carroController.excluirCarro(
                carId,
                new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            Toast.makeText(DeleteCarActivity.this, "Carro excluído!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                },
                erro -> runOnUiThread(() ->
                        Toast.makeText(DeleteCarActivity.this, "Erro ao excluir: " + erro, Toast.LENGTH_LONG).show()
                )
        );

    }
}
