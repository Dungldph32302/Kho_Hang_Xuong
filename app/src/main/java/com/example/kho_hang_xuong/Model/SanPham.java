package com.example.kho_hang_xuong.Model;

public class SanPham {
    private  int id_sp,id_tl,soluong,sl;
    private String name,donGia,moTa;
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

    public SanPham(int id_sp, int id_tl, int soluong, String name, String donGia, String moTa) {
        this.id_sp = id_sp;
        this.id_tl = id_tl;
        this.soluong = soluong;
        this.name = name;
        this.donGia = donGia;
        this.moTa = moTa;
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

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
