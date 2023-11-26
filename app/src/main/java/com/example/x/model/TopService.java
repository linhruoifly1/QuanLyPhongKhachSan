package com.example.x.model;

public class TopService {
    private String nameService;
    private int QuantityService;

    public TopService(String nameService, int QuantityService) {
        this.nameService = nameService;
        this.QuantityService = QuantityService;
    }

    public TopService() {
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public int getQuantityService() {
        return QuantityService;
    }

    public void setQuantityService(int quantityService) {
        QuantityService = quantityService;
    }
}
