package com.example.x.model;

public class TopRoom {
    private String nameType;
    private int numberRoom;
    private int quantity;

    public TopRoom() {
    }

    public TopRoom(String nameType, int numberRoom, int quantity) {
        this.nameType = nameType;
        this.numberRoom = numberRoom;
        this.quantity = quantity;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
