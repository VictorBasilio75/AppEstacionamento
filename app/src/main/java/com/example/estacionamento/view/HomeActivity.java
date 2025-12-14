package com.example.estacionamento.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.estacionamento.R;

public class HomeActivity extends AppCompatActivity {

    Button BtnCadastrar, BtnLista, BtnCadastrarUsuario, BtnDeleteCarro, BtnEditarCarro;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_home);

        BtnCadastrar = findViewById(R.id.BtnCadastrar);
        BtnLista = findViewById(R.id.BtnLista);
        BtnCadastrarUsuario = findViewById(R.id.BtnCadastrarUsuario);
        BtnDeleteCarro = findViewById(R.id.BtnDeleteCarro);
        BtnEditarCarro = findViewById(R.id.BtnEditarCarro);

        BtnCadastrar.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroCarroActivity.class))
        );

        BtnLista.setOnClickListener(v ->
                startActivity(new Intent(this, CarrosListActivity.class))
        );

        BtnCadastrarUsuario.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroUsuarioActivity.class))
        );

        BtnDeleteCarro.setOnClickListener(v ->
                startActivity(new Intent(this, DeleteCarActivity.class))
        );

        BtnEditarCarro.setOnClickListener(v ->
                startActivity(new Intent(this, EditCarroActivity.class))
        );

    }
}
