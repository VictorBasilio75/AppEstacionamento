package com.example.estacionamento.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.R;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.Email);
        edtSenha = findViewById(R.id.Senha);
        btnLogin = findViewById(R.id.BotaoEntrar);

        btnLogin.setOnClickListener(v -> {
            // mock login: accept any non-empty credentials
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }
}
