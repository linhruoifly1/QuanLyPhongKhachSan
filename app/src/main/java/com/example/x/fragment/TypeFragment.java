package com.example.x.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.adapter.TypeAdapter;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class    TypeFragment extends Fragment {
    private RecyclerView rcvType;
    private FloatingActionButton fltType;
    private TypeAdapter adapter;
    private TypeDAO typeDAO;
    private ArrayList<Type> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_type, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvType = view.findViewById(R.id.rcvType);
        fltType = view.findViewById(R.id.fltType);
        typeDAO = new TypeDAO(getContext());
        arrayList = typeDAO.getAll();
        rcvType.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new TypeAdapter(getContext(),arrayList);
        rcvType.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fltType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDiaLogInsert();
            }
        });
    }

    private void OpenDiaLogInsert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.insert_type,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edNameTypeAdd = view.findViewById(R.id.edNameTypeAdd);
        Button btnAddTypeNew = view.findViewById(R.id.btnAddTypeNew);
        Button btnCancelTypeNew = view.findViewById(R.id.btnCancelTypeNew);
        btnCancelTypeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddTypeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edNameTypeAdd.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                Type type = new Type();
                type.setName(name);
                type.setStatus(0);
                if(typeDAO.insert(type)>0){
                    arrayList.clear();
                    arrayList.addAll(typeDAO.getAll());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }
}