package com.example.kho_hang_xuong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.View.Fragment_HD;
import com.example.kho_hang_xuong.View.Fragment_HD_ChiTiet;
import com.example.kho_hang_xuong.View.Fragment_SP;
import com.example.kho_hang_xuong.View.Fragment_TK;
import com.example.kho_hang_xuong.View.Fragment_TL;
import com.example.kho_hang_xuong.View.Fragment_TV;
import com.example.kho_hang_xuong.View.Login.FragmentTK;
import com.example.kho_hang_xuong.View.Login.FragmentTaoTk;
import com.example.kho_hang_xuong.View.Login.Login_Activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawable;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    UersDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawable=findViewById(R.id.drawerLayout);
        bottomNavigationView=findViewById(R.id.btnNavigation);
        navigationView=findViewById(R.id.navigationView);
        View v=navigationView.getHeaderView(0);
        TextView tvname=v.findViewById(R.id.welcomeName);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences",MODE_PRIVATE);
        String ten = sharedPreferences.getString("savedUsername", "");
        String savedPassword = sharedPreferences.getString("savedPassword", "");
        Intent intent = getIntent();
        if("dung".equals(intent.getStringExtra("kt"))){
            Fragment_HD frg= new Fragment_HD();
            replec(frg);
            bottomNavigationView.setSelectedItemId(R.id.hoaDon);
        }if("dung".equals(intent.getStringExtra("add"))){
            Fragment_SP frg= new Fragment_SP();
            replec(frg);
            bottomNavigationView.setSelectedItemId(R.id.hoaDon);
        }
        dao= new UersDao(MainActivity.this);
        String fullname=dao.name(ten);
        tvname.setText("Welcome "+ fullname);
        int quen=dao.quyen(ten);
        Log.i("xxxx","Quyen "+quen);
        if(quen==0){
            navigationView.getMenu().findItem(R.id.tk).setVisible(false);
        }


        toolbar.setTitle("Quản lý kho hàng ");
        // xử lý navigation


        ActionBarDrawerToggle drawerToggle=new ActionBarDrawerToggle(this,drawable,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawable.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // xửa lý khi chọn bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.hoaDon) {
                    Fragment_HD frg= new Fragment_HD();
                    replec(frg);
//                    drawerToggle.syncState();

                } else if (item.getItemId()==R.id.sanPham) {
                    Fragment_SP frg = new Fragment_SP();
                    replec(frg);

                } else if (item.getItemId()==R.id.thanhVien) {
                    Fragment_TV frg = new Fragment_TV();
                    replec(frg);
                } else if (item.getItemId()==R.id.thongtin) {
                    FragmentTK frg= new FragmentTK();
                    replec(frg);
                }
                return true;
            }
        });

        // xử lý khi chọn item navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.phieu){
                    Fragment_HD frg = new Fragment_HD();
                    replec(frg);
                } else if (item.getItemId()==R.id.SanPham) {
                    Fragment_SP frg = new Fragment_SP();
                    replec(frg);
                } else if (item.getItemId()==R.id.TheLoai) {
                    Fragment_TL frg = new Fragment_TL();
                    replec(frg);
                } else if (item.getItemId()==R.id.ThanhVien) {
                    Fragment_TV frg = new Fragment_TV();
                    replec(frg);
                } else if (item.getItemId()==R.id.TKXuat) {
                    Fragment_TK frg = new Fragment_TK();
                    replec(frg);
                }else if (item.getItemId()==R.id.TKNhap) {
                    Fragment_TK frg = new Fragment_TK();
                    replec(frg);
                }else if (item.getItemId()==R.id.tk) {
                    FragmentTaoTk frg = new FragmentTaoTk();
                    replec(frg);
                }else if (item.getItemId()==R.id.doimk) {
                    Fragment_HD frg = new Fragment_HD();
                    replec(frg);
                }else if (item.getItemId()==R.id.checkout) {
                    // Lấy SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

// Lấy Editor để chỉnh sửa dữ liệu
                    SharedPreferences.Editor editor = sharedPreferences.edit();

// Xóa dữ liệu đã lưu
                    editor.remove("savedUsername");
                    editor.remove("savedPassword");
                    Intent intent1= new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(intent1);
                }
                return true;
            }
        });

    }


    public void  replec(Fragment fragment){
        FragmentManager frg=getSupportFragmentManager();
        frg.beginTransaction().replace(R.id.frmHienthi,fragment).commit();
    }


}