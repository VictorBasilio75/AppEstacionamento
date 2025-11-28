package com.example.estacionamento.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.estacionamento.VolleySingleton;

import org.json.JSONObject;

public class UserController {

    private static final String TAG = "UserController";
    private final String BASE_URL = "http://10.0.2.2:8080/users";

    private final Context ctx;

    public UserController(Context ctx) {
        this.ctx = ctx;
    }

    public void cadastrarUsuario(String name, String email, String password,
                                 Runnable onSuccess, java.util.function.Consumer<String> onError) {
        try {
            JSONObject body = new JSONObject();
            // API do José espera name,email,password
            body.put("name", name);
            body.put("email", email);
            body.put("password", password);

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BASE_URL, body,
                    response -> onSuccess.run(),
                    error -> {
                        String msg = extractVolleyError(error);
                        Log.e(TAG, "POST user error: " + msg);
                        onError.accept(msg);
                    });

            VolleySingleton.getInstance(ctx).addToRequestQueue(req);
        } catch (Exception e) {
            onError.accept("Erro ao montar JSON: " + e.getMessage());
        }
    }

    private String extractVolleyError(com.android.volley.VolleyError error) {
        try {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                String body = new String(error.networkResponse.data);
                return "HTTP " + error.networkResponse.statusCode + " - " + body;
            } else {
                return error.toString();
            }
        } catch (Exception e) {
            return error.toString();
        }
    }
}
