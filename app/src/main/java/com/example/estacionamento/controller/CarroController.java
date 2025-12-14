package com.example.estacionamento.controller;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            // Endpoint do backend
            String urlFinal = BASE_URL + "/users/" + userId + "/cars";

            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    urlFinal,
                    body,
                    response -> onSuccess.run(),
                    error -> onError.onError("Erro ao cadastrar: " + extractError(error))

            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    return headers;
                }
            };

            VolleySingleton.getInstance(ctx).addToRequestQueue(req);

        } catch (Exception e) {
            onError.onError("Erro ao montar JSON: " + e.getMessage());
        }
    }

    // ========== EDITAR CARRO ==========
    public void editarCarro(String userId, String carId, String modelo, String marca,
                            final Runnable onSuccess,
                            final ErrorCallback onError) {

        try {
            JSONObject body = new JSONObject();
            body.put("modelo", modelo);
            body.put("marca", marca);

            String url = BASE_URL + "/users/" + userId + "/cars/" + carId;

            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    body,
                    response -> onSuccess.run(),
                    error -> onError.onError("Erro ao editar: " + extractError(error))
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            VolleySingleton.getInstance(ctx).addToRequestQueue(req);

        } catch (Exception e) {
            onError.onError("Erro ao montar JSON: " + e.getMessage());
        }
    }


    public void excluirCarro(String carId,
                             final Runnable onSuccess,
                             final ErrorCallback onError) {

        String urlFinal = BASE_URL + "/" + carId;

        JsonObjectRequest req = new JsonObjectRequest(
                Request.Method.DELETE,
                urlFinal,
                null,
                response -> onSuccess.run(),
                error -> onError.onError("Erro ao deletar: " + extractError(error))
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        VolleySingleton.getInstance(ctx).addToRequestQueue(req);
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
        if (error == null) {
            log("ERRO DESCONHECIDO: error == null");
            return "Erro nulo";
        }

        if (error.networkResponse != null && error.networkResponse.data != null) {
            String msg = new String(error.networkResponse.data);
            log("ERRO COM RESPOSTA: " + msg);
            return msg;
        }

        log("ERRO SEM RESPOSTA: " + error.toString());
        return error.toString();
    }


    public interface CarrosCallback {
        void onSuccess(List<Carro> lista);
        void onError(String msg);
    }

    private void log(String msg) {
        android.util.Log.e("VOLLEY_DEBUG", msg);
    }

}
