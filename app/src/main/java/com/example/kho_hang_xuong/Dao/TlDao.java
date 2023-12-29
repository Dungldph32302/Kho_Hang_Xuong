package com.example.kho_hang_xuong.Dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kho_hang_xuong.DataBase.dbHelper;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;

import java.util.ArrayList;

public class TlDao {
    private  final Context context;
    private final dbHelper db;

    public TlDao(Context context) {
        this.context = context;
        db= new dbHelper(context);
    }

    public ArrayList<TheLoai> getAllTl(){
        ArrayList<TheLoai> list= new ArrayList<>();
        SQLiteDatabase dbb=db.getReadableDatabase();
        try {
            Cursor cursor=dbb.rawQuery("Select * from TheLoai ",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                        TheLoai tl= new TheLoai();
                        tl.setID(cursor.getInt(0));
                        tl.setName(cursor.getString(1));
                        list.add(tl);
                        cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG,"Lỗi truy vấn thể Loại ",e);
        }

        return  list;
    }
    public boolean deletetl(int id){
        SQLiteDatabase dbb= db.getWritableDatabase();
        long row=dbb.delete("TheLoai","ID_TL=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updateSP(TheLoai sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase b=db.getWritableDatabase();
        values.put("ID_TL",sp.getID());
        values.put("Name",sp.getName());
        long row=b.update("TheLoai",values,"ID_TL=?",new String[]{String.valueOf(sp.getID())});
        return (row>0);
    }
    public boolean addtl(TheLoai sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase d=db.getWritableDatabase();
        values.put("Name",sp.getName());
        long row=d.insert("TheLoai",null,values);
        return (row>0);
    }
}
