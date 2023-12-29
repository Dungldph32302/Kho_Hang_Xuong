package com.example.kho_hang_xuong.View.Login;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kho_hang_xuong.Adapter.AdapterSanPham;
import com.example.kho_hang_xuong.Adapter.Adapter_HD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.TKdao;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.TaoDon_Activity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;


public class FragmentTK extends Fragment {



    public FragmentTK() {
        // Required empty public constructor
    }


    TextView xuatnhap,tientonkho,textView1,textView2,textView3,textView4,textView5;
    TKdao tKdao;
    int soluongHDxuat=0,soluongHDnhap=0;
    Button thongke;
    EditText ngaybd,ngaykt;
    ImageView bd,kt;
    RecyclerView rcv;
    AdapterSanPham adapter;
    ArrayList<SanPham> list;
    @SuppressLint("NewApi")
    String ngay = String.valueOf(java.time.LocalDate.now());
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_t_k, container, false);
        xuatnhap=view.findViewById(R.id.txtXuatnhap);
        tientonkho=view.findViewById(R.id.txttienTon);
        rcv=view.findViewById(R.id.rcvTOp);
        textView1=view.findViewById(R.id.tv1);
        textView2=view.findViewById(R.id.tv2);
        textView3=view.findViewById(R.id.tv3);
        textView4=view.findViewById(R.id.tv4);
        textView5=view.findViewById(R.id.tv5);
        thongke=view.findViewById(R.id.btnThongKe);
        ngaybd=view.findViewById(R.id.edtNgaybatdau);
        ngaykt=view.findViewById(R.id.edtngay);
        bd=view.findViewById(R.id.imgngaybatdau);
        kt=view.findViewById(R.id.imgngay);

        tKdao= new TKdao(getContext());

        int soHDNhap= tKdao.getSlHdNhap();
        int soHDXuat=tKdao.getSlHdXuat();
        xuatnhap.setText(""+soHDNhap+"/"+soHDXuat);
        double tienTonKho=tKdao.getTienTonKho();
        tientonkho.setText(""+String.format("%2f",tienTonKho));

        // set top 10
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);

        list =tKdao.getTop10Products();
        adapter = new AdapterSanPham(getActivity(), list);
        Log.i("xzzzzz","list" +list.size());
        rcv.setAdapter(adapter);


        TabLayout tabLayout =view.findViewById(R.id.tabLayouthd);
        // sinh tablayout
        TabLayout.Tab tab1 = tabLayout.newTab().setText("Ngày");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab().setText("Thàng");
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab().setText("Năm");
        tabLayout.addTab(tab3);

        // xửa lý khi chọn tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabIndex = tab.getPosition();
                if (selectedTabIndex == 0) {


                    soluongHDnhap=tKdao.getSoLuongHoaDonNhapTheoNgay(ngay);
                    soluongHDxuat= tKdao.getSoLuongHoaDonXuatTheoNgay(ngay);
                    textView1.setText("Hóa đơn nhập : "+soluongHDnhap);
                    textView2.setText("Hóa đơn xuất : "+soluongHDxuat);
                } else if (selectedTabIndex == 1) {

                    soluongHDnhap=tKdao.getSoLuongHoaDonNhapTheoThang(ngay);
                    soluongHDxuat= tKdao.getSoLuongHoaDonXuatTheoThang(ngay);
                    textView1.setText("Hóa đơn nhập : "+soluongHDnhap);
                    textView2.setText("Hóa đơn xuất : "+soluongHDxuat);
                }else if(selectedTabIndex == 2){
                    soluongHDnhap=tKdao.getSoLuongHoaDonNhapTheoNam(ngay);
                    soluongHDxuat= tKdao.getSoLuongHoaDonXuatTheoNam(ngay);
                    Log.i("ddđ","xuat "+soluongHDxuat);
                    Log.i("ddđ","nhap "+soluongHDnhap);
                    textView1.setText("Hóa đơn nhập : "+soluongHDnhap);
                    textView2.setText("Hóa đơn xuất : "+soluongHDxuat);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        bd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);
                                Calendar currentDate = Calendar.getInstance();
                                String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                                String  ngayhd=  year+ "-" + thang + "-" + ngay;
                                ngaybd.setText(ngayhd);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );



                datePickerDialog.show();
            }
        });
        kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);
                                Calendar currentDate = Calendar.getInstance();
                                String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                              String  ngayhd=  year+ "-" + thang + "-" + ngay;
                            ngaykt.setText(ngayhd);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );



                datePickerDialog.show();
            }
        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double xuat=tKdao.getTongTienHoaDonXuatTrongKhoangThoiGian(ngaybd.getText().toString(),ngaykt.getText().toString());
                double nhap=tKdao.getTongTienHoaDonNhapTrongKhoangThoiGian(ngaybd.getText().toString(),ngaykt.getText().toString());

                textView3.setText("Doanh thu nhập : "+String.format("%.2f",nhap));
                textView4.setText("Doanh thu xuất : "+String.format("%.2f",xuat));
                textView5.setText("Lợi nhuận : "+String.format("%.2f",nhap-xuat));
            }
        });
        return view;
    }

}