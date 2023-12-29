package com.example.kho_hang_xuong;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Adapter.AdapterTaoDon;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerSP;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerTheLoai;
import com.example.kho_hang_xuong.View.Fragment_HD;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaoDon_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterTaoDon adapterTaoDon;
    SP_Dao spDao;
    HD_Dao hdDao;
    ArrayList<SanPham> list;
    ArrayList<SanPham> list1;
    ArrayList<SanPham> list2= new ArrayList<>();
    TextView tong,hh,tp;
    Button btnt;
    int index=0;
    RadioGroup radioGroup;
    RadioButton xuat,nhap;
    int LoaiHD=-1;
    int id=-1;
    Spinner spinner;
    AppCompatEditText ngayy;
    String ngayhd;
    ImageView img;
    int tongTien=0;
    @SuppressLint({"MissingInflatedId", "WrongViewCast", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_don);
        recyclerView=findViewById(R.id.taodon);
        tong=findViewById(R.id.tvtong);
        btnt=findViewById(R.id.btnzTao);
        tp=findViewById(R.id.tvThuPhong);
        radioGroup=findViewById(R.id.chkGr);
        xuat=findViewById(R.id.radioButton);
        nhap=findViewById(R.id.radioButton2);
        spinner=findViewById(R.id.spinnerSP);
        ngayy=findViewById(R.id.edtNgay);
        img=findViewById(R.id.imgngay);


       ngayhd= String.valueOf(LocalDate.now());
       Log.i("dd","ngay"+ngayhd);
       String ngaydao=ngayhd.substring(8,10)+ngayhd.substring(4,8)+ngayhd.substring(0,4);
       ngayy.setText(ngaydao);
        Calendar calendar = Calendar.getInstance();
       img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatePickerDialog datePickerDialog = new DatePickerDialog(
                       TaoDon_Activity.this,
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                               Calendar selectedDate = Calendar.getInstance();
                               selectedDate.set(year, month, dayOfMonth);
                               Calendar currentDate = Calendar.getInstance();
                                   String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                   String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                                  ngayhd=  year+ "/" + thang + "/" + ngay;
                                  ngayy.setText(ngayhd.substring(8,10)+ngayhd.substring(4,8)+ngayhd.substring(0,4));
                           }
                       },
                       calendar.get(Calendar.YEAR),
                       calendar.get(Calendar.MONTH),
                       calendar.get(Calendar.DAY_OF_MONTH)
               );



               datePickerDialog.show();
           }
       });


        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String ten = sharedPreferences.getString("savedUsername", "");
        UersDao uersDao= new UersDao(TaoDon_Activity.this);
        id=uersDao.id(ten);
        hdDao=new HD_Dao(TaoDon_Activity.this);




        //  xu lý chk
        RadioGroup radioGroup = findViewById(R.id.chkGr);

//        if(xuat.isChecked()){
//            LoaiHD = 1;
//        }else if (nhap.isChecked()){
//            LoaiHD=0;
//        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton) {
                    LoaiHD = 1;
                    adapterTaoDon.setLoaiHoaDon(LoaiHD);
                } else if (checkedId == R.id.radioButton2) {
                    LoaiHD = 0;
                    adapterTaoDon.setLoaiHoaDon(LoaiHD);
                }
                Log.d("TaoDon_Activity", "LoaiHD: " + LoaiHD); // Thêm log để theo dõi giá trị LoaiHD
            }
        });
        spDao= new SP_Dao(TaoDon_Activity.this);
        list=spDao.ALLSP();



        AdapterSpinnerSP adapter1 = new AdapterSpinnerSP(this, list);
        spinner.setAdapter(adapter1);

        int[] selectedItemSpinner = {0};
        // lấy string từ spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham sp= new SanPham();
                selectedItemSpinner[0] = ((SanPham) spinner.getItemAtPosition(position)).getId_sp();
                sp= spDao.getSanPhamById(selectedItemSpinner[0]);
                boolean kt=false;
                for (SanPham item:list2){
                    if(item.getId_sp()==selectedItemSpinner[0]){
                        kt=true;
                        Toast.makeText(TaoDon_Activity.this, "Đã thêm sản phẩm ", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!kt){
                    list2.add(sp);
                    adapterTaoDon.notifyDataSetChanged();
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(TaoDon_Activity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterTaoDon=new AdapterTaoDon(TaoDon_Activity.this,list2,LoaiHD);
        recyclerView.setAdapter(adapterTaoDon);
        adapterTaoDon.setOnQuantityChangeListener(new AdapterTaoDon.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged() {
                int t = adapterTaoDon.getTongTatCa();
                tongTien=adapterTaoDon.getTongTien();
              list2= new ArrayList<>();
              list2= adapterTaoDon.getSelectedIds();
            }
        });

        // xử lý spinner
        //set spiner


        btnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nga=ngayy.getText().toString();

                String ngay=nga.substring(6) +"-"+nga.substring(3,5)+"-"+nga.substring(0,2);
                Toast.makeText(TaoDon_Activity.this, "Vui lòng chọn loại hóa đơn", Toast.LENGTH_SHORT).show();
                if(LoaiHD==-1){
                    Toast.makeText(TaoDon_Activity.this, "Vui lòng chọn loại hóa đơn", Toast.LENGTH_SHORT).show();
                }else {
                    HoaDon hd = new HoaDon();
                    hd.setId_hd(hdDao.getMaxHoaDonId());
                    hd.setId_user(id);
                    hd.setNgay(ngay);
                    hd.setLoai_hd(LoaiHD);

                    int tongtienn=0;
                    // lấy tổng tiền
                    for(SanPham sp:list2){
                        tongtienn +=sp.getDongia()*sp.getSl();
                    }
                    hd.setTongtien(tongtienn);
                    // Thay đổi dòng sau để sử dụng phương thức mới thêm hóa đơn và chi tiết
                    if (hdDao.addHoaDonAndChiTiet(hd, list2)) {
                        for (SanPham sp:list2){
                            int soHienTai=spDao.getSoLuongSP(sp.getId_sp());
                            if(LoaiHD==0){
                                // nhập
                                int capNhatSL=soHienTai+sp.getSl();
                                spDao.updateSoLuong(sp.getId_sp(),capNhatSL);
                            } else if (LoaiHD==1) {
                               // xuất
                                int capNhatSL=soHienTai-sp.getSl();
                                spDao.updateSoLuong(sp.getId_sp(),capNhatSL);
                            }

                        }
                        Toast.makeText(TaoDon_Activity.this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(TaoDon_Activity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e("TaoDon_Activity", "Thêm hóa đơn và chi tiết thất bại");
                        Toast.makeText(TaoDon_Activity.this, "Thêm hóa đơn và chi tiết thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                }
        });




    }
}