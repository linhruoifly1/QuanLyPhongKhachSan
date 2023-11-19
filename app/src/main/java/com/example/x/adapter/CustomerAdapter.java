package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.CustomerDAO;
import com.example.x.R;
import com.example.x.model.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder>{
    private Context context;
    private ArrayList<Customer> arrayList;
    private CustomerDAO customerDAO;

    public CustomerAdapter(Context context, ArrayList<Customer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        customerDAO= new CustomerDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_customer,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Customer customer = arrayList.get(position);
        holder.tvIdCustomer.setText(""+customer.getId());
        holder.tvNameCustomer.setText(customer.getName());
        holder.tvPhoneCustomer.setText(customer.getPhone());
        holder.tvEmailCustomer.setText(customer.getEmail());
        holder.tvBirthCustomer.setText(""+customer.getBirth());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDiaLogUpdate(customer);
                return true;
            }
        });
    }

    private void openDiaLogUpdate(Customer customer) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdCustomer,tvNameCustomer,tvPhoneCustomer,tvEmailCustomer,tvBirthCustomer;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdCustomer = itemView.findViewById(R.id.tvIdCustomer);
            tvNameCustomer = itemView.findViewById(R.id.tvNameCustomer);
            tvPhoneCustomer = itemView.findViewById(R.id.tvPhoneCustomer);
            tvEmailCustomer = itemView.findViewById(R.id.tvEmailCustomer);
            tvBirthCustomer = itemView.findViewById(R.id.tvBirthCustomer);
        }
    }
}
