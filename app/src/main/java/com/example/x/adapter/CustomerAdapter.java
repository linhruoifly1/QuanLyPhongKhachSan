package com.example.x.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.x.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.CustomerDAO;
import com.example.x.model.Customer;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.viewHolder>{
    private Context context;
    private ArrayList<Customer> arrayList;
    private CustomerDAO customerDAO;
    EditText edtnameCustomer;
    EditText edtphoneCustomer;
    EditText edtEmailCustomer ;
    EditText edtBirthCustomer ;
    Button btnUpdateCustomer ;
    Button btnCanleCustomer;

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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_customer,null);
        // ánh xạ

         edtnameCustomer = view.findViewById(R.id.edNameCustomerUp);
         edtphoneCustomer = view.findViewById(R.id.edPhoneCustomerUp);
         edtEmailCustomer = view.findViewById(R.id.edEmailCustomerUp);
         edtBirthCustomer = view.findViewById(R.id.edBirthCustomerUp);
        Button btnUpdateCustomer = view.findViewById(R.id.btnUpCustomer);
        Button btnCanleCustomer = view.findViewById(R.id.btnCancelCustomerUp);

        edtnameCustomer.setText(customer.getName());
        edtphoneCustomer.setText(customer.getPhone());
        edtEmailCustomer.setText(customer.getEmail());
        edtBirthCustomer.setText(String.valueOf(customer.getBirth()));


        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        btnCanleCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo mới đối tượng
                customerDAO = new CustomerDAO(context);
                            boolean test = true;
                            if (edtnameCustomer.getText().toString().equals("")){
                                edtnameCustomer.setError("Vui lòng nhập tên khách hàng");
                                test = false;
                            }
                            if (edtphoneCustomer.getText().toString().equals("")){
                                edtphoneCustomer.setError("Vui lòng nhập số điện thoại");
                                test = false;
                            }
                            if (edtEmailCustomer.getText().toString().equals("")){
                                edtEmailCustomer.setError("Vui lòng nhập Email");
                                test = false;
                            }
                            if (String.valueOf(edtBirthCustomer.getText()).equals("")){
                                edtBirthCustomer.setError("Vui lòng nhập năm sinh");
                                test = false;
                            }

                            if(edtphoneCustomer.getText().length()!=10){
                                edtphoneCustomer.setError("SĐT không hợp lệ");
                                test = false;
                            }
                            String email = edtEmailCustomer.getText().toString();
                            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                                edtEmailCustomer.setError("Email không hợp lệ");
                                test = false;
                            }

                            try{
                                int tuoi = Integer.parseInt(edtBirthCustomer.getText().toString());
                                if(tuoi<18){
                                    edtBirthCustomer.setError("Bạn không đủ tuổi đăng ký");
                                    test = false;
                                }
                            }catch (Exception e){
                                if (!edtBirthCustomer.getText().toString().equals(""))
                                    edtBirthCustomer.setError("Năm sinh phải là số");
                                test = false;
                            }

                            if(!test){
                                return;
                            }

                            customer.setName(edtnameCustomer.getText().toString());
                            customer.setPhone(edtphoneCustomer.getText().toString());
                            customer.setEmail(edtEmailCustomer.getText().toString());
                            customer.setBirth(Integer.parseInt(edtBirthCustomer.getText().toString()));
                            // Trong onClick() sau khi cập nhật thành công
                            if (customerDAO.update(customer)) {
                                arrayList.clear();
                                arrayList.addAll(customerDAO.getAll());
                                notifyDataSetChanged(); // Thông báo cho adapter về sự thay đổi trong tập dữ liệu
                                dialog.dismiss();
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
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
