package com.example.x.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x.DAO.StatisticsDAO;
import com.example.x.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.SimpleFormatter;


public class RevenueFragment extends Fragment {

    int mYear, mDay, mMonth;
    StatisticsDAO statisticsDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        EditText edtStar = view.findViewById(R.id.edtStart);
        EditText edtEnd = view.findViewById(R.id.edtEnd);
        ImageView imgStar = view.findViewById(R.id.imgStart);
        ImageView imgEnd = view.findViewById(R.id.imgEnd);
        Button btnthongKe = view.findViewById(R.id.btnthongKeDoanhThu);
        TextView txtDoanhThu = view.findViewById(R.id.txtdoanhThu);
        statisticsDAO = new StatisticsDAO(getContext());

        //start
        DatePickerDialog.OnDateSetListener startDay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = mMonth;
                mDay = dayOfMonth;
                GregorianCalendar gregorianCalendar =new GregorianCalendar(mYear,mMonth,mDay);
                edtStar.setText(sdf.format(gregorianCalendar.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener endDay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar gregorianCalendar = new GregorianCalendar(mYear,mMonth,mDay);
                edtEnd.setText(sdf.format(gregorianCalendar.getTime()));
            }
        };
        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(),0,startDay,mYear,mMonth,mDay);
                dialog.show();
            }
        });

        imgEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), 0,endDay,mYear,mMonth,mDay);
                dialog.show();
            }
        });
        btnthongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = edtStar.getText().toString();
                String endDate = edtEnd.getText().toString();
                txtDoanhThu.setText("$ "+statisticsDAO.getRevenue(startDate,endDate));
            }
        });
        return  view;
    }
}