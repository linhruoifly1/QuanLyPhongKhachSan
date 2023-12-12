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
    private EditText edSearchType;
    private TypeAdapter adapter;
    private TypeDAO typeDAO;
    private ArrayList<Type> arrayList;
    private ArrayList<Type> arrayList1;
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
        edSearchType = view.findViewById(R.id.edSearchType);
        typeDAO = new TypeDAO(getContext());
        arrayList = typeDAO.getAll();
        arrayList1 = typeDAO.getAll();
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
        edSearchType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                for (Type type: arrayList1
                     ) {
                    if(type.getName().contains(charSequence.toString())){
                        arrayList.add(type);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                    edNameTypeAdd.setError("Không bỏ trống tên loại phòng");
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