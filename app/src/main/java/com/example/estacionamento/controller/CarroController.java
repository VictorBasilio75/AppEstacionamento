package com.example.estacionamento.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.example.estacionamento.model.Carro;
import com.example.estacionamento.VolleySingleton;

public class CarroController {

    // BASE correta do backend
    private final String BASE_URL = "http://10.0.2.2:8080/cars";



    private Context ctx;

    public CarroController(Context ctx) {
        this.ctx = ctx;
    }

    // ========== Cadastrar carro para usuário ==========
    public void cadastrarCarro(String userId, String brand, String model,
                               final Runnable onSuccess,
                               final ErrorCallback onError) {

        try {
            JSONObject body = new JSONObject();
            body.put("modelo", model);
            body.put("marca", brand);

            // Endpoint correto baseado no backend:
            // POST /cars/users/{userId}/cars
            String urlFinal = BASE_URL + "/users/" + userId + "/cars";

            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    urlFinal,
                    body,
                    response -> onSuccess.run(),
                    error -> onError.onError(extractError(error))
            );

            VolleySingleton.getInstance(ctx).addToRequestQueue(req);

        } catch (Exception e) {
            onError.onError("Erro ao montar JSON: " + e.getMessage());
        }
    }



    // ========== Buscar todos os carros ==========
    public void getCarros(final CarrosCallback callback) {

        JsonArrayRequest req = new JsonArrayRequest(
                Request.Method.GET,
                BASE_URL,  // GET /cars
                null,
                response -> {
                    try {
                        List<Carro> lista = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);

                            Carro c = new Carro();
                            c.setId(String.valueOf(obj.getLong("id")));
                            c.setModel(obj.getString("modelo"));
                            c.setBrand(obj.getString("marca"));

                            // Aqui deve vir o user dentro do objeto JSON
                            if (obj.has("user") && !obj.isNull("user")) {
                                JSONObject userObj = obj.getJSONObject("user");
                                c.setUserId(String.valueOf(userObj.getLong("id")));
                            } else {
                                c.setUserId("-1");
                            }

                            lista.add(c);
                        }

                        callback.onSuccess(lista);

                    } catch (Exception e) {
                        callback.onError("Erro ao converter JSON: " + e.getMessage());
                    }
                },
                error -> callback.onError("Erro ao carregar: " + extractError(error))
        );

        VolleySingleton.getInstance(ctx).addToRequestQueue(req);
    }



    private String extractError(VolleyError error) {
        try {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                return new String(error.networkResponse.data);
            }
        } catch (Exception ignored) {}
        return error.toString();
    }

    public interface CarrosCallback {
        void onSuccess(List<Carro> lista);
        void onError(String msg);
    }
}
