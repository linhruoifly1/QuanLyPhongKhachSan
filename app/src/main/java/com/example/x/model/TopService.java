package com.example.x.model;

public class TopService {
    private String tenDichVu;
    private int soLuong;

    public TopService(String tenDichVu, int soLuong) {
        this.tenDichVu = tenDichVu;
        this.soLuong = soLuong;
    }

    public TopService() {
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
