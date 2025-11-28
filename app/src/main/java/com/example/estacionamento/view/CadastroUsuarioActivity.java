package com.example.estacionamento.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.R;
import com.example.estacionamento.controller.UserController;

public class CadastroUsuarioActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPassword;
    Button btnCadastrar;
    UserController controller;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_cadastro_usuario);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        controller = new UserController(this);

        btnCadastrar.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            controller.cadastrarUsuario(name, email, password,
                    () -> runOnUiThread(() -> {
                        Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show();
                        finish();
                    }),
                    (erro) -> runOnUiThread(() ->
                            Toast.makeText(this, "Erro ao cadastrar: " + erro, Toast.LENGTH_LONG).show()
                    ));
        });
    }
}
