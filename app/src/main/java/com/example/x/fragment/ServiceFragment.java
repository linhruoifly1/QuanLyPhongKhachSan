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

import com.example.x.DAO.RoomDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.adapter.ServiceAdapter;
import com.example.x.model.Customer;
import com.example.x.model.HardBill;
import com.example.x.model.Room;
import com.example.x.model.Service;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ServiceFragment extends Fragment {
    private RecyclerView rcvService;
    private FloatingActionButton fltService;
    private EditText edSearchService;
    private ServiceAdapter adapter;
    private ArrayList<Service> arrayList;
    private ArrayList<Service> arrayList1;
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
        edSearchService = view.findViewById(R.id.edSearchService);
        serviceDAO = new ServiceDAO(getContext());
        arrayList = serviceDAO.getAll();
        arrayList1 = serviceDAO.getAll();
        rcvService.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new ServiceAdapter(getContext(),arrayList);
        rcvService.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fltService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert();
            }
        });
        edSearchService.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                for (Service service1 : arrayList1){
                    if(service1.getName().contains(charSequence.toString())){
                        arrayList.add(service1);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void openDiaLogInsert() {
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
                if(edNameServiceAdd.getText().toString().equals("")){
                    edNameServiceAdd.setError("Vui lòng điền tên dịch vụ");
                    return;
                }
                if (edPriceServiceAdd.getText().toString().equals("")){
                    edPriceServiceAdd.setError("Vui lòng điền giá dịch vụ");
                    return;
                }
                try {
                    int price = Integer.parseInt(edPriceServiceAdd.getText().toString());
                }catch (Exception e){
                    edPriceServiceAdd.setError("Giá phải là số");
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
                service1.setStatus(0);
                if(serviceDAO.insert(service1)>0){
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