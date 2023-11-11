package com.example.kho_hang_xuong.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public static final String DB ="QLKH";
    public dbHelper(@Nullable Context context) {
        super(context,DB,null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (\n" +
                "    ID_Use INTEGER  PRIMARY KEY AUTOINCREMENT,\n" +
                "    UserName TEXT NOT NULL,        \n" +
                "    Password TEXT NOT NULL,        \n" +
                "    FullName TEXT NOT NULL,        \n" +
                "    Email TEXT NOT NULL,           \n" +
                "\t  ChucVu INT not null            \n" +
                ");");
        db.execSQL("CREATE TABLE TheLoai (\n" +
                "    ID_TL INTEGER  PRIMARY KEY AUTOINCREMENT,        \n" +
                "    Name TEXT NOT NULL             \n" +
                ");");
        db.execSQL("CREATE TABLE SanPham (\n" +
                "    ID_SP INTEGER  PRIMARY KEY,        \n" +
                "    ID_TL INT NOT NULL,            \n" +
                "    NAME_SP TEXT NOT NULL,         \n" +
                "    DonGia DECIMAL(10, 2) NOT NULL,\n" +
                "    SoLuong INT NOT NULL,          \n" +
                "    MoTa TEXT,                     \n" +
                "    FOREIGN KEY (ID_TL) REFERENCES TheLoai(ID_TL)\n" +
                ");");
        db.execSQL("CREATE TABLE HoaDon (\n" +
                "    ID_HD INT  PRIMARY KEY,        \n" +
                "    ID_User INT,\n" +
                "    Ngay date  NOT NULL,           \n" +
                "\t  LoaiHoaDon INT NOT NULL,         \n" +
                "   TongTien TEXT NOT NULL,         \n" +
                "    FOREIGN KEY (ID_User) REFERENCES User(ID_User)\n" +
                ");");
        db.execSQL("CREATE TABLE HoaDon_ChiTiet (\n" +
                "\tID_HDCT INT PRIMARY KEY,         \n" +
                "    ID_SP INT NOT NULL,            \n" +
                "    ID_HD INT NOT NULL,            \n" +
                "    so_luong INT NOT NULL,         \n" +
                "    FOREIGN KEY (ID_SP) REFERENCES SanPham(ID_SP),\n" +
                "    FOREIGN KEY (ID_HD) REFERENCES HoaDon(ID_HD)\n" +
                ");");

        // thêm dữ liệu
        db.execSQL("INSERT INTO User (UserName, Password, FullName, Email, ChucVu)\n" +
                "VALUES\n" +
                "    ('User1', 'Password1', 'Full Name 1', 'user1@example.com', 1),\n" +
                "    ('User2', 'Password2', 'Full Name 2', 'user2@example.com', 2),\n" +
                "    ('User3', 'Password3', 'Full Name 3', 'user3@example.com', 3);\n");
        db.execSQL(" INSERT INTO TheLoai (ID_TL, Name)\n" +
                "VALUES\n" +
                "    (1, 'Thể loại 1'),\n" +
                "    (2, 'Thể loại 2'),\n" +
                "    (3, 'Thể loại 3');\n");
        db.execSQL("INSERT INTO SanPham (ID_SP, ID_TL, NAME_SP, DonGia, SoLuong, MoTa)\n" +
                "VALUES\n" +
                "    (1, 1, 'Sản phẩm 1', 10.99, 100, 'Mô tả sản phẩm 1'),\n" +
                "    (2, 2, 'Sản phẩm 2', 15.99, 50, 'Mô tả sản phẩm 2'),\n" +
                "    (3, 1, 'Sản phẩm 1', 10.99, 100, 'Mô tả sản phẩm 1'),\n" +
                "    (4, 1, 'Sản phẩm 1', 10.99, 100, 'Mô tả sản phẩm 1'),\n" +
                "    (5, 1, 'Sản phẩm 3', 20.99, 75, 'Mô tả sản phẩm 3');\n ");
        db.execSQL(" INSERT INTO HoaDon (ID_HD, ID_User, Ngay, LoaiHoaDon, TongTien)\n" +
                "VALUES\n" +
                "    (1, 1, '2023-11-06', 1, '100.00'),\n" +
                "    (2, 2, '2023-11-07',2, '150.00'),\n" +
                "    (3, 3, '2023-11-08', 1, '200.00');\n");
        db.execSQL("INSERT INTO HoaDon_ChiTiet (ID_SP, ID_HD, so_luong)\n" +
                "VALUES\n" +
                "    (1, 1, 5),\n" +
                "    (2, 2, 3),\n" +
                "    (2, 1, 4),\n" +
                "    (3, 3, 2);\n ");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion!=oldVersion){
            db.execSQL("drop table if exists User");
            db.execSQL("drop table if exists TheLoai");
            db.execSQL("drop table if exists SanPham");
            db.execSQL("drop table if exists HoaDon");
            db.execSQL("drop table if exists HoaDon_ChiTiet");
            onCreate(db);
        }
    }
}
