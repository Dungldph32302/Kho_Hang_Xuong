package com.example.kho_hang_xuong.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.example.kho_hang_xuong.Adapter.AdapterTL;
import com.example.kho_hang_xuong.Adapter.AdapterTaoDon;
import com.example.kho_hang_xuong.Adapter.Adapter_HD;
import com.example.kho_hang_xuong.Dao.HD_Dao;
import com.example.kho_hang_xuong.Dao.SP_Dao;
import com.example.kho_hang_xuong.Dao.TlDao;
import com.example.kho_hang_xuong.Model.HoaDon;
import com.example.kho_hang_xuong.Model.SanPham;
import com.example.kho_hang_xuong.Model.TheLoai;
import com.example.kho_hang_xuong.R;
import com.example.kho_hang_xuong.TaoDon_Activity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_HD extends Fragment {

    public Fragment_HD() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    ArrayList<HoaDon> list;
    Adapter_HD adapter;
    HD_Dao hdDao;
    AppCompatButton add;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__h_d, container, false);
        recyclerView = view.findViewById(R.id.rcvHd);
        add = view.findViewById(R.id.addHD);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        hdDao = new HD_Dao(getActivity());
        list = hdDao.getAllHoaDonByLoai(0);
        adapter = new Adapter_HD(getActivity(), list);
        recyclerView.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayouthd);
        // sinh tablayout
        TabLayout.Tab tab1 = tabLayout.newTab().setText(" Hóa đơn nhập ");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab().setText("Hóa đơn xuất");
        tabLayout.addTab(tab2);

        // xửa lý khi chọn tablayout
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabIndex = tab.getPosition();
                if (selectedTabIndex == 0) {
                    list.clear();
                    list.addAll(hdDao.getAllHoaDonByLoai(0));
                    adapter.notifyDataSetChanged();
                } else if (selectedTabIndex == 1) {
                    list.clear();
                    list.addAll(hdDao.getAllHoaDonByLoai(1));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // xử lý khi clich vào thêm hóa đơn
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaoDon_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.item_toolbar, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.menu_item2);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void handleSearch(String query) {
        ArrayList<HoaDon> listSearch = new ArrayList<>();
        for (HoaDon hoaDon : list) {
            if (hoaDon.getFullName().toLowerCase().contains(query.toLowerCase())) {
                listSearch.add(hoaDon);
            }
        }
        adapter = new Adapter_HD(getActivity(), listSearch);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
