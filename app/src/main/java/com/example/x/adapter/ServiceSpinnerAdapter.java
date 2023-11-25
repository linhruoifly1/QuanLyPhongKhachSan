package com.example.x.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.x.R;
import com.example.x.model.Service;

import java.util.ArrayList;

public class ServiceSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Service> arrayList;

    public ServiceSpinnerAdapter(Context context, ArrayList<Service> arrayList) {
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
        Service service = arrayList.get(position);
        if(service != null){
            tvItem.setText(""+service.getName());
        }
        return view;
    }
}
