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

public class AdapterSanPhaminHD extends RecyclerView.Adapter<AdapterSanPhaminHD.ViewHoder>{

    private final Context context;
    private final ArrayList<SanPham> list;

    public AdapterSanPhaminHD(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_sp_inhd,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.gia.setText(list.get(position).getDonGia());
        holder.sl.setText(String.valueOf(list.get(position).getSl()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name,gia,sl;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
             name=itemView.findViewById(R.id.name);
            gia=itemView.findViewById(R.id.gia);
            sl =itemView.findViewById(R.id.sl);
        }
    }
}
