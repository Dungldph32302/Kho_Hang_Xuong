package com.example.kho_hang_xuong.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.kho_hang_xuong.DataBase.dbHelper;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;

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
            Cursor cursor = db.rawQuery("SELECT HoaDon.*, User.FullName " +
                    "FROM HoaDon " +
                    "INNER JOIN User ON HoaDon.ID_User = User.ID_Use", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    HoaDon hoaDon = new HoaDon();

                    // Set thông tin từ bảng HoaDon
                    hoaDon.setId_hd(cursor.getInt(cursor.getColumnIndex("ID_HD")));
                    hoaDon.setId_user(cursor.getInt(cursor.getColumnIndex("ID_User")));
                    hoaDon.setNgay(cursor.getString(cursor.getColumnIndex("Ngay")));
                    hoaDon.setLoai_hd(cursor.getInt(cursor.getColumnIndex("LoaiHoaDon")));
                    hoaDon.setTongtien(cursor.getInt(cursor.getColumnIndex("TongTien")));
                    // Set tên đầy đủ từ bảng User
                    hoaDon.setFullName(cursor.getString(cursor.getColumnIndex("FullName")));

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


    @SuppressLint("Range")
    public String getfullnamebyid(int idUser) {
        String fullName = "";
        SQLiteDatabase db = dbHelpe.getReadableDatabase();

        try {
            String query = "SELECT FullName FROM User WHERE ID_Use = ?";
            String[] selectionArgs = {String.valueOf(idUser)};
            Cursor cursor = db.rawQuery(query, selectionArgs);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                fullName = cursor.getString(cursor.getColumnIndex("FullName"));
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getFullNameByIdUser", e);
        } finally {
            db.close();
        }

        return fullName;
    }

    public boolean deleteHD(int id){
        SQLiteDatabase db= dbHelpe.getWritableDatabase();
        long row=db.delete("HoaDon","ID_HD=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean deleteHDct(int id){
        SQLiteDatabase db= dbHelpe.getWritableDatabase();
        long row=db.delete("HoaDon_ChiTiet","ID_HD=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updateHD(HoaDon sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbHelpe.getWritableDatabase();
        values.put("ID_User",sp.getId_user());
        values.put("Ngay",sp.getNgay());
        values.put("LoaiHoaDon",sp.getLoai_hd());
        values.put("TongTien",sp.getTongtien());
        long row=db.update("HoaDon",values,"ID_HD=?",new String[]{String.valueOf(sp.getId_hd())});
        return (row>0);
    }
    public int addHD(HoaDon sp) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbHelpe.getWritableDatabase();

        values.put("ID_User", sp.getId_user());
        values.put("Ngay", sp.getNgay());
        values.put("LoaiHoaDon", sp.getLoai_hd());
        values.put("TongTien", sp.getTongtien());

        long newRowId = db.insert("HoaDon", null, values);

        // Trả về ID_HD mới tạo hoặc -1 nếu có lỗi
        return (int) newRowId;
    }
    public boolean deleteHDCT(int id){
        SQLiteDatabase db= dbHelpe.getWritableDatabase();
        long row=db.delete("HoaDon_ChiTiet","ID_HDCT=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updateHDCT(HoaDon sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbHelpe.getWritableDatabase();
        values.put("ID_SP",sp.getId_sp());
        values.put("ID_HD",sp.getId_hd());
        values.put("so_luong",sp.getSl());
        long row=db.update("HoaDon_ChiTiet",values,"ID_HDCT=?",new String[]{String.valueOf(sp.getId_hd())});
        return (row>0);
    }
    public boolean addHDCT(HoaDon sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbHelpe.getWritableDatabase();
        values.put("ID_SP",sp.getId_sp());
        values.put("ID_HD",sp.getId_hd());
        values.put("so_luong",sp.getSl());
        long row=db.insert("HoaDon_ChiTiet",null,values);
        return (row>0);
    }
    public int getMaxHoaDonId() {
        SQLiteDatabase db = dbHelpe.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(ID_HD) FROM HoaDon", null);
        int maxId = -1;

        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return maxId+1;
    }

    public boolean addHoaDonAndChiTiet(HoaDon hoaDon, ArrayList<SanPham> sanPhamList) {
        SQLiteDatabase db = dbHelpe.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues hoaDonValues = new ContentValues();
            hoaDonValues.put("ID_HD",hoaDon.getId_hd());
            hoaDonValues.put("ID_User", hoaDon.getId_user());
            hoaDonValues.put("Ngay", hoaDon.getNgay());
            hoaDonValues.put("LoaiHoaDon", hoaDon.getLoai_hd());
            hoaDonValues.put("TongTien", hoaDon.getTongtien());
            long hoaDonId = db.insert("HoaDon", null, hoaDonValues);
            if (hoaDonId != -1) {
                if(sanPhamList.size()>=0) {


                    for (SanPham sanPham : sanPhamList) {
                        ContentValues chiTietValues = new ContentValues();
                        chiTietValues.put("ID_SP", sanPham.getId_sp());
                        chiTietValues.put("ID_HD", hoaDon.getId_hd());
                        chiTietValues.put("so_luong", sanPham.getSl());
                        long row = db.insert("HoaDon_ChiTiet", null, chiTietValues);
                        if (row == -1) {
                            Log.e("AddHoaDonAndChiTiet", "Lỗi khi thêm chi tiết hóa đơn, ID_SP: " + sanPham.getId_sp());
                            db.endTransaction();
                            return false;
                        }
                    }
                }else {
                    Toast.makeText(context, "Vui lòng chọn sp ", Toast.LENGTH_SHORT).show();
                }

                db.setTransactionSuccessful();
                return true;
            } else {
                Log.e("AddHoaDonAndChiTiet", "Lỗi khi thêm hóa đơn");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return false;
    }

    @SuppressLint("Range")
    public ArrayList<HoaDon> getAllHoaDonByLoai(int loaiHoaDon) {
        ArrayList<HoaDon> hoaDonList = new ArrayList<>();
        SQLiteDatabase db = dbHelpe.getReadableDatabase();
        try {
            // Sử dụng câu truy vấn SQL với điều kiện LoaiHoaDon và INNER JOIN bảng User
            Cursor cursor = db.rawQuery("SELECT HoaDon.*, User.FullName FROM HoaDon INNER JOIN User ON HoaDon.ID_User = User.ID_Use WHERE HoaDon.LoaiHoaDon = ?", new String[]{String.valueOf(loaiHoaDon)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    HoaDon hoaDon = new HoaDon();

                    // Set thông tin từ bảng HoaDon
                    hoaDon.setId_hd(cursor.getInt(cursor.getColumnIndex("ID_HD")));
                    hoaDon.setId_user(cursor.getInt(cursor.getColumnIndex("ID_User")));
                    hoaDon.setNgay(cursor.getString(cursor.getColumnIndex("Ngay")));
                    hoaDon.setLoai_hd(cursor.getInt(cursor.getColumnIndex("LoaiHoaDon")));
                    hoaDon.setTongtien(cursor.getInt(cursor.getColumnIndex("TongTien")));

                    // Set thông tin FullName từ bảng User
                    hoaDon.setFullName(cursor.getString(cursor.getColumnIndex("FullName")));

                    hoaDonList.add(hoaDon);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getAllHoaDonByLoai", e);
        } finally {
            db.close();
        }

        return hoaDonList;
    }


}
