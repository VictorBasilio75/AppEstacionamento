package com.example.estacionamentocarros.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamentocarros.R;
import com.example.estacionamentocarros.controller.LoginController;

public class LoginActivity extends AppCompatActivity {

    EditText Email, Senha;
    Button BotaoEntrar;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ligando elementos do layout
        Email = findViewById(R.id.Email);
        Senha = findViewById(R.id.Senha);
        BotaoEntrar = findViewById(R.id.BotaoEntrar);

        // Instanciando o controller
        loginController = new LoginController();

        BotaoEntrar.setOnClickListener(v -> {
            String email = Email.getText().toString();
            String senha = Senha.getText().toString();

            boolean loginValido = loginController.validarLogin(email, senha);

            if (loginValido) {
                Toast.makeText(this, "E-mail e senha válidos!", Toast.LENGTH_SHORT).show();

                // Exemplo: vai pra tela principal
                /*Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("emailLogado", email);
                startActivity(intent);
                finish();*/


            } else {
                Toast.makeText(this, "E-mail ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
