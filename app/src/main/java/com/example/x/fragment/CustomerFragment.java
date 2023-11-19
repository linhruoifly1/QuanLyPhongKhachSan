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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.x.DAO.CustomerDAO;
import com.example.x.R;
import com.example.x.adapter.CustomerAdapter;
import com.example.x.model.Customer;
import com.example.x.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomerFragment extends Fragment {
    private RecyclerView rcvCustomer;
    private FloatingActionButton fltCustomer;
    private EditText edSearchCustomer;
    private CustomerAdapter adapter;
    private CustomerDAO customerDAO;
    private ArrayList<Customer> arrayList;
    private ArrayList<Customer> arrayList1;
    private Customer customer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvCustomer = view.findViewById(R.id.rcvCustomer);
        fltCustomer = view.findViewById(R.id.fltCustomer);
        edSearchCustomer = view.findViewById(R.id.edSearchCustomer);
        customerDAO = new CustomerDAO(getContext());
        arrayList = customerDAO.getAll();
        arrayList1 = customerDAO.getAll();
        rcvCustomer.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new CustomerAdapter(getContext(),arrayList);
        rcvCustomer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fltCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert(customer);
            }
        });
    }

    private void openDiaLogInsert(Customer customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.insert_customer,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edNameCustomer = view.findViewById(R.id.edNameCustomerAdd);
        EditText edPhoneCustomer = view.findViewById(R.id.edPhoneCustomerAdd);
        EditText edEmailCustomer = view.findViewById(R.id.edEmailCustomerAdd);
        EditText edBirthCustomer = view.findViewById(R.id.edBirthCustomerAdd);
        Button btnAdd = view.findViewById(R.id.btnAddCustomer);
        Button btnCancel = view.findViewById(R.id.btnCancelCustomerAdd);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNameCustomer.getText().length()==0||
                        edPhoneCustomer.getText().length()==0||
                        edEmailCustomer.getText().length()==0||
                        edBirthCustomer.getText().length()==0){
                    Toast.makeText(getActivity(), "Không để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i =0;i<arrayList.size();i++){
                    Customer customer1 = arrayList.get(i);
                    if(edPhoneCustomer.getText().toString().equals(String.valueOf(customer1.getPhone()))
                            ||edEmailCustomer.getText().toString().equals(String.valueOf(customer1.getPhone()))){
                        Toast.makeText(getActivity(), "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if(edPhoneCustomer.getText().length()!=10){
                    Toast.makeText(getActivity(), "SĐT không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = edEmailCustomer.getText().toString();
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getActivity(), "email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                int yearNow = calendar.get(Calendar.YEAR);
                int birth = Integer.parseInt(edBirthCustomer.getText().toString());
                if((yearNow-birth)<18){
                    Toast.makeText(getActivity(), "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Customer customer1 = new Customer(edNameCustomer.getText().toString(),edPhoneCustomer.getText().toString(),email,birth);
                if(customerDAO.insert(customer1)){
                    arrayList.clear();
                    arrayList.addAll(customerDAO.getAll());
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