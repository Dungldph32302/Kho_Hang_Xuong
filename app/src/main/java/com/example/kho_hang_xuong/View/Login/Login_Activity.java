package com.example.kho_hang_xuong.View.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.MainActivity;
import com.example.kho_hang_xuong.R;

public class Login_Activity extends AppCompatActivity {

    AppCompatEditText name,pass;
    CheckBox chk;
    AppCompatButton btn;
    UersDao uersDao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.appCompatEditText);
        pass=findViewById(R.id.editTextTextPassword);
        chk=findViewById(R.id.chkRemenber);
        btn=findViewById(R.id.btnlogin);
        uersDao= new UersDao(Login_Activity.this);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.edittext);
                name.setBackgroundDrawable(drawable);
            }
        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.edittext);
                pass.setBackgroundDrawable(drawable);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("savedUsername", "");
        String savedPassword = sharedPreferences.getString("savedPassword", "");
        name.setText(savedUsername);
        pass.setText(savedPassword);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable= ContextCompat.getDrawable(getApplicationContext(),R.drawable.edittext_loi);
                String n =name.getText().toString().trim();
                String p=pass.getText().toString().trim();
                if(n.isEmpty()){

                    name.setBackgroundDrawable(drawable);
                }
                if(p.isEmpty()){
                    pass.setBackgroundDrawable(drawable);
                }

                    if(uersDao.checkdangnhap(n,p)){
                        Intent intent= new Intent(Login_Activity.this, MainActivity.class);
                        intent.putExtra("usename",n);
                        startActivity(intent);
                        Toast.makeText(Login_Activity.this,"Đăng Nhập thành công", Toast.LENGTH_LONG).show();
                        if(chk.isChecked()){
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("savedUsername",n);
                            editor.putString("savedPassword",p);
                            editor.apply();
                        }
                    }else {
//                        Drawable errorBorder = getResources().getDrawable(R.drawable.eros);
//                        txtpassword.setBackground(errorBorder);
                        name.setBackgroundDrawable(drawable);
                        pass.setBackgroundDrawable(drawable);
                        Toast.makeText(Login_Activity.this," Đăng Nhập thất bại !", Toast.LENGTH_LONG).show();
                    }


            }
        });


    }
}