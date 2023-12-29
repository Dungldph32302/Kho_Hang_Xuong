package com.example.kho_hang_xuong.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.ActivityHD_Chitiet;
import com.example.kho_hang_xuong.Activity_Tao_SP;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.MainActivity;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerTheLoai;
import com.example.kho_hang_xuong.TaoDon_Activity;
import com.example.kho_hang_xuong.View.Fragment_HD_ChiTiet;

import java.util.ArrayList;
import java.util.Calendar;

public class Adapter_HD extends RecyclerView.Adapter<Adapter_HD.ViewHoder>{
    private final Context context;
    private final ArrayList<HoaDon> list;
    public Adapter_HD(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }
    HD_Dao hdDao;
    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_hd2, parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        HoaDon hd=list.get(position);
        String ngay, thang,nam;
        // 2023-20-12
        if (list.get(position).getLoai_hd()==1){
              holder.loai.setText(String.valueOf("Hóa Đơn Xuất"+ list.get(position).getId_hd()));
        }else {
            holder.loai.setText(String.valueOf("Hóa Đơn Nhập"+ list.get(position).getId_hd()));
        }
        holder.tong.setText("Tổng : "+String.valueOf(list.get(position).getTongtien())+" VND");
        holder.ngay.setText("Ngày : "+list.get(position).getNgay());
        hdDao= new HD_Dao(context);
        String fullname=hdDao.getfullnamebyid(list.get(position).getId_user());
        holder.nguoiTao.setText("Người tạo: "+list.get(position).getFullName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHD_CT(hd);

            }
        });
        holder.tuyChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.popumenu, popupMenu.getMenu());

                // Bắt sự kiện khi một mục trong menu được chọn
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                            showDialogSua(list.get(position));
                        if (itemId == R.id.action_custom){


                        } else if (itemId == R.id.action_delete) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Cảnh Báo"); //set tieu de cho hop thoai
                            builder.setIcon(R.drawable.baseline_warning_24); //icon cho hop thoai
                            builder.setMessage("Bạn Có Muốn Xóa Không?"); //chuoi thong bao
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    int idhd=list.get(position).getId_hd();
                                    if(hdDao.deleteHD(idhd)){
                                        if(hdDao.deleteHDct(idhd)){
                                            list.clear();
                                            list.addAll(hdDao.getAllHoaDonWithDetails());
                                            notifyDataSetChanged();
                                            opendialogloi(" Xóa Thành Công ");
                                        }
                                    }
                                }
                            });
                            //nut no
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Không Xóa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            // tao va hien thi hop thoai
                            AlertDialog dialog = builder.create();// tao hop thoai
                            dialog.show();//hien thi hop thoai
                        }
                        return  false;
                    }
                });

                popupMenu.show();
            }
        });
    }
    private void goToHD_CT(HoaDon hd){
        Intent intent= new Intent(context,ActivityHD_Chitiet.class);
        Bundle bundle= new Bundle();
        bundle.putSerializable("hd",hd);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {

        TextView ngay, loai,tong,nguoiTao;

        ImageView tuyChinh;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            ngay=itemView.findViewById(R.id.TvNgay);
            loai=itemView.findViewById(R.id.TVloai);
            tong=itemView.findViewById(R.id.TvTong);
            nguoiTao=itemView.findViewById(R.id.Tvng);
            tuyChinh=itemView.findViewById(R.id.tvTuyChinh);
        }
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
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

    private void showDialogSua(HoaDon hd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_hd, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        AppCompatEditText  ngay;
        Button update,huy;
        RadioGroup radioGroup;
        RadioButton xuat,nhap;
        ImageView ngayy= view.findViewById(R.id.imgngay);
         EditText tien=view.findViewById(R.id.tien);


        radioGroup=view.findViewById(R.id.chkGr);
        xuat=view.findViewById(R.id.radioButton);
        nhap=view.findViewById(R.id.radioButton2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView ten = view.findViewById(R.id.Maid);
        ngay=view.findViewById(R.id.edtNgay);
        update=view.findViewById(R.id.btnLuu_sua);
        huy=view.findViewById(R.id.btnHuy_sua);


        ngay.setText(hd.getNgay());
        tien.setText(""+hd.getTongtien());
        ten.setText("Mã Hóa đơn "+hd.getId_hd());
        if(hd.getLoai_hd()==0){
            // nhập
            xuat.setChecked(true);
        }else {
            nhap.setChecked(true);
        }

//        if(xuat.isChecked()){
//            LoaiHD = 1;
//        }else if (nhap.isChecked()){
//            LoaiHD=0;
//        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    hd.setLoai_hd(1);
                } else if (checkedId == R.id.radioButton2) {
                    hd.setId_hd(0);
                }
                Log.d("TaoDon_Activity", "LoaiHD: " + hd.getLoai_hd()); // Thêm log để theo dõi giá trị LoaiHD
            }
        });



        ImageView imageView= view.findViewById(R.id.imgngay);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);
                                Calendar currentDate = Calendar.getInstance();
                                String ngayj = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                                String ngayhd=  year+ "/" + thang + "/" + ngayj;
                                ngay.setText(ngayhd.substring(8,10)+ngayhd.substring(4,8)+ngayhd.substring(0,4));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );



                datePickerDialog.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namesp=ten.getText().toString();
                String tientong=tien.getText().toString();
                String ngayy=ngay.getText().toString();
                    HoaDon hoaDon= new HoaDon();
                    hoaDon.setId_hd(hd.getId_hd());
                    hoaDon.setLoai_hd(hd.getLoai_hd());
                    hoaDon.setTongtien(Integer.parseInt(tientong));
                    hoaDon.setNgay(ngayy);
                    if(hdDao.updateHD(hoaDon)){
                        opendialogloi(" Cập nhật thành công ");
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
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
