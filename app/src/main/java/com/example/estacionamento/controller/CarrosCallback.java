package com.example.estacionamento.controller;

import com.example.estacionamento.model.Carro;
import java.util.List;

public interface CarrosCallback {
    void onSuccess(List<Carro> lista);
    void onError(String msg);
}
