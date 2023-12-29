
package com.example.kho_hang_xuong;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerTheLoai;
import com.example.kho_hang_xuong.View.Fragment_SP;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Activity_Tao_SP extends AppCompatActivity {


    AppCompatEditText edtTen,edtSl,edtMoTa,edtDonGia;
    AppCompatButton btnThem , btntl;
    Spinner spTl;
    SP_Dao spDao;
    String duongDanAnh;
    ImageView img;
    private final ActivityResultLauncher<Intent> moGalleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    xuLyKetQuaChonAnh(result);
                }
            }
    );
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_sp);
        edtTen=findViewById(R.id.edtTen);
        edtSl=findViewById(R.id.edtsl);
        edtMoTa=findViewById(R.id.edtmota);
        edtDonGia=findViewById(R.id.edtdongia);
        btnThem=findViewById(R.id.btnthem);
        btntl=findViewById(R.id.btnTL);
        img=findViewById(R.id.img_sp);
        spTl=findViewById(R.id.spn_TheLoai);
        spDao= new SP_Dao(this);

        btntl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Activity_Tao_SP.this,MainActivity.class);
                String h="dung";
                intent.putExtra("add",h);
                startActivity(intent);
            }
        });
        //set spiner
        TlDao theLoaiDao = new TlDao(this);
        ArrayList<TheLoai> list1=theLoaiDao.getAllTl();
        AdapterSpinnerTheLoai adapter1 = new AdapterSpinnerTheLoai(this, list1);
        spTl.setAdapter(adapter1);

        int[] selectedItemSpinner = {0};
        // lấy string từ spinner
        spTl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemSpinner[0] = ((TheLoai) spTl.getItemAtPosition(position)).getID();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnhTuGallery();
            }
        });
        // xử lý click thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten,donGia,soLuong,moTa;
                ten=edtTen.getText().toString();
                soLuong=edtSl.getText().toString();
                moTa=edtMoTa.getText().toString();
                donGia=edtDonGia.getText().toString();
                if(kiemtra(ten,soLuong,donGia,moTa)){
                    SanPham sp= new SanPham();
                    sp.setSoluong(Integer.parseInt(soLuong));
                    sp.setName(ten);
                    sp.setDongia(Integer.parseInt(donGia));
                    sp.setAnh(duongDanAnh);
                    int idtl=selectedItemSpinner[0];
                    sp.setId_tl(idtl);
                    themSP(sp);
                }
            }
        });
    }

    public  Boolean kiemtra(String ten ,String soluong,String dongia,String mota){
        if(ten.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(soluong.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập Số lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dongia.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập đơn giá", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(mota.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void themSP(SanPham sp){
        spDao= new SP_Dao(Activity_Tao_SP.this);
       if (spDao.addSP(sp)){
          opendialog("Bạn vừa thêm 1 Sản Phẩm mới ");
        }else {
           opendialogloi("Thêm Thất Bại");}
    }

    private void chonAnhTuGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        moGalleryLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }

    private void xuLyKetQuaChonAnh(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    // Chuyển đổi mảng byte thành chuỗi base64
                    duongDanAnh = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
  public void opendialog(String nd){
      AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Tao_SP.this);

      LayoutInflater inflater = ((Activity)this).getLayoutInflater();
      View view = inflater.inflate(R.layout.thong_bao,null);
      builder.setView(view);
      Dialog dialog = builder.create();
      dialog.show();
       TextView noidung=view.findViewById(R.id.TVnd);
      AppCompatButton btn=view.findViewById(R.id.btnok);
      noidung.setText(nd);

      btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
              Intent intent= new Intent(Activity_Tao_SP.this,MainActivity.class);
              String h="dung";
              intent.putExtra("add",h);
              startActivity(intent);

          }
      });

  }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    public void opendialogloi(String nd) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Tao_SP.this);

        // Sử dụng LayoutInflater.from(context) thay vì ((Activity)this).getLayoutInflater()
        LayoutInflater inflater = LayoutInflater.from(Activity_Tao_SP.this);
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
