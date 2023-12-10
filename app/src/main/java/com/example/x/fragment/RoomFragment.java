package com.example.x.fragment;

import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.x.DAO.RoomDAO;
import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.adapter.RoomAdapter;
import com.example.x.adapter.TypeSpinnerAdapter;
import com.example.x.model.Room;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RoomFragment extends Fragment {
    private RecyclerView rcvRoom;
    private FloatingActionButton fltRoom;
    private EditText edSearchRoom;
    private RoomAdapter adapter;
    private RoomDAO roomDAO;
    private ArrayList<Room> arrayList;
    private ArrayList<Room> arrayList1;
    private Room room;
    private int idType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvRoom = view.findViewById(R.id.rcvRoom);
        fltRoom = view.findViewById(R.id.fltRoom);
        edSearchRoom = view.findViewById(R.id.edSearchRoom);
        roomDAO = new RoomDAO(getContext());
        arrayList = roomDAO.getAll();
        arrayList1 = roomDAO.getAll();
        rcvRoom.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new RoomAdapter(getContext(),arrayList);
        rcvRoom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        edSearchRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.clear();
                TypeDAO typeDAO = new TypeDAO(getContext());
                ArrayList<Type> types = typeDAO.getAll();
                for (Room room1 : arrayList1) {
                    for (Type type: types) {
                        if(type.getName().contains(s.toString()) && room1.getIdType()==type.getId()){
                            arrayList.add(room1);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                for (Room room1: arrayList1) {
                    if(room1.getStatus()==0 && "sẵn sàng".contains(s.toString())){
                        arrayList.add(room1);
                    }else if(room1.getStatus()==1 && "có khách".contains(s.toString())){
                        arrayList.add(room1);
                    }else if(room1.getStatus()==2 && "đang chờ".contains(s.toString())){
                        arrayList.add(room1);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        fltRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert(room);
            }
        });
    }

    private void openDiaLogInsert(Room room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.insert_room,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        Spinner spinnerType = view.findViewById(R.id.spinnerTypeAdd);
        EditText edNumberRoom = view.findViewById(R.id.edNumberRoomAdd);
        EditText edPriceRoom = view.findViewById(R.id.edPriceRoomAdd);
        Button btnAddRoom = view.findViewById(R.id.btnAddRoomNew);
        Button btnCancel = view.findViewById(R.id.btnCancelRoomNew);
        TypeDAO typeDAO = new TypeDAO(getContext());
        ArrayList<Type> types = typeDAO.getAll();
        TypeSpinnerAdapter spinnerAdapter = new TypeSpinnerAdapter(getContext(),types);
        spinnerType.setAdapter(spinnerAdapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idType = types.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Chọn loại phòng", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNumberRoom.getText().length()==0){
                    edNumberRoom.setError("Không bỏ trống số phòng");
                    return;
                }
                if(edPriceRoom.getText().length()==0){
                    edPriceRoom.setError("Không bỏ trống giá phòng");
                    return;
                }
                for(int i =0;i<arrayList.size();i++){
                    Room room1 = arrayList.get(i);
                    if(edNumberRoom.getText().toString().equals(String.valueOf(room1.getNumber()))){
                        Toast.makeText(getActivity(), "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Room room1 = new Room(idType,Integer.parseInt(edNumberRoom.getText().toString()),0,Integer.parseInt(edPriceRoom.getText().toString()));
                if(roomDAO.insert(room1)){
                    arrayList.clear();
                    arrayList.addAll(roomDAO.getAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}