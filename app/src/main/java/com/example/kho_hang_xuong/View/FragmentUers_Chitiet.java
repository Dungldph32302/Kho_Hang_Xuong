package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.MainActivity;
import com.example.kho_hang_xuong.Model.Uers;
import com.example.kho_hang_xuong.R;


public class FragmentUers_Chitiet extends Fragment {


    public FragmentUers_Chitiet() {
        // Required empty public constructor
    }

UersDao uersDao;
    TextView tvname,tvuer,tvpass,tvrmai,tvchucvu;
    AppCompatButton xoa;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uers__chitiet, container, false);
        tvname=view.findViewById(R.id.tvfullname);
        tvuer=view.findViewById(R.id.tvusre);
        tvpass=view.findViewById(R.id.tvpass);
        tvrmai=view.findViewById(R.id.tvemail);
        tvchucvu=view.findViewById(R.id.tvchuc);
        xoa=view.findViewById(R.id.btnldelete);

        Bundle bundle= getArguments();
        int id= bundle.getInt("idUer");

        Log.i("id","id là "+id);
        uersDao= new UersDao(getActivity());
        Uers uers=uersDao.SelectUserById(id);
        tvname.setText("Họ tên : "+uers.getFullName());
        tvrmai.setText("Email : "+uers.getEmail());
        tvuer.setText("Tên đăng nhập : "+uers.getUserName());
        tvpass.setText("Password : "+uers.getPassword());
        if(uers.getChucvu()==0){
            tvchucvu.setText(" Chức vụ : Thủ kho ");
        }else {
            tvchucvu.setText(" Chức vụ : Admin");
        }
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Cảnh báo!");
                builder.setIcon(R.drawable.baseline_warning_24);
                builder.setMessage("Bạn có muốn xoá?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (uersDao.deleteUers(id)) {
                            MainActivity mainActivity=(MainActivity)getContext();
                            Fragment_TV frg= new Fragment_TV();
                            mainActivity.replec(frg);
                            Toast.makeText(getActivity(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Xoá thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getActivity(), "Huỷ xoá", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}