package com.example.kho_hang_xuong.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerTheLoai;

import java.util.ArrayList;


public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.ViewHoder> {

    private final Context context;
    private final ArrayList<SanPham> list;
    private String anhh;
    SP_Dao spDao;

    public AdapterSanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sp, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        spDao = new SP_Dao(context);
        holder.name.setText(list.get(position).getName());
        holder.gia.setText(list.get(position).getDongia() + " VND");
        holder.sl.setText("SL : " + String.valueOf(list.get(position).getSoluong()));
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

        holder.tuyChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sp=list.get(position);
                    openchinhsua(sp,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name, gia, sl;
        ImageView anh, tuyChinh;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvname);
            gia = itemView.findViewById(R.id.tvgia);
            sl = itemView.findViewById(R.id.tvsl);
            anh = itemView.findViewById(R.id.img_sp);
            tuyChinh = itemView.findViewById(R.id.tvTuyChinh);
        }
    }

    public void openchinhsua(SanPham sp, View v) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.getMenuInflater().inflate(R.menu.popumenu, popupMenu.getMenu());

        // Bắt sự kiện khi một mục trong menu được chọn
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_custom) {
                    showDialogSua(sp);

                } else if (itemId == R.id.action_delete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Cảnh Báo"); //set tieu de cho hop thoai
                    builder.setIcon(R.drawable.baseline_warning_24); //icon cho hop thoai
                    builder.setMessage("Bạn Có Muốn Xóa Sản phẩm Này Không?"); //chuoi thong bao
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (spDao.deleteSP(sp.getId_sp())) {
                                list.clear();
                                list.addAll(spDao.ALLSP());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Không Xóa", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }


        });

        popupMenu.show();
    }

    @SuppressLint("MissingInflatedId")
    private void showDialogSua(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_sp, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        AppCompatEditText ten, soluong, dongia, mota;
        Spinner sptl;
        AppCompatButton btnUpadate ,huy=view.findViewById(R.id.btnhuy);
        ImageView anhupdate=view.findViewById(R.id.img_sp);


        ten = view.findViewById(R.id.edtTen);
        soluong = view.findViewById(R.id.edtsl);
        dongia = view.findViewById(R.id.edtdongia);
        mota = view.findViewById(R.id.edtmota);
        sptl = view.findViewById(R.id.spn_TheLoai);
        btnUpadate = view.findViewById(R.id.btnSua);

        ten.setText(sanPham.getName());
        soluong.setText(String.valueOf(sanPham.getSoluong()));
        dongia.setText(String.valueOf(sanPham.getDongia()));
        mota.setText("Mô tả ");

        //set spiner
        TlDao theLoaiDao = new TlDao(context);
        ArrayList<TheLoai> list1=theLoaiDao.getAllTl();
        AdapterSpinnerTheLoai adapter1 = new AdapterSpinnerTheLoai(context, list1);
        sptl.setAdapter(adapter1);

        int[] selectedItemSpinner = {0};
        // lấy string từ spinner
        sptl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemSpinner[0] = ((TheLoai) sptl.getItemAtPosition(position)).getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       String a = sanPham.getAnh();

        if (a == null) {

        } else {
            byte[] decodedByteArray = Base64.decode(a, Base64.DEFAULT);

            // Chuyển đổi mảng byte thành Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
            // Hiển thị Bitmap bằng Glide
            Glide.with(context)
                    .load(bitmap)
                    .into(anhupdate);
        }
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnUpadate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namesp=ten.getText().toString();
                String sl=soluong.getText().toString();
                String gia=dongia.getText().toString();
                String gt=mota.getText().toString();
               if(kt(namesp,sl,gia,gt)){
                   SanPham sanPham1= new SanPham();
                   sanPham1.setAnh(sanPham.getAnh());
                   sanPham1.setSl(Integer.parseInt(sl));
                   sanPham1.setName(namesp);
                   if(spDao.updateSP(sanPham1)){
                        opendialogloi(" Cập nhật thành công ");
                   }
                }
               dialog.dismiss();

            }
        });
    }
    public boolean kt(String a,String b,String c,String d){
        if(a.isEmpty()){
            opendialogloi("Không để trống tên sản phẩm ");
            return false;
        }
        if(b.isEmpty()){
            opendialogloi("Không để trống số lượng ");
            return false;
        }
        if(c.isEmpty()){
            opendialogloi("Không để trống đơn giá");
            return false;
        }
        if(d.isEmpty()){
            opendialogloi("Không để trống mô tả  ");
            return false;
        }
        return true;
    }
    public void opendialogloi(String nd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Sử dụng LayoutInflater.from(context) thay vì ((Activity)this).getLayoutInflater()
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.thong_bao, null);
        builder.setView(view);
        builder.setCancelable(false);
        Dialog dialog = builder.create();

        dialog.show();
        TextView noidung = view.findViewById(R.id.TVnd);
        AppCompatButton btn = view.findViewById(R.id.btnok);
        noidung.setText(nd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
