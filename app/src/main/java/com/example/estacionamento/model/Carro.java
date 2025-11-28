package com.example.estacionamento.model;

public class Carro {
    private String id;
    private String model;
    private String brand;
    private String userId;

    public String getId() { return id; }
    public String getModel() { return model; }
    public String getBrand() { return brand; }
    public String getUserId() { return userId; }

    public void setId(String id) { this.id = id; }
    public void setModel(String model) { this.model = model; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setUserId(String userId) { this.userId = userId; }
}
