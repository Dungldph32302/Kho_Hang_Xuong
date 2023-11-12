package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kho_hang_xuong.Adapter.AdapterTL;
import com.example.kho_hang_xuong.Adapter.AdapterTaoDon;
import com.example.kho_hang_xuong.Adapter.Adapter_HD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.TaoDon_Activity;

import java.util.ArrayList;


public class Fragment_HD extends Fragment {

    public Fragment_HD() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    ArrayList<HoaDon> list;
    Adapter_HD adapter;
    HD_Dao hdDao;
    AppCompatButton add;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__h_d, container, false);
        recyclerView=view.findViewById(R.id.rcvHd);
        add=view.findViewById(R.id.addHD);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        hdDao = new HD_Dao(getActivity());
        list = hdDao.getAllHoaDonWithDetails();
        adapter = new Adapter_HD(getActivity(), list);
        recyclerView.setAdapter(adapter);

        // xử lý khi clich vào thêm hóa đơn
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TaoDon_Activity.class);
                startActivity(intent);
//                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
//                LayoutInflater layoutInflater= ((Activity)getActivity()).getLayoutInflater();
//                View view = inflater.inflate(R.layout.layout_tao_don,container,false);
//                builder.setView(view);
//                Dialog dialog = builder.create();
//                dialog.show();
//
//                // ánh xạ thành phần
//
//                RecyclerView recyclerView1= view.findViewById(R.id.taodon);
//
//                RadioGroup radioGroup;
//                RadioButton xuat,nhap;
//                TextView tong,hh,tp;
//                Button btnt;
//                // hiển thị sp
//               SP_Dao spDao= new SP_Dao(getActivity());
//               ArrayList<SanPham>  list=spDao.ALLSP();
//                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//                recyclerView1.setLayoutManager(linearLayoutManager);
//               AdapterTaoDon  adapterTaoDon=new AdapterTaoDon(getActivity(),list);
//                recyclerView1.setAdapter(adapterTaoDon);
//                adapterTaoDon.notifyDataSetChanged();
            }
        });
        return view;
    }
}