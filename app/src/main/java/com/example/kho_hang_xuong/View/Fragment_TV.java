package com.example.kho_hang_xuong.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kho_hang_xuong.Adapter.AdapterTV;
import com.example.kho_hang_xuong.Dao.UersDao;
import com.example.kho_hang_xuong.Model.Uers;
import com.example.kho_hang_xuong.R;

import java.util.ArrayList;

public class Fragment_TV extends Fragment {

    public Fragment_TV() {
        // Required empty public constructor
    }


    RecyclerView rcv;
    AdapterTV adapterTV;
    UersDao uersDao;
    ArrayList<Uers> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__t_v, container, false);
        rcv=view.findViewById(R.id.rcvTV);
        uersDao= new UersDao(getActivity());
        list=uersDao.SellecAllTT();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(layoutManager);
        adapterTV= new AdapterTV(getActivity(),list);
        rcv.setAdapter(adapterTV);

        return view;
    }
}