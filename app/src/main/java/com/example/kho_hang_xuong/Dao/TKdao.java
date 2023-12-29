package com.example.kho_hang_xuong.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kho_hang_xuong.DataBase.dbHelper;
import com.example.kho_hang_xuong.Model.SanPham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TKdao {
    private final Context context;
    private final dbHelper dbHelper;

    public TKdao(Context context) {
        this.context = context;
        dbHelper = new dbHelper(context);
    }

    @SuppressLint("Range")
    public double getTienTonKho() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String queryInput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalInput " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.LoaiHoaDon = 0";

        String queryOutput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalOutput " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.LoaiHoaDon = 1";

        // Thực hiện truy vấn và lấy tổng số tiền nhập và xuất
        Cursor cursorInput = db.rawQuery(queryInput, null);
        double totalInput = 0;
        if (cursorInput.moveToFirst()) {
            totalInput = cursorInput.getDouble(cursorInput.getColumnIndex("TotalInput"));
        }
        cursorInput.close();

        Cursor cursorOutput = db.rawQuery(queryOutput, null);
        double totalOutput = 0;
        if (cursorOutput.moveToFirst()) {
            totalOutput = cursorOutput.getDouble(cursorOutput.getColumnIndex("TotalOutput"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về tổng số tiền tồn kho
        return totalInput - totalOutput;
    }
    // Phương thức lấy số lượng sản phẩm nhập
    @SuppressLint("Range")
    public int getSlHdNhap() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy số lượng hóa đơn nhập
        String queryInput = "SELECT COUNT(*) AS TotalInput " +
                "FROM HoaDon " +
                "WHERE HoaDon.LoaiHoaDon = 1";

        // Thực hiện truy vấn và lấy số lượng hóa đơn nhập
        Cursor cursorInput = db.rawQuery(queryInput, null);
        int totalInput = 0;
        if (cursorInput.moveToFirst()) {
            totalInput = cursorInput.getInt(cursorInput.getColumnIndex("TotalInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về số lượng hóa đơn nhập
        return totalInput;
    }

    // Phương thức lấy số lượng sản phẩm xuất
    @SuppressLint("Range")
  public int getSlHdXuat() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy số lượng hóa đơn nhập
        String queryInput = "SELECT COUNT(*) AS TotalInput " +
                "FROM HoaDon " +
                "WHERE HoaDon.LoaiHoaDon = 0";

        // Thực hiện truy vấn và lấy số lượng hóa đơn nhập
        Cursor cursorInput = db.rawQuery(queryInput, null);
        int totalInput = 0;
        if (cursorInput.moveToFirst()) {
            totalInput = cursorInput.getInt(cursorInput.getColumnIndex("TotalInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về số lượng hóa đơn nhập
        return totalInput;
    }

    // Phương thức lấy tổng số tiền sản phẩm trong hóa đơn nhập
    @SuppressLint("Range")
    public double getTotalInputAmount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Truy vấn để lấy tổng số tiền từ hóa đơn nhập
        String queryInput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalInputAmount " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.LoaiHoaDon = 0";

        // Thực hiện truy vấn và lấy tổng số tiền nhập
        Cursor cursorInput = db.rawQuery(queryInput, null);
        double totalInputAmount = 0;
        if (cursorInput.moveToFirst()) {
            totalInputAmount = cursorInput.getDouble(cursorInput.getColumnIndex("TotalInputAmount"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về tổng số tiền của sản phẩm trong hóa đơn nhập
        return totalInputAmount;
    }

    // Phương thức lấy tổng số tiền sản phẩm trong hóa đơn xuất
    @SuppressLint("Range")
    public double getTotalOutputAmount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy tổng số tiền từ hóa đơn xuất
        String queryOutput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalOutputAmount " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.LoaiHoaDon = 1";

        // Thực hiện truy vấn và lấy tổng số tiền xuất
        Cursor cursorOutput = db.rawQuery(queryOutput, null);
        double totalOutputAmount = 0;
        if (cursorOutput.moveToFirst()) {
            totalOutputAmount = cursorOutput.getDouble(cursorOutput.getColumnIndex("TotalOutputAmount"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        // Trả về tổng số tiền của sản phẩm trong hóa đơn xuất
        return totalOutputAmount;
    }

    @SuppressLint("Range")
    public ArrayList<SanPham> getTop10Products() {
        ArrayList<SanPham> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy top 10 sản phẩm xuất nhiều nhất
        String query = "SELECT SanPham.*, SUM(HoaDon_ChiTiet.so_luong) AS TotalSold " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.LoaiHoaDon = 1 " +
                "GROUP BY SanPham.ID_SP " +
                "ORDER BY TotalSold DESC " +
                "LIMIT 10";

        // Thực hiện truy vấn và lấy danh sách sản phẩm
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SanPham sp = new SanPham();
                sp.setId_sp(cursor.getInt(cursor.getColumnIndex("ID_SP")));
                sp.setId_tl(cursor.getInt(cursor.getColumnIndex("ID_TL")));
                sp.setName(cursor.getString(cursor.getColumnIndex("NAME_SP")));
                sp.setDongia(cursor.getInt(cursor.getColumnIndex("DonGia")));
                sp.setSoluong(cursor.getInt(cursor.getColumnIndex("SoLuong")));
                sp.setMoTa(cursor.getString(cursor.getColumnIndex("MoTa")));
                sp.setAnh(cursor.getString(cursor.getColumnIndex("anh")));
                productList.add(sp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        // Trả về danh sách top 10 sản phẩm xuất nhiều nhất
        return productList;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonNhapTheoNgay(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Chuyển định dạng ngày từ chuỗi sang Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(ngay);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi khi chuyển đổi ngày
        }

        // Chuyển ngày thành chuỗi định dạng để sử dụng trong truy vấn
        String formattedDate = dateFormat.format(parsedDate);

        // Truy vấn số lượng hóa đơn nhập theo ngày
        String queryInput = "SELECT COUNT(*) AS CountInput FROM HoaDon WHERE strftime('%Y-%m-%d', Ngay) = '" + formattedDate + "' AND LoaiHoaDon = 0";
        Cursor cursorInput = db.rawQuery(queryInput, null);

        int countInput = 0;
        if (cursorInput.moveToFirst()) {
            countInput = cursorInput.getInt(cursorInput.getColumnIndex("CountInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countInput;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonXuatTheoNgay(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Chuyển định dạng ngày từ chuỗi sang Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(ngay);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu có lỗi khi chuyển đổi ngày
        }

        // Chuyển ngày thành chuỗi định dạng để sử dụng trong truy vấn
        String formattedDate = dateFormat.format(parsedDate);

        // Truy vấn số lượng hóa đơn xuất theo ngày
        String queryOutput = "SELECT COUNT(*) AS CountOutput FROM HoaDon WHERE strftime('%Y-%m-%d', Ngay) = '" + formattedDate + "' AND LoaiHoaDon = 1";
        Cursor cursorOutput = db.rawQuery(queryOutput, null);

        int countOutput = 0;
        if (cursorOutput.moveToFirst()) {
            countOutput = cursorOutput.getInt(cursorOutput.getColumnIndex("CountOutput"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countOutput;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonNhapTheoThang(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Trích xuất thông tin tháng từ giá trị ngày
        String thang = ngay.substring(0, 7);

        // Truy vấn số lượng hóa đơn nhập theo tháng
        String queryInput = "SELECT COUNT(*) AS CountInput FROM HoaDon " +
                "WHERE strftime('%Y-%m', Ngay) = '" + thang + "' AND LoaiHoaDon = 0";

        Cursor cursorInput = db.rawQuery(queryInput, null);

        int countInput = 0;
        if (cursorInput.moveToFirst()) {
            countInput = cursorInput.getInt(cursorInput.getColumnIndex("CountInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countInput;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonXuatTheoThang(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Trích xuất thông tin tháng từ giá trị ngày
        String thang = ngay.substring(0, 7);

        // Truy vấn số lượng hóa đơn xuất theo tháng
        String queryOutput = "SELECT COUNT(*) AS CountOutput FROM HoaDon " +
                "WHERE strftime('%Y-%m', Ngay) = '" + thang + "' AND LoaiHoaDon = 1";

        Cursor cursorOutput = db.rawQuery(queryOutput, null);

        int countOutput = 0;
        if (cursorOutput.moveToFirst()) {
            countOutput = cursorOutput.getInt(cursorOutput.getColumnIndex("CountOutput"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countOutput;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonNhapTheoNam(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Trích xuất thông tin năm từ giá trị ngày
        String nam = ngay.substring(0, 4);

        // Truy vấn số lượng hóa đơn nhập theo năm
        String queryInput = "SELECT COUNT(*) AS CountInput FROM HoaDon " +
                "WHERE strftime('%Y', Ngay) = '" + nam + "' AND LoaiHoaDon = 0";

        Cursor cursorInput = db.rawQuery(queryInput, null);

        int countInput = 0;
        if (cursorInput.moveToFirst()) {
            countInput = cursorInput.getInt(cursorInput.getColumnIndex("CountInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countInput;
    }

    @SuppressLint("Range")
    public int getSoLuongHoaDonXuatTheoNam(String ngay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Trích xuất thông tin năm từ giá trị ngày
        String nam = ngay.substring(0, 4);

        // Truy vấn số lượng hóa đơn xuất theo năm
        String queryOutput = "SELECT COUNT(*) AS CountOutput FROM HoaDon " +
                "WHERE strftime('%Y', Ngay) = '" + nam + "' AND LoaiHoaDon = 1";

        Cursor cursorOutput = db.rawQuery(queryOutput, null);

        int countOutput = 0;
        if (cursorOutput.moveToFirst()) {
            countOutput = cursorOutput.getInt(cursorOutput.getColumnIndex("CountOutput"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return countOutput;
    }
    @SuppressLint("Range")
    public double getTongTienHoaDonNhapTrongKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn tổng tiền hóa đơn nhập trong khoảng thời gian
        String queryInput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalInput " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.Ngay BETWEEN '" + ngayBatDau + "' AND '" + ngayKetThuc + "' AND HoaDon.LoaiHoaDon = 0";

        Cursor cursorInput = db.rawQuery(queryInput, null);

        double totalInput = 0;
        if (cursorInput.moveToFirst()) {
            totalInput = cursorInput.getDouble(cursorInput.getColumnIndex("TotalInput"));
        }
        cursorInput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return totalInput;
    }

    @SuppressLint("Range")
    public double getTongTienHoaDonXuatTrongKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn tổng tiền hóa đơn xuất trong khoảng thời gian
        String queryOutput = "SELECT SUM(SanPham.DonGia * HoaDon_ChiTiet.so_luong) AS TotalOutput " +
                "FROM SanPham " +
                "INNER JOIN HoaDon_ChiTiet ON SanPham.ID_SP = HoaDon_ChiTiet.ID_SP " +
                "INNER JOIN HoaDon ON HoaDon_ChiTiet.ID_HD = HoaDon.ID_HD " +
                "WHERE HoaDon.Ngay BETWEEN '" + ngayBatDau + "' AND '" + ngayKetThuc + "' AND HoaDon.LoaiHoaDon = 1";

        Cursor cursorOutput = db.rawQuery(queryOutput, null);

        double totalOutput = 0;
        if (cursorOutput.moveToFirst()) {
            totalOutput = cursorOutput.getDouble(cursorOutput.getColumnIndex("TotalOutput"));
        }
        cursorOutput.close();

        // Đóng cơ sở dữ liệu
        db.close();

        return totalOutput;
    }


}
