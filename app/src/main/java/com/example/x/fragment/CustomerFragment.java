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
import android.util.Patterns;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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
        Collections.reverse(arrayList);
        rcvCustomer.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new CustomerAdapter(getContext(),arrayList);
        rcvCustomer.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Collections.reverse(arrayList);
        fltCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert(customer);
            }
        });
        edSearchCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arrayList.clear();
                for (Customer customer1 : arrayList1){
                    if(customer1.getName().contains(charSequence.toString())){
                        arrayList.add(customer1);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
//                if(edNameCustomer.getText().length()==0||
//                        edPhoneCustomer.getText().length()==0||
//                        edEmailCustomer.getText().length()==0||
//                        edBirthCustomer.getText().length()==0){
//                    Snackbar.make(view, "Không bỏ trống", Snackbar.LENGTH_SHORT).show();
//
//                    return;
//                }
                if (edNameCustomer.getText().toString().equals("")){
//                    Snackbar.make(view,"Vui lòng nhập tên khách hàng", Snackbar.LENGTH_SHORT).show();
                    edNameCustomer.setError("Vui lòng nhập tên khách hàng");

                }
                if (edPhoneCustomer.getText().toString().equals("")){
//                    Snackbar.make(view,"Vui lòng nhập số điện thoại", Snackbar.LENGTH_SHORT).show();
                    edPhoneCustomer.setError("Vui lòng nhập số điện thoại");

                }
                if (edEmailCustomer.getText().toString().equals("")){
//                    Snackbar.make(view,"Vui lòng nhập Email", Snackbar.LENGTH_SHORT).show();
                    edEmailCustomer.setError("Vui lòng nhập Email");

                }
                if (edPhoneCustomer.getText().toString().equals("")){
//                    Snackbar.make(view,"Vui lòng nhập số điện thoại", Snackbar.LENGTH_SHORT).show();
                    edPhoneCustomer.setError("Vui lòng nhập số điện thoại");
                }
                if (edBirthCustomer.getText().toString().equals("")){
                    edBirthCustomer.setError("Vui lòng nhập năm sinh");
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
                if(!Patterns.PHONE.matcher(edPhoneCustomer.getText().toString()).matches()){
//                    Toast.makeText(getActivity(), "SĐT không hợp lệ", Toast.LENGTH_SHORT).show();
                    edPhoneCustomer.setError("SĐT không hợp lệ");
                    return;
                }
                String email = edEmailCustomer.getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    Toast.makeText(getActivity(), "email không hợp lệ", Toast.LENGTH_SHORT).show();
                    edEmailCustomer.setError("Email không hợp lệ");
                    return;
                }
                Calendar calendar = Calendar.getInstance();
                int yearNow = calendar.get(Calendar.YEAR);
                int birth = Integer.parseInt(edBirthCustomer.getText().toString());
                if((yearNow-birth)<18){
//                    Toast.makeText(getActivity(), "Năm sinh không hợp lệ", Toast.LENGTH_SHORT).show();
                    edBirthCustomer.setError("Năm sinh không hợp lệ");
                    return;
                }
                Customer customer1 = new Customer(edNameCustomer.getText().toString(),edPhoneCustomer.getText().toString(),email,birth);
                if(customerDAO.insert(customer1)){
                    arrayList.clear();
                    arrayList.addAll(customerDAO.getAll());
                    adapter.notifyDataSetChanged();
                    Collections.reverse(arrayList);
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