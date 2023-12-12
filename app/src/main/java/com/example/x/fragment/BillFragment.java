package com.example.x.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.AddBillActivity;
import com.example.x.DAO.BillDAO;
import com.example.x.DAO.CustomerDAO;
import com.example.x.DAO.ReceptionistDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.adapter.BillAdapter;
import com.example.x.adapter.CustomerAdapter;
import com.example.x.adapter.CustomerSpinnerAdapter;
import com.example.x.adapter.ServiceSpinnerAdapter;
import com.example.x.model.Bill;
import com.example.x.model.Customer;
import com.example.x.model.Receptionist;
import com.example.x.model.Room;
import com.example.x.model.Service;
import com.example.x.model.Type;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BillFragment extends Fragment {
    private RecyclerView rcvBill;
    private FloatingActionButton fltBill;
    private EditText edSearchBill;
    private ArrayList<Bill> arrayList;
    private ArrayList<Bill> arrayList1;
    private BillDAO billDAO;
    private BillAdapter adapter;
    String user;
    private static final int ADD_BILL_REQUEST_CODE = 1;
   ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        arrayList.clear();
                        arrayList.addAll(billDAO.getAll());
                        adapter.notifyDataSetChanged();
                        Collections.reverse(arrayList);
                    }
                }
            }
    );

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
        rcvBill.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new BillAdapter(getContext(), arrayList);
        rcvBill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Collections.reverse(arrayList);
        Collections.reverse(arrayList1);
        fltBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBillActivity.class);
                user = getActivity().getIntent().getStringExtra("user");
                intent.putExtra("user_bill", user);
                resultLauncher.launch(intent);
            }
        });
        edSearchBill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayList.clear();
                CustomerDAO customerDAO = new CustomerDAO(getContext());
                ArrayList<Customer> customers = customerDAO.getAll();
                ReceptionistDAO receptionistDAO = new ReceptionistDAO(getContext());
                ArrayList<Receptionist> receptionists = receptionistDAO.getAll();
                for (Bill bill : arrayList1) {
                    for (Customer customer: customers) {
                        if(customer.getName().contains(s.toString()) && bill.getIdCustomer()==customer.getId()){
                            arrayList.add(bill);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                for(Bill bill : arrayList1){
                    if(bill.getStatus()==0 && "chưa thanh toán".contains(s.toString())){
                        arrayList.add(bill);
                    }else if(bill.getStatus()==1 && "đã thanh toán".contains(s.toString())){
                        arrayList.add(bill);
                    }else if(bill.getStatus()==2 && "hủy".contains(s.toString())){
                        arrayList.add(bill);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}