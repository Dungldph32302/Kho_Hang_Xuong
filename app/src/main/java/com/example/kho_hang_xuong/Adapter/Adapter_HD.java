package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class Adapter_HD extends RecyclerView.Adapter<Adapter_HD.ViewHoder>{
    private final Context context;
    private final ArrayList<HoaDon> list;

    SP_Dao spDao;
    ArrayList<SanPham> list1;

    public Adapter_HD(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hd, parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        if (list.get(position).getLoai_hd()==1){
              holder.loai.setText("Hóa Đơn Nhập");
        }else {
            holder.loai.setText("Hóa Đơn Xuất");
        }
        holder.ngay.setText(list.get(position).getNgay());

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.rcv.getContext());
        holder.rcv.setLayoutManager(layoutManager);
        spDao=new SP_Dao(context);
        list1=spDao.getSanPhamByHoaDonID(list.get(position).getId_hd());
        AdapterSanPhaminHD sanPhamAdapter = new AdapterSanPhaminHD(context,list1);
        holder.rcv.setAdapter(sanPhamAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {

        TextView ngay, loai,tong;
        RecyclerView rcv;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            ngay=itemView.findViewById(R.id.tvngayhd);
            loai=itemView.findViewById(R.id.tvloaihd);
            tong=itemView.findViewById(R.id.tvtongtien);
            rcv=itemView.findViewById(R.id.SanPham_HD);
        }
    }
}
