package com.example.kho_hang_xuong.Spiner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class AdapterSpinnerTheLoai extends BaseAdapter {
    private Context context;
    private ArrayList<TheLoai> list;

    public AdapterSpinnerTheLoai(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // khởi tạo và kiên kết layout
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();// sinh layout
        convertView = inflater.inflate(R.layout.tt_in_spinner, parent, false); // liên kết
        // ánh xạ thành phần từng thành phần trên  layout
        TextView txtName = convertView.findViewById(R.id.tvname);
        // điền dữ liệu
        txtName.setText(list.get(position).getName());

return convertView;
    }
}
