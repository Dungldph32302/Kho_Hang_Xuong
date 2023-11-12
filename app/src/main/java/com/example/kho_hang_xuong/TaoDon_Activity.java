package com.example.kho_hang_xuong;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Adapter.AdapterTaoDon;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.View.Fragment_HD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TaoDon_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTaoDon adapterTaoDon;
    SP_Dao spDao; HD_Dao hdDao; HoaDon hoaDon;
    ArrayList<SanPham> list;
    ArrayList<SanPham> list1;
    TextView tong,hh,tp;
    Button btnt;
    int index=0;
    RadioGroup radioGroup;
    RadioButton xuat,nhap;
    int LoaiHD=0;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_don);
        recyclerView=findViewById(R.id.taodon);
        tong=findViewById(R.id.tvtong);
        hh=findViewById(R.id.tvhh);
        btnt=findViewById(R.id.btnzTao);
        tp=findViewById(R.id.tvThuPhong);
        radioGroup=findViewById(R.id.chkGr);
        xuat=findViewById(R.id.radioButton);
        nhap=findViewById(R.id.radioButton2);

        hdDao=new HD_Dao(TaoDon_Activity.this);

        spDao= new SP_Dao(TaoDon_Activity.this);
        list=spDao.ALLSP();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TaoDon_Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterTaoDon=new AdapterTaoDon(TaoDon_Activity.this,list);
        recyclerView.setAdapter(adapterTaoDon);
        adapterTaoDon.setOnQuantityChangeListener(new AdapterTaoDon.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                int t = adapterTaoDon.getTongTatCa();
              list1= new ArrayList<>();
              list1= adapterTaoDon.getSelectedIds();
                hh.setText(String.valueOf(list1.size()));
                tong.setText(String.valueOf(t));
            }
        });

        btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint({"NewApi", "LocalSuppress"}) String ngay = String.valueOf(java.time.LocalDate.now());
                int chekGR=radioGroup.getCheckedRadioButtonId();
                if(chekGR==R.id.radioButton){
                    LoaiHD=1;
                } else if (chekGR==R.id.radioButton2) {
                    LoaiHD=0;
                }
                if(chekGR==-1){
                    Toast.makeText(TaoDon_Activity.this, "Vui lòng chọn loại hóa đơn", Toast.LENGTH_SHORT).show();
                }else {
                    HoaDon hd = new HoaDon();
                    hd.setId_hd(hdDao.getMaxHoaDonId());
                    hd.setId_user(2);
                    hd.setNgay(ngay);
                    hd.setLoai_hd(LoaiHD);
                    hd.setTongtien(adapterTaoDon.getTongTatCa());
                    // Thay đổi dòng sau để sử dụng phương thức mới thêm hóa đơn và chi tiết
                    if (hdDao.addHoaDonAndChiTiet(hd, list1)) {
                        Toast.makeText(TaoDon_Activity.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(TaoDon_Activity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("TaoDon_Activity", "Thêm hóa đơn và chi tiết thất bại");
                        Toast.makeText(TaoDon_Activity.this, "Thêm hóa đơn và chi tiết thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                }
        });

        // xử lý khi thu phóng rcv


    }
}