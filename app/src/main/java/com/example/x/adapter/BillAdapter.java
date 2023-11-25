package com.example.x.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.BillDAO;
import com.example.x.DAO.CustomerDAO;
import com.example.x.DAO.ReceptionistDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.R;
import com.example.x.model.Bill;
import com.example.x.model.Customer;
import com.example.x.model.Receptionist;
import com.example.x.model.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewHolder>{
    private Context context;
    private ArrayList<Bill> arrayList;
    BillDAO billDAO;
    ServiceDAO serviceDAO;
    CustomerDAO customerDAO;
    ReceptionistDAO receptionistDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public BillAdapter(Context context, ArrayList<Bill> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        serviceDAO = new ServiceDAO(context);
        customerDAO = new CustomerDAO(context);
        receptionistDAO = new ReceptionistDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Bill bill = arrayList.get(position);
        holder.tvIdBill.setText(""+bill.getId());
        Receptionist receptionist = receptionistDAO.getId(String.valueOf(bill.getIdReceptionist()));
        holder.tvReceptionistBill.setText(receptionist.getName());
        Customer customer = customerDAO.getId(String.valueOf(bill.getIdCustomer()));
        holder.tvCustomerBill.setText(customer.getName());
        Service service = serviceDAO.getId(String.valueOf(bill.getIdService()));
        holder.tvServiceBill.setText(service.getName());
        String timeCheckIn = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        holder.tvCheckIn.setText(bill.getCheckIn()+" "+timeCheckIn+" UTC");
        holder.tvCheckOut.setText(bill.getCheckOut()+" 12:00:00 UTC");
        holder.tvCostRoom.setText(""+bill.getCostRoom());
        holder.tvCostService.setText("$ "+service.getPrice());
        holder.tvVAT.setText(bill.getVAT()+"%");
        int statusBill = bill.getStatus();
        if(statusBill==0){
            holder.tvStatusBill.setText("Chưa thanh toán");
            holder.tvStatusBill.setTextColor(Color.RED);
            holder.imgRoomBill.setVisibility(View.VISIBLE);
            holder.imgStatusBill.setVisibility(View.VISIBLE);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDiaLogUpdate(bill);
                    return true;
                }
            });
        }else{
            holder.tvStatusBill.setText("Đã thanh toán");
            holder.tvStatusBill.setTextColor(Color.BLUE);
            holder.imgRoomBill.setVisibility(View.INVISIBLE);
            holder.imgDeleteBill.setVisibility(View.INVISIBLE);
            holder.imgStatusBill.setVisibility(View.INVISIBLE);
        }
        holder.tvSumCost.setText(""+bill.getSumCost());
    }

    private void openDiaLogUpdate(Bill bill) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdBill,tvCustomerBill,tvReceptionistBill,tvCheckIn,tvCheckOut,tvRealCheckOut,tvCostRoom,tvCostService,tvVAT,tvStatusBill,tvSumCost,tvServiceBill;
        ImageView imgRoomBill,imgDeleteBill,imgStatusBill;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBill = itemView.findViewById(R.id.tvIdBill);
            tvCustomerBill = itemView.findViewById(R.id.tvCustomerBill);
            tvReceptionistBill = itemView.findViewById(R.id.tvReceptionistBill);
            tvServiceBill = itemView.findViewById(R.id.tvServiceBill);
            tvCheckIn = itemView.findViewById(R.id.tvCheckIn);
            tvCheckOut = itemView.findViewById(R.id.tvCheckOut);
            tvRealCheckOut = itemView.findViewById(R.id.tvRealCheckOut);
            tvCostRoom = itemView.findViewById(R.id.tvCostRoom);
            tvCostService = itemView.findViewById(R.id.tvCostService);
            tvVAT = itemView.findViewById(R.id.tvVAT);
            tvStatusBill = itemView.findViewById(R.id.tvStatusBill);
            tvSumCost = itemView.findViewById(R.id.tvSumCost);
            imgRoomBill = itemView.findViewById(R.id.imgRoomBill);
            imgDeleteBill = itemView.findViewById(R.id.imgDeleteBill);
            imgStatusBill = itemView.findViewById(R.id.imgStatusBill);
        }
    }
}
