package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kho_hang_xuong.Adapter.AdapterTL;
import com.example.kho_hang_xuong.Adapter.Adapter_HD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.HoaDon;
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
            }
        });
        return view;
    }
}