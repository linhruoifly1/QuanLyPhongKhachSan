package com.example.x.model;

import java.io.Serializable;

public class Bill implements Serializable {
    private int id,idCustomer,idReceptionist,idService;
    private String checkIn,checkOut;
    private int VAT,status,sumCost;

    public Bill(int idCustomer, int idReceptionist,int idService, String checkIn, String checkOut, int VAT, int status, int sumCost) {
        this.idCustomer = idCustomer;
        this.idReceptionist = idReceptionist;
        this.idService = idService;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.VAT = VAT;
        this.status = status;
        this.sumCost = sumCost;
    }

    public Bill(int idCustomer, int idReceptionist, int idService, String checkIn, String checkOut, int VAT, int status) {
        this.idCustomer = idCustomer;
        this.idReceptionist = idReceptionist;
        this.idService = idService;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.VAT = VAT;
        this.status = status;
    }

    public Bill(int id, int idCustomer, int idReceptionist, int idService, String checkIn, String checkOut, int VAT, int status, int sumCost) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idReceptionist = idReceptionist;
        this.idService = idService;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.VAT = VAT;
        this.status = status;
        this.sumCost = sumCost;
    }

    public Bill() {
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdReceptionist() {
        return idReceptionist;
    }

    public void setIdReceptionist(int idReceptionist) {
        this.idReceptionist = idReceptionist;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSumCost() {
        return sumCost;
    }

    public void setSumCost(int sumCost) {
        this.sumCost = sumCost;
    }
}
