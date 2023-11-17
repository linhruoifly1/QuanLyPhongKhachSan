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

import com.example.x.DAO.ServiceDAO;
import com.example.x.R;
import com.example.x.adapter.ServiceAdapter;
import com.example.x.model.Service;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {
    private RecyclerView rcvService;
    private FloatingActionButton fltService;
    private ServiceAdapter adapter;
    private ArrayList<Service> arrayList;
    private Service service;
    private ServiceDAO serviceDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvService = view.findViewById(R.id.rcvService);
        fltService = view.findViewById(R.id.fltService);
        serviceDAO = new ServiceDAO(getContext());
        arrayList = serviceDAO.getAll();
        rcvService.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new ServiceAdapter(getContext(),arrayList);
        rcvService.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fltService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert(service);
            }
        });
    }

    private void openDiaLogInsert(Service service) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.insert_service,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edNameServiceAdd = view.findViewById(R.id.edNameServiceAdd);
        EditText edPriceServiceAdd = view.findViewById(R.id.edPriceServiceAdd);
        Button btnAddServiceNew = view.findViewById(R.id.btnAddServiceNew);
        Button btnCancelServiceNew = view.findViewById(R.id.btnCancelServiceNew);
        btnCancelServiceNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAddServiceNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNameServiceAdd.getText().length()==0 || edPriceServiceAdd.getText().length()==0){
                    Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i =0;i<arrayList.size();i++){
                    Service service1 = arrayList.get(i);
                    if(edNameServiceAdd.getText().toString().equals(service1.getName())){
                        Toast.makeText(getActivity(), "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Service service1 = new Service(edNameServiceAdd.getText().toString(),Integer.parseInt(edPriceServiceAdd.getText().toString()));
                if(serviceDAO.insert(service1)){
                    arrayList.clear();
                    arrayList.addAll(serviceDAO.getAll());
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