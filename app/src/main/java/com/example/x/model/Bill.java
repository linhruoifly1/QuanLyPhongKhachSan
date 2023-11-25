package com.example.x.model;

public class Bill {
    private int id,idCustomer,idReceptionist;
    private String checkIn,checkOut;
    private int costRoom,costService,VAT,status,sumCost;

    public Bill(int idCustomer, int idReceptionist, String checkIn, String checkOut, int costRoom, int costService, int VAT, int status, int sumCost) {
        this.idCustomer = idCustomer;
        this.idReceptionist = idReceptionist;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.costRoom = costRoom;
        this.costService = costService;
        this.VAT = VAT;
        this.status = status;
        this.sumCost = sumCost;
    }

    public Bill(int id, int idCustomer, int idReceptionist, String checkIn, String checkOut, int costRoom, int costService, int VAT, int status, int sumCost) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idReceptionist = idReceptionist;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.costRoom = costRoom;
        this.costService = costService;
        this.VAT = VAT;
        this.status = status;
        this.sumCost = sumCost;
    }

    public Bill() {
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
    public int getCostRoom() {
        return costRoom;
    }

    public void setCostRoom(int costRoom) {
        this.costRoom = costRoom;
    }

    public int getCostService() {
        return costService;
    }

    public void setCostService(int costService) {
        this.costService = costService;
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
