package com.example.kho_hang_xuong.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.Spiner.AdapterSpinnerTheLoai;

import java.util.ArrayList;

public class AdapterTL  extends RecyclerView.Adapter<AdapterTL.ViewHoder> {

    private final Context context;
    private final ArrayList<TheLoai> list;
    TlDao tlDao;

    public AdapterTL(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        tlDao= new TlDao(context);
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_tl, parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
                 holder.name.setText(list.get(position).getName());
                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                               showDialogSua(list.get(position));
                     }
                 });
                 holder.xoa.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         AlertDialog.Builder builder = new AlertDialog.Builder(context);
                         builder.setTitle("Cảnh Báo"); //set tieu de cho hop thoai
                         builder.setIcon(R.drawable.baseline_warning_24); //icon cho hop thoai
                         builder.setMessage("Bạn Có Muốn Xóa Thể Loại Này Không?"); //chuoi thong bao
                         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                                 if (tlDao.deletetl(list.get(position).getID())) {
                                     list.clear();
                                     list.addAll(tlDao.getAllTl());
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
                 });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHoder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView xoa;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
             name=itemView.findViewById(R.id.tvNameTL);
             xoa=itemView.findViewById(R.id.ImgDeleteTL);
        }
    }

    private void showDialogSua(TheLoai theLoai) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_tl, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        AppCompatEditText ten;

        Button update,huy;

        update=view.findViewById(R.id.btnLuu_sua);
        huy=view.findViewById(R.id.btnHuy_sua);

        ten = view.findViewById(R.id.edtmota);

        ten.setText(theLoai.getName());

        //set spiner


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namesp=ten.getText().toString();
               TheLoai theLoai1= new TheLoai();
               theLoai1.setID(theLoai.getID());
               theLoai1.setName(ten.getText().toString());
                    if(tlDao.updateSP(theLoai1)){
                        list.clear();
                        list.addAll(tlDao.getAllTl());
                        Toast.makeText(context, "Câp nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                dialog.dismiss();
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
