package com.example.kho_hang_xuong.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kho_hang_xuong.DataBase.dbHelper;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;

import java.util.ArrayList;

public class SP_Dao {
    private final Context context;
    private final dbHelper dbHelpe;

    public SP_Dao(Context context) {
        this.context = context;
        dbHelpe = new dbHelper(context);
    }
    public ArrayList<SanPham> ALLSP(){
        ArrayList<SanPham>list= new ArrayList<>();
        SQLiteDatabase db=dbHelpe.getReadableDatabase();
        try {
            Cursor cursor=db.rawQuery("Select * from SanPham  ",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    SanPham sp= new SanPham();
                    sp.setId_sp(cursor.getInt(0));
                    sp.setId_tl(cursor.getInt(1));
                    sp.setName(cursor.getString(2));
                    sp.setDonGia(cursor.getString(3));
                    sp.setSoluong(cursor.getInt(4));
                    sp.setMoTa(cursor.getString(5));
                    list.add(sp);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG, "Lỗi allSP",e);
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<SanPham> getSanPhamByHoaDonID(int hoaDonID) {
        ArrayList<SanPham> listSanPham = new ArrayList<>();
        SQLiteDatabase db = dbHelpe.getReadableDatabase();

        try {
            String query = "SELECT SanPham.*, HoaDon_ChiTiet.so_luong " +
                    "FROM SanPham " +
                    "JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                    "WHERE HoaDon_ChiTiet.ID_HD = ?";


            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hoaDonID)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SanPham sp = new SanPham();
                    sp.setId_sp(cursor.getInt(cursor.getColumnIndex("ID_SP")));
                    sp.setId_tl(cursor.getInt(cursor.getColumnIndex("ID_TL")));
                    sp.setName(cursor.getString(cursor.getColumnIndex("NAME_SP")));
                    sp.setDonGia(cursor.getString(cursor.getColumnIndex("DonGia")));
                    sp.setSoluong(cursor.getInt(cursor.getColumnIndex("SoLuong")));
                    sp.setMoTa(cursor.getString(cursor.getColumnIndex("MoTa")));
                    int soLuong = cursor.getInt(cursor.getColumnIndex("so_luong"));
                    sp.setSl(soLuong);
                    listSanPham.add(sp);
                    cursor.moveToNext();
                }
            }

            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getSanPhamByHoaDonID", e);
        } finally {
            db.close();
        }

        return listSanPham;
    }

    @SuppressLint("Range")
    public int getSoLuongByID(int idSP) {
        SQLiteDatabase db = dbHelpe.getReadableDatabase();
        int soLuong = -1;  // Giá trị mặc định nếu không tìm thấy

        String query = "SELECT SoLuong FROM SanPham WHERE ID_SP = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idSP)});

        if (cursor.moveToFirst()) {
            soLuong = cursor.getInt(cursor.getColumnIndex("SoLuong"));
        }

        cursor.close();
        db.close();

        return soLuong;
    }
    public boolean updateSP(SanPham sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbHelpe.getWritableDatabase();
        values.put("ID_SP",sp.getId_sp());
        values.put("ID_TL",sp.getId_tl());
        values.put("NAME_SP",sp.getName());
        values.put("SoLuong",sp.getSoluong());
        values.put("MoTa",sp.getMoTa());
        long row=db.update("SanPham",values,"ID_HD=?",new String[]{String.valueOf(sp.getId_sp())});
        return (row>0);
    }

}