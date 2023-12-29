package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kho_hang_xuong.Activity_Tao_SP;
import com.example.kho_hang_xuong.Adapter.AdapterSanPham;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;


public class Fragment_SP extends Fragment {

    public Fragment_SP() {
        // Required empty public constructor
    }

    ArrayList<SanPham>list;
    AdapterSanPham adapterSanPham;
    RecyclerView rcv;
    SP_Dao spDao;

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__s_p, container, false);
        rcv=view.findViewById(R.id.rcvsp);
         AppCompatButton btn=view.findViewById(R.id.btnaddsp);
        spDao= new SP_Dao(getActivity());
        list=spDao.ALLSP();
        int spanCount = 2; // Số cột hoặc hàng trong lưới
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), spanCount);
        rcv.setLayoutManager(layoutManager);
        adapterSanPham= new AdapterSanPham(getActivity(),list);
        rcv.setAdapter(adapterSanPham);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_Tao_SP.class);
                startActivity(intent);
            }
        });
        return view;
    }
}