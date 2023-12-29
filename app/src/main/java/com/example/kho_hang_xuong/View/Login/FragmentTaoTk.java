package com.example.kho_hang_xuong.View.Login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.Model.Uers;
import com.example.kho_hang_xuong.R;


public class FragmentTaoTk extends Fragment {


    public FragmentTaoTk() {
        // Required empty public constructor
    }



    UersDao dao;
    Button them;
    AppCompatEditText username,password,fullname,email;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_tao_tk, container, false);
        them=view.findViewById(R.id.btnthem);
        username=view.findViewById(R.id.edtUserName);
        password=view.findViewById(R.id.edtPassword);
        fullname=view.findViewById(R.id.edtEmail);
        email=view.findViewById(R.id.edtFullName);

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = username.getText().toString();
                String b = password.getText().toString();
                String c = email.getText().toString();
                String d = fullname.getText().toString();
                if(a.isEmpty()){
                    Toast.makeText(getActivity(), "Không để trống userName", Toast.LENGTH_SHORT).show();
                } else if (b.isEmpty()) {
                    Toast.makeText(getActivity(), "Không để trống password", Toast.LENGTH_SHORT).show();

                }else if (c.isEmpty()) {
                    Toast.makeText(getActivity(), "Không để trống email", Toast.LENGTH_SHORT).show();

                }else if (d.isEmpty()) {
                    Toast.makeText(getActivity(), "Không để trống fullName", Toast.LENGTH_SHORT).show();

                }else {
                    dao= new UersDao(getActivity());
                    Uers uers= new Uers();
                    uers.setChucvu(1);
                    uers.setFullName(d);
                    uers.setPassword(b);
                    uers.setEmail(c);
                    uers.setUserName(a);
                    if(dao.addThuThu(uers)){
                        Toast.makeText(getActivity(), "Tạo thành công", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                        email.setText("");
                        fullname.setText("");
                    }else {
                        Toast.makeText(getActivity(), "Thêm That bại nhập username mới ", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        return view;
    }
}