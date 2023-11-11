package com.example.kho_hang_xuong.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kho_hang_xuong.Adapter.AdapterTL;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;


public class Fragment_TL extends Fragment {



    public Fragment_TL() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<TheLoai> list;
    AdapterTL adapterTL;
    TlDao tlDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment__t_l, container, false);
        recyclerView=view.findViewById(R.id.rcvTL);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        tlDao = new TlDao(getActivity());
        list = tlDao.getAllTl();
        adapterTL = new AdapterTL(getActivity(), list);
        recyclerView.setAdapter(adapterTL);
        return view;
    }
}