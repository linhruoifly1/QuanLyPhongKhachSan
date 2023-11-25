package com.example.x.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.x.DAO.BillDAO;
import com.example.x.DAO.CustomerDAO;
import com.example.x.DAO.ReceptionistDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.R;
import com.example.x.adapter.BillAdapter;
import com.example.x.adapter.CustomerAdapter;
import com.example.x.adapter.CustomerSpinnerAdapter;
import com.example.x.adapter.ServiceSpinnerAdapter;
import com.example.x.model.Bill;
import com.example.x.model.Customer;
import com.example.x.model.Receptionist;
import com.example.x.model.Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BillFragment extends Fragment {
    private RecyclerView rcvBill;
    private FloatingActionButton fltBill;
    private EditText edSearchBill;
    private ArrayList<Bill> arrayList;
    private ArrayList<Bill> arrayList1;
    private Bill bill;
    private BillDAO billDAO;
    private BillAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int idCustomer,idService,idReceptionist,costService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvBill = view.findViewById(R.id.rcvBill);
        fltBill = view.findViewById(R.id.fltBill);
        edSearchBill = view.findViewById(R.id.edSearchBill);
        billDAO = new BillDAO(getContext());
        arrayList = billDAO.getAll();
        arrayList1 = billDAO.getAll();
        rcvBill.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new BillAdapter(getContext(),arrayList);
        rcvBill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        fltBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDiaLogInsert(bill);
            }
        });
        edSearchBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void openDiaLogInsert(Bill bill) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.insert_bill,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Ánh xạ
        Spinner spinnerCustomer = view.findViewById(R.id.spinnerCustomerAdd);
        Spinner spinnerService = view.findViewById(R.id.spinnerServiceAdd);
        EditText edCheckIn = view.findViewById(R.id.edCheckIn);
        EditText edCheckOut = view.findViewById(R.id.edCheckOut);
        Button btnAdd = view.findViewById(R.id.btnAddBillNew);
        Button btnCancel = view.findViewById(R.id.btnCancelBillNew);
        // set adapter cho spinner customer
        CustomerDAO customerDAO = new CustomerDAO(getContext());
        ArrayList<Customer> customerArrayList = customerDAO.getAll();
        CustomerSpinnerAdapter customerSpinnerAdapter = new CustomerSpinnerAdapter(getContext(),customerArrayList);
        spinnerCustomer.setAdapter(customerSpinnerAdapter);
        // set adapter cho spinner service
        ServiceDAO serviceDAO = new ServiceDAO(getContext());
        ArrayList<Service> serviceArrayList = serviceDAO.getAll();
        ServiceSpinnerAdapter serviceSpinnerAdapter = new ServiceSpinnerAdapter(getContext(),serviceArrayList);
        spinnerService.setAdapter(serviceSpinnerAdapter);
        // customer
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCustomer = customerArrayList.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // service
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idService = serviceArrayList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // receptionist
        Intent intent = getActivity().getIntent();
        String username = intent.getStringExtra("user");
        ReceptionistDAO receptionistDAO = new ReceptionistDAO(getContext());
        Receptionist receptionist = receptionistDAO.getUsername(username);
        idReceptionist = receptionist.getId();
        // thêm giờ check in
        String checkIn = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new java.util.Date());
        edCheckIn.setText(checkIn);
        edCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        GregorianCalendar calendar = new GregorianCalendar(year,month,dayOfMonth);
                        edCheckOut.setText(sdf.format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill bill1 = new Bill(idCustomer,idReceptionist,idService,edCheckIn.getText().toString(),edCheckOut.getText().toString(),8,0);
                if(billDAO.insert(bill1)){
                    arrayList.clear();
                    arrayList.addAll(billDAO.getAll());
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