package com.example.kho_hang_xuong.Model;

public class SanPham {
    private  int id_sp,id_tl,soluong,sl;
    private String name,moTa,anh;
    private int dongia;
    // Trường mới để theo dõi trạng thái đã chọn
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public SanPham() {
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public int getId_tl() {
        return id_tl;
    }

    public void setId_tl(int id_tl) {
        this.id_tl = id_tl;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDongia() {
        return dongia;
    }

    public void setDongia(int dongia) {
        this.dongia = dongia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
