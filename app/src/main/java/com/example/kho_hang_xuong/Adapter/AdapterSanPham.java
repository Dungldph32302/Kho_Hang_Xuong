package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class AdapterSanPham  extends RecyclerView.Adapter<AdapterSanPham.ViewHoder>{

    private final Context context;
    private final ArrayList<SanPham> list;

    public AdapterSanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_sp,null);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
               holder.name.setText(list.get(position).getName());
        holder.tloai.setText(String.valueOf(list.get(position).getId_tl()));
        holder.gia.setText(list.get(position).getDonGia());
        holder.sl.setText(String.valueOf(list.get(position).getSoluong()));
        holder.mota.setText(list.get(position).getMoTa());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name,tloai,gia,sl,mota;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
             name=itemView.findViewById(R.id.name);
            tloai=itemView.findViewById(R.id.tloai);
            gia=itemView.findViewById(R.id.gia);
            sl =itemView.findViewById(R.id.sl);
            mota=itemView.findViewById(R.id.mota);
        }
    }
}
