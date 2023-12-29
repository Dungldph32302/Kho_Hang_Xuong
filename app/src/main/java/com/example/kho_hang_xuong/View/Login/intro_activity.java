package com.example.kho_hang_xuong.View.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.kho_hang_xuong.R;

public class intro_activity extends AppCompatActivity {

AppCompatButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bt=findViewById(R.id.btnintrol);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intro_activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }
}