package com.example.kho_hang_xuong.Model;

public class HoaDon {
    private int id_hd,id_hdct,id_user,loai_hd,id_sp,sl,tongtien;
    private String ngay;

    public HoaDon() {
    }

    public HoaDon(int id_user, int loai_hd, int tongtien, String ngay) {
        this.id_user = id_user;
        this.loai_hd = loai_hd;
        this.tongtien = tongtien;
        this.ngay = ngay;
    }

    public HoaDon(int id_hd, int id_sp, int sl) {
        this.id_hd = id_hd;
        this.id_sp = id_sp;
        this.sl = sl;
    }

    public HoaDon(int id_hd, int id_hdct, int id_user, int loai_hd, int id_sp, int sl, String ngay, int tongtien) {
        this.id_hd = id_hd;
        this.id_hdct = id_hdct;
        this.id_user = id_user;
        this.loai_hd = loai_hd;
        this.id_sp = id_sp;
        this.sl = sl;
        this.ngay = ngay;
        this.tongtien = tongtien;
    }

    public int getId_hd() {
        return id_hd;
    }

    public void setId_hd(int id_hd) {
        this.id_hd = id_hd;
    }

    public int getId_hdct() {
        return id_hdct;
    }

    public void setId_hdct(int id_hdct) {
        this.id_hdct = id_hdct;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getLoai_hd() {
        return loai_hd;
    }

    public void setLoai_hd(int loai_hd) {
        this.loai_hd = loai_hd;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
