package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class AdapterSanPhaminHD extends RecyclerView.Adapter<AdapterSanPhaminHD.ViewHoder> {

    private final Context context;
    private final ArrayList<SanPham> list;

    public AdapterSanPhaminHD(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sp_inhd, parent, false);
        return new ViewHoder(view);
    }

    String anhh;
    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.gia.setText(String.valueOf(list.get(position).getDongia()));
        holder.sl.setText(String.valueOf(list.get(position).getSl()));
        int sll = Integer.parseInt(String.valueOf(list.get(position).getSl()));
        int gia = Integer.parseInt(String.valueOf(list.get(position).getDongia()));
        holder.tong.setText(String.valueOf(sll * gia) + "  $");
        anhh = list.get(position).getAnh();
        if (anhh == null) {
        } else {
            byte[] decodedByteArray = Base64.decode(anhh, Base64.DEFAULT);

            // Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
            // Hiển thị Bitmap bằng Glide
            Glide.with(context)
                    .load(bitmap)
                    .into(holder.anh);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name, gia, sl, tong;
        ImageView anh;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            gia = itemView.findViewById(R.id.gia);
            sl = itemView.findViewById(R.id.sl);
            tong = itemView.findViewById(R.id.tong);
            anh=itemView.findViewById(R.id.imganh);
        }
    }
}
