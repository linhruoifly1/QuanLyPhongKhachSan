package com.example.x;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.x.DAO.BillDAO;
import com.example.x.DAO.CustomerDAO;
import com.example.x.DAO.ReceptionistDAO;
import com.example.x.DAO.RoomDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.adapter.BillAdapter;
import com.example.x.adapter.CustomerSpinnerAdapter;
import com.example.x.adapter.RoomSpinnerAdapter;
import com.example.x.adapter.ServiceSpinnerAdapter;
import com.example.x.model.Bill;
import com.example.x.model.Customer;
import com.example.x.model.Receptionist;
import com.example.x.model.Room;
import com.example.x.model.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddBillActivity extends AppCompatActivity {
    Spinner spinnerCustomer,spinnerService;
    EditText edCheckIn,edCheckOut;
    Button btnCancel,btnAdd;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private int idCustomer,idService,idReceptionist;
    private BillDAO billDAO;
    private ArrayList<Bill> arrayList;
    private BillAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
         spinnerCustomer = findViewById(R.id.spinnerCustomerAdd);
         spinnerService = findViewById(R.id.spinnerServiceAdd);
         edCheckIn = findViewById(R.id.edCheckIn);
         edCheckOut = findViewById(R.id.edCheckOut);
         btnAdd = findViewById(R.id.btnAddBillNew);
         btnCancel = findViewById(R.id.btnCancelBillNew);
         billDAO = new BillDAO(this);
         arrayList = billDAO.getAll();
         adapter = new BillAdapter(this,arrayList);
        //customer
        CustomerDAO customerDAO = new CustomerDAO(this);
        ArrayList<Customer> customerArrayList = customerDAO.getAll();
        CustomerSpinnerAdapter customerSpinnerAdapter = new CustomerSpinnerAdapter(this,customerArrayList);
        spinnerCustomer.setAdapter(customerSpinnerAdapter);
        // set adapter cho spinner service
        ServiceDAO serviceDAO = new ServiceDAO(this);
        ArrayList<Service> serviceArrayList = serviceDAO.getAll();
        ServiceSpinnerAdapter serviceSpinnerAdapter = new ServiceSpinnerAdapter(this,serviceArrayList);
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
        String username = getIntent().getStringExtra("user_bill");
        ReceptionistDAO receptionistDAO = new ReceptionistDAO(this);
        if (username==null){
            return;
        }
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBillActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                onBackPressed();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkout = edCheckOut.getText().toString();

                if (checkout.isEmpty()){
                    Toast.makeText(AddBillActivity.this, "không để trống"+checkout, Toast.LENGTH_SHORT).show();
                    return;
                }
                Bill bill1 = new Bill();
                bill1.setCheckIn(edCheckIn.getText().toString());
                bill1.setCheckOut(checkout);
                bill1.setIdReceptionist(idReceptionist);
                bill1.setIdService(idService);
                bill1.setIdCustomer(idCustomer);
                bill1.setStatus(0);
                bill1.setVAT(10);
                bill1.setCheckOut(edCheckOut.getText().toString());

                if(billDAO.insert(bill1)>0){
                    arrayList.clear();
                    arrayList.addAll(billDAO.getAll());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else{
                    Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}