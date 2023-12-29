package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class AdapterTaoDon extends RecyclerView.Adapter<AdapterTaoDon.Viewhoder> {
    private final Context context;
    private final ArrayList<SanPham> list;
    private int tongTatCa = 0;
    private int loai;
    SP_Dao spDao;


    public AdapterTaoDon(Context context, ArrayList<SanPham> list, int loai) {
        this.context = context;
        this.list = list;
        this.tongTatCa = 0;
        this.loai=loai;
    }
     // Thêm biến loaiHoaDon

    public void setLoaiHoaDon(int loaiHoaDon) {
        this.loai = loaiHoaDon;
    }
    public int getTongTien() {
       return tongtien;
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
                tong++;
            }
        }
        return tong;
    }

    // phương thức trả về id sp
    public ArrayList<SanPham> getSelectedIds() {
        ArrayList<SanPham> list1 = new ArrayList<>();

        for (SanPham sanPham : list) {
            if (sanPham.isSelected()) {
                list1.add(sanPham);
            }
        }

        return list1;
    }


    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sp2, parent, false);
        return new Viewhoder(view);
    }

    int soluong = 0;
    int kt = 0;
    private boolean isLnVisible = false;
    int tongtien = 0;
    int tong = 0;

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        list.get(position).setSl(1);
        holder.name.setText(list.get(position).getName());
        holder.gia.setText(String.valueOf(list.get(position).getDongia()));
        double gia = list.get(position).getDongia();
        holder.so.setText(String.valueOf(list.get(position).getSl()));
        String anhh = list.get(position).getAnh();
        list.get(position).setSelected(true);

        if (anhh == null) {

        } else {
            byte[] decodedByteArray = Base64.decode(anhh, Base64.DEFAULT);

            // Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
            // Hiển thị Bitmap bằng Glide
            Glide.with(context)
                    .load(bitmap)
                    .into(holder.img);
        }

        if (isLnVisible) {
            tongtien += gia * soluong;
            // Cập nhật tổng tiền
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isLnVisible = !isLnVisible;
//                Drawable backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.chon);
//                Drawable background = ContextCompat.getDrawable(context, R.drawable.item_sp);
//
//                if (isLnVisible) {
//                    list.get(position).setSl(1);
//                    holder.so.setText(String.valueOf(list.get(position).getSl()));
//                    holder.ln.setVisibility(View.VISIBLE);
//                    holder.itemView.setBackground(backgroundDrawable);
//                    list.get(position).setSelected(true); // Đánh dấu item đã chọn
//                } else {
//                    holder.ln.setVisibility(View.INVISIBLE);
//                    holder.itemView.setBackground(background);
//                    list.get(position).setSelected(false); // Đánh dấu item không chọn
//                }
//
//
//
//                if(onQuantityChangeListener != null) {
//                    onQuantityChangeListener.onQuantityChanged();
//                }
//
//                // Cập nhật tổng tiền của tất cả các item đã chọn khi item thay đổi trạng thái
//                Toast.makeText(context, "Tổng tất cả " + getTongTatCa(), Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getSl() > 1) {
                    list.get(position).setSl(list.get(position).getSl() - 1);
                    holder.so.setText(String.valueOf(list.get(position).getSl()));
                    if (onQuantityChangeListener != null) {
                        onQuantityChangeListener.onQuantityChanged();
                    }
                }
            }
        });

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spDao = new SP_Dao(context);
                Log.i("xxx","Loại hóa đơn "+loai);
                if (loai == 1) { // Nếu loại hóa đơn là 1 (Xuất)
                    int soLuongTonKho = spDao.getSoLuongByID(list.get(position).getId_sp());

                    if (list.get(position).getSl() >= soLuongTonKho) {
                        // Số lượng sản phẩm đã đạt hoặc vượt quá số lượng tồn kho
                        tb("", "Số lượng sản phẩm vượt số lượng tồn kho hiện tại");
                    } else {
                        // Số lượng chưa vượt quá số lượng tồn kho, có thể cộng thêm
                        list.get(position).setSl(list.get(position).getSl() + 1);
                        holder.so.setText(String.valueOf(list.get(position).getSl()));
                        if (onQuantityChangeListener != null) {
                            onQuantityChangeListener.onQuantityChanged();
                        }
                    }
                } else { // Nếu loại hóa đơn không phải là 1 (Nhập) hoặc loại hóa đơn không được xác định
                    // Cộng số lượng mà không kiểm tra số lượng tồn kho
                    list.get(position).setSl(list.get(position).getSl() + 1);
                    holder.so.setText(String.valueOf(list.get(position).getSl()));
                    if (onQuantityChangeListener != null) {
                        onQuantityChangeListener.onQuantityChanged();
                    }
                }
            }
        });

        // xóa
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(list.get(position));
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Viewhoder extends RecyclerView.ViewHolder {
        TextView name, gia, tru, cong;
        TextView so;
        ImageView img,delete;
        LinearLayout ln;

        public Viewhoder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.TVNameSP);
            delete=itemView.findViewById(R.id.imgdelete);
            img = itemView.findViewById(R.id.img_sp);
            gia = itemView.findViewById(R.id.TvGiaSP);
            ln = itemView.findViewById(R.id.layouSL);
            tru = itemView.findViewById(R.id.tvtru);
            cong = itemView.findViewById(R.id.tvcong);
            so = itemView.findViewById(R.id.tvso);

        }
    }


    public void tb(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Thiết lập tiêu đề và nội dung của dialog
        builder.setTitle(title);
        builder.setMessage(message);

        // Thiết lập nút "OK" và xử lý sự kiện khi nút được nhấn
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý sự kiện khi nút "OK" được nhấn
                dialog.dismiss(); // Đóng dialog
            }
        });

        // Tạo và hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
