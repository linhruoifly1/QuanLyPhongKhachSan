package com.example.x.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.x.model.Room;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class HardBillFragment extends Fragment {
    private RecyclerView rcvHard;
    private EditText edSearchHard;
    private ArrayList<HardBill> arrayList;
    private ArrayList<HardBill> arrayList1;
    private HardBillDAO hardBillDAO;
    private HardBillAdapter adapter;

    private FloatingActionButton fab_add_bill;
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
        arrayList1 = hardBillDAO.getAll();
        rcvHard.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new HardBillAdapter(getContext(),arrayList);
        rcvHard.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Collections.reverse(arrayList);
        edSearchHard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                for(HardBill hardBill: arrayList1){
                    if(String.valueOf(hardBill.getIdBill()).contains(charSequence.toString())){
                        arrayList.add(hardBill);
                    }
                }
                adapter.notifyDataSetChanged();
                RoomDAO roomDAO = new RoomDAO(getContext());
                ArrayList<Room> rooms = roomDAO.getAll();
                for (HardBill hardBill : arrayList1) {
                    for (Room room: rooms) {
                        if(String.valueOf(room.getNumber()).contains(charSequence.toString()) && hardBill.getIdRoom()==room.getId()){
                            arrayList.add(hardBill);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}