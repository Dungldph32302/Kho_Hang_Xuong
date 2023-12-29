package com.example.kho_hang_xuong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kho_hang_xuong.Adapter.AdapterSanPham;
import com.example.kho_hang_xuong.Adapter.AdapterSanPhaminHD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.View.Fragment_HD;

import java.util.ArrayList;

public class ActivityHD_Chitiet extends AppCompatActivity {

    TextView loai,ngTao,ngay,tong;
    HD_Dao hdDao;
    AdapterSanPhaminHD adapterSanPham;
    RecyclerView rcv;
    AppCompatButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd_chitiet);
        loai=findViewById(R.id.TVloai);
        ngTao=findViewById(R.id.Tvng);
        ngay=findViewById(R.id.TvNgay);
        tong=findViewById(R.id.TvTong);
        rcv=findViewById(R.id.rcvHd_chitiet);
        back=findViewById(R.id.appCompatButton);


        // lấy dữ liệu để xét lên thông tin hóa đơn
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            Toast.makeText(this, "bundle null", Toast.LENGTH_SHORT).show();
        }
        HoaDon hoaDon=(HoaDon) bundle.getSerializable("hd");
        if(hoaDon.getLoai_hd()==1){
               loai.setText("Hóa Đơn nhập");
        }else {
            loai.setText("Hóa Đơn xuất");
        }
        UersDao uersDao= new UersDao(getApplicationContext());
        String ten =uersDao.fullname(hoaDon.getId_user());
        String ngayy=hoaDon.getNgay().substring(8)+hoaDon.getNgay().substring(4,7)+hoaDon.getNgay().substring(0,4);
        ngTao.setText("Người tạo: "+ten);
        tong.setText("Tổng "+String.valueOf(hoaDon.getTongtien())+" VND");
        ngay.setText("Ngày :"+ngayy);
        // xử lý hóa đơn chi tiết
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);
        SP_Dao spDao = new SP_Dao(this);
        ArrayList<SanPham>list=spDao.getSanPhamByHoaDonID(hoaDon.getId_hd());
        adapterSanPham = new AdapterSanPhaminHD(this,list);
        rcv.setAdapter(adapterSanPham);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ActivityHD_Chitiet.this,MainActivity.class);
                String h="dung";
                intent.putExtra("kt",h);
                startActivity(intent);
            }
        });

    }
}