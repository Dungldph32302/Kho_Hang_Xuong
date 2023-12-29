package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kho_hang_xuong.Adapter.AdapterTL;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;


public class Fragment_TL extends Fragment {



    public Fragment_TL() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<TheLoai> list;
    AdapterTL adapterTL;
    TlDao tlDao;
    AppCompatButton add;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__t_l, container, false);
        recyclerView=view.findViewById(R.id.rcvTL);
        add=view.findViewById(R.id.addtl);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiathem();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tlDao = new TlDao(getActivity());
        list = tlDao.getAllTl();
        adapterTL = new AdapterTL(getActivity(), list);
        recyclerView.setAdapter(adapterTL);
        return view;
    }
    private void showDiathem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = ((Activity) getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.them_tl, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        AppCompatEditText ten;

        Button update,huy;

        update=view.findViewById(R.id.btnLuu_sua);
        huy=view.findViewById(R.id.btnHuy_sua);

        ten = view.findViewById(R.id.edtmota);

        //set spiner


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheLoai theLoai1= new TheLoai();
                String nam= ten.getText().toString();
                theLoai1.setName(nam);
                if(tlDao.addtl(theLoai1)){
                    list.clear();
                    list.addAll(tlDao.getAllTl());
                    adapterTL.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Câp nhật thành công", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}