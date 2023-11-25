package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.x.R;
import com.example.x.model.Customer;

import java.util.ArrayList;

public class CustomerSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Customer> arrayList;

    public CustomerSpinnerAdapter(Context context, ArrayList<Customer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //set view
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_spiner,parent,false);
        TextView tvItem = view.findViewById(R.id.tvNameSpinner);
        Customer customer = arrayList.get(position);
        if(customer != null){
            tvItem.setText(customer.getName());
        }
        return view;
    }
}
