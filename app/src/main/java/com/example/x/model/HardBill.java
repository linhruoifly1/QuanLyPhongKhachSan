package com.example.x.model;

public class HardBill {
    private int id,idBill,idRoom,quantityPeople;

    public HardBill(int idBill, int idRoom, int quantityPeople) {
        this.idBill = idBill;
        this.idRoom = idRoom;
        this.quantityPeople = quantityPeople;
    }

    public HardBill() {
    }

    public HardBill(int id, int idBill, int idRoom, int quantityPeople) {
        this.id = id;
        this.idBill = idBill;
        this.idRoom = idRoom;
        this.quantityPeople = quantityPeople;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getQuantityPeople() {
        return quantityPeople;
    }

    public void setQuantityPeople(int quantityPeople) {
        this.quantityPeople = quantityPeople;
    }
}
