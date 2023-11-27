package com.example.x.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.x.DAO.HardBillDAO;
import com.example.x.DAO.RoomDAO;
import com.example.x.R;
import com.example.x.adapter.HardBillAdapter;
import com.example.x.adapter.RoomAdapter;
import com.example.x.model.HardBill;

import java.util.ArrayList;

public class HardBillFragment extends Fragment {
    private RecyclerView rcvHard;
    private EditText edSearchHard;
    private ArrayList<HardBill> arrayList;
    private HardBillDAO hardBillDAO;
    private HardBillAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_hard_bill, container, false);

       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvHard = view.findViewById(R.id.rcvHardBill);
        edSearchHard = view.findViewById(R.id.edSearchHardBill);
        hardBillDAO = new HardBillDAO(getContext());
        arrayList = hardBillDAO.getAll();
        rcvHard.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new HardBillAdapter(getContext(),arrayList);
        rcvHard.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}