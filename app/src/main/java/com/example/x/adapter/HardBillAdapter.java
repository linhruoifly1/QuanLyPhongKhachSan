package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.HardBillDAO;
import com.example.x.DAO.RoomDAO;
import com.example.x.R;
import com.example.x.model.HardBill;
import com.example.x.model.Room;

import java.util.ArrayList;

public class HardBillAdapter extends RecyclerView.Adapter<HardBillAdapter.viewHolder>{
    private Context context;
    private ArrayList<HardBill> arrayList;
    HardBillDAO hardBillDAO;
    RoomDAO roomDAO;

    public HardBillAdapter(Context context, ArrayList<HardBill> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        hardBillDAO = new HardBillDAO(context);
        roomDAO = new RoomDAO(context);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hardbill,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        HardBill hardBill = arrayList.get(position);
        holder.tvIdHard.setText(""+hardBill.getId());
        holder.tvIdBillHard.setText(""+hardBill.getIdBill());
        Room room = roomDAO.getId(String.valueOf(hardBill.getIdRoom()));
        holder.tvNumberRoomHard.setText(""+room.getNumber());
        holder.tvQuantityPeople.setText(""+hardBill.getQuantityPeople());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdHard, tvIdBillHard,tvNumberRoomHard,tvQuantityPeople;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdHard = itemView.findViewById(R.id.tvIdHard);
            tvIdBillHard = itemView.findViewById(R.id.tvIdBillHard);
            tvNumberRoomHard = itemView.findViewById(R.id.tvNumberRoomHard);
            tvQuantityPeople = itemView.findViewById(R.id.tvQuantityPeople);
        }
    }
}
