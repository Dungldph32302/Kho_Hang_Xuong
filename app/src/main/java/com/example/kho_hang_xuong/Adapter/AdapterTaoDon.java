package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterTaoDon extends RecyclerView.Adapter<AdapterTaoDon.Viewhoder>{
   private final Context context;
   private final ArrayList<SanPham>list;
    private int tongTatCa = 0;
    public AdapterTaoDon(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.tongTatCa = 0;
    }
    private OnQuantityChangeListener onQuantityChangeListener;
    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }


    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }


    // Phương thức này để trả về tổng của tất cả tongtien
    public int getTongTatCa() {
        int tong = 0;
        for (SanPham sanPham : list) {
            if (sanPham.isSelected()) {
                tong += Double.parseDouble(sanPham.getDonGia()) * sanPham.getSl();
            }
        }
        return tong;
    }
    // phương thức trả về id sp
    public Integer[] getSelectedIds() {
        List<Integer> selectedIdsList = new ArrayList<>();

        for (SanPham sanPham : list) {
            if (sanPham.isSelected()) {
                selectedIdsList.add(sanPham.getId_sp());
            }
        }

        return selectedIdsList.toArray(new Integer[0]);
    }



    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_sp2,parent,false);
        return new  Viewhoder(view);
    }
    int soluong=0;
    int kt=0;
    private boolean isLnVisible = false;
    int tongtien=0;
    int tong=0;

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.gia.setText(String.valueOf(list.get(position).getDonGia()));
        double gia = Double.parseDouble(list.get(position).getDonGia());

        if (isLnVisible) {
            tongtien += gia * soluong;
            // Cập nhật tổng tiền tại đây nếu bạn muốn
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLnVisible = !isLnVisible;
                Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.chon);
                Drawable background = ContextCompat.getDrawable(context, R.drawable.item_sp);

                if (isLnVisible) {
                    holder.ln.setVisibility(View.VISIBLE);
                    holder.itemView.setBackground(backgroundDrawable);
                    list.get(position).setSelected(true); // Đánh dấu item đã chọn
                } else {
                    holder.ln.setVisibility(View.INVISIBLE);
                    holder.itemView.setBackground(background);
                    list.get(position).setSelected(false); // Đánh dấu item không chọn
                }



                if(onQuantityChangeListener != null) {
                    onQuantityChangeListener.onQuantityChanged();
                }

                // Cập nhật tổng tiền của tất cả các item đã chọn khi item thay đổi trạng thái
                Toast.makeText(context, "Tổng tất cả " + getTongTatCa(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getSl() > 0 && list.get(position).isSelected()) {
                    list.get(position).setSl(list.get(position).getSl() - 1);
                    holder.so.setText(String.valueOf(list.get(position).getSl()));

                    // Thêm dòng log để kiểm tra giá trị của soLuong và tongTatCa
                    Log.d("AdapterTaoDon", "Số lượng sau khi trừ: " + list.get(position).getSl() + ", Tổng tất cả: " + getTongTatCa());

                    notifyDataSetChanged();
//                    if(onQuantityChangeListener != null) {
//                        onQuantityChangeListener.onQuantityChanged();
//                    }
                }
            }
        });

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setSl(list.get(position).getSl() + 1);
                holder.so.setText(String.valueOf(list.get(position).getSl()));
                // Thêm dòng log để kiểm tra giá trị của soLuong và tongTatCa
                Log.d("AdapterTaoDon", "Số lượng sau khi trừ: " + list.get(position).getSl() + ", Tổng tất cả: " + getTongTatCa());

                if(onQuantityChangeListener != null) {
                    onQuantityChangeListener.onQuantityChanged();
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewhoder extends RecyclerView.ViewHolder {
        TextView name,gia,tru,cong;
        TextView so;
        LinearLayout ln;
        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.TVNameSP);
            gia=itemView.findViewById(R.id.TvGiaSP);
            ln=itemView.findViewById(R.id.layouSL);
            tru=itemView.findViewById(R.id.tvtru);
            cong=itemView.findViewById(R.id.tvcong);
            so=itemView.findViewById(R.id.tvso);

        }
    }


}
