package com.example.x.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.x.model.Room;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewHolder>{
    private Context context;
    private ArrayList<Bill> arrayList;
    BillDAO billDAO;
    ServiceDAO serviceDAO;
    CustomerDAO customerDAO;
    ReceptionistDAO receptionistDAO;

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
        holder.tvCheckIn.setText(bill.getCheckIn());
        holder.tvCheckOut.setText(bill.getCheckOut());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdBill,tvCustomerBill,tvReceptionistBill,tvCheckIn,tvCheckOut,tvRealCheckOut,tvCostRoom,tvCostService,tvVAT,tvStatusBill,tvSumCost;
        ImageView imgRoomBill,imgDeleteBill,imgStatusBill;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBill = itemView.findViewById(R.id.tvIdBill);
            tvCustomerBill = itemView.findViewById(R.id.tvCustomerBill);
            tvReceptionistBill = itemView.findViewById(R.id.tvReceptionistBill);
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
