package com.example.kho_hang_xuong.Dao;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kho_hang_xuong.DataBase.dbHelper;
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
}
