package com.example.kho_hang_xuong;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Adapter.AdapterTaoDon;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Model.SanPham;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaoDon_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTaoDon adapterTaoDon;
    SP_Dao spDao;
    ArrayList<SanPham> list;
    TextView tong,hh,tp;
    Button btnt;
    int index=0;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_don);
        recyclerView=findViewById(R.id.taodon);
        tong=findViewById(R.id.tvtong);
        hh=findViewById(R.id.tvhh);
        btnt=findViewById(R.id.btnzTao);
        tp=findViewById(R.id.tvThuPhong);

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
                List<Integer> selectedIdhds = Arrays.asList(adapterTaoDon.getSelectedIds());
                hh.setText(selectedIdhds.toString());
                tong.setText(String.valueOf(t));
            }
        });

        btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterTaoDon.setOnQuantityChangeListener(new AdapterTaoDon.OnQuantityChangeListener() {
                    @Override
                    public void onQuantityChanged() {
                        int t = adapterTaoDon.getTongTatCa();
                        List<Integer> selectedIdhds = Arrays.asList(adapterTaoDon.getSelectedIds());
                        hh.setText(selectedIdhds.toString());
                        tong.setText(String.valueOf(t));
                    }
                });
            }
        });

        // xử lý khi thu phóng rcv


    }
}