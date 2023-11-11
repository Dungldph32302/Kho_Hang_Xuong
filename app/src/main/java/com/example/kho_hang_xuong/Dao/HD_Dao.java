package com.example.kho_hang_xuong.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kho_hang_xuong.DataBase.dbHelper;
import com.example.kho_hang_xuong.Model.HoaDon;

import java.util.ArrayList;

public class HD_Dao {
    private final Context context;
    private final dbHelper dbHelpe;

    public HD_Dao(Context context) {
        this.context = context;
        dbHelpe = new dbHelper(context);
    }

    @SuppressLint("Range")
    public ArrayList<HoaDon> getAllHoaDonWithDetails() {
        ArrayList<HoaDon> hoaDonList = new ArrayList<>();
        SQLiteDatabase db = dbHelpe.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM HoaDon JOIN HoaDon_ChiTiet ON HoaDon.ID_HD = HoaDon_ChiTiet.ID_HD", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    HoaDon hoaDon = new HoaDon();

                    // Set thông tin từ bảng HoaDon
                    hoaDon.setId_hd(cursor.getInt(cursor.getColumnIndex("ID_HD")));
                    hoaDon.setId_user(cursor.getInt(cursor.getColumnIndex("ID_User")));
                    hoaDon.setNgay(cursor.getString(cursor.getColumnIndex("Ngay")));
                    hoaDon.setLoai_hd(cursor.getInt(cursor.getColumnIndex("LoaiHoaDon")));
                    hoaDon.setTongtien(cursor.getString(cursor.getColumnIndex("TongTien")));

                    // Set thông tin từ bảng HoaDon_ChiTiet

                    hoaDon.setId_hdct(cursor.getInt(cursor.getColumnIndex("ID_HDCT")));
                    hoaDon.setId_sp(cursor.getInt(cursor.getColumnIndex("ID_SP")));
                    hoaDon.setId_hd(cursor.getInt(cursor.getColumnIndex("ID_HD")));
                    hoaDon.setSl(cursor.getInt(cursor.getColumnIndex("so_luong")));

                    // Thêm chi tiết vào hóa đơn


                    hoaDonList.add(hoaDon);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getAllHoaDonWithDetails", e);
        } finally {
            db.close();
        }

        return hoaDonList;
    }

}
