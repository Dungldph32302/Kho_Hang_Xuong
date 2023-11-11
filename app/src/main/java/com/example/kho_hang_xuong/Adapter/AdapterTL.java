package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class AdapterTL  extends RecyclerView.Adapter<AdapterTL.ViewHoder> {

    private final Context context;
    private final ArrayList<TheLoai> list;
    TlDao tlDao;

    public AdapterTL(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        tlDao= new TlDao(context);
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tl, null);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
                 holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
             name=itemView.findViewById(R.id.tvNameTL);
        }
    }
}
