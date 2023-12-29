package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Adapter.Adapter_HD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.TaoDon_Activity;

import java.util.ArrayList;


public class Fragment_HD_ChiTiet extends Fragment {

    public Fragment_HD_ChiTiet() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    ArrayList<HoaDon> list;
    Adapter_HD adapter;
    private HoaDon hoaDon;
    TextView loai,ngTao,soLuong,tong;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__hd_chitiet, container, false);
     //   recyclerView=view.findViewById(R.id.rcvHd);


        return view;
    }
}