package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.MainActivity;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.Model.Uers;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.View.FragmentUers_Chitiet;

import java.util.ArrayList;

public class AdapterTV extends RecyclerView.Adapter<AdapterTV.ViewHoder> {

    private final Context context;
    private final ArrayList<Uers> list;
    TlDao tlDao;

    public AdapterTV(Context context, ArrayList<Uers> list) {
        this.context = context;
        this.list = list;
        tlDao= new TlDao(context);
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tv, parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
                 holder.name.setText(list.get(position).getFullName());
                 holder.sdt.setText(list.get(position).getEmail());

                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         MainActivity mainActivity=(MainActivity)context;
                         FragmentUers_Chitiet frg= new FragmentUers_Chitiet();
                         Bundle args = new Bundle();
                         args.putInt("idUer", list.get(position).getId());
                         frg.setArguments(args);
                         mainActivity.replec(frg);
                     }
                 });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name,sdt;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
             name=itemView.findViewById(R.id.TVname);
             sdt=itemView.findViewById(R.id.TVsdt);
        }
    }
}
