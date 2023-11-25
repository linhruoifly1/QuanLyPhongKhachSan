package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.R;
import com.example.x.model.HardBill;

import java.util.ArrayList;

public class HardBillAdapter extends RecyclerView.Adapter<HardBillAdapter.viewHolder>{
    private Context context;
    private ArrayList<HardBill> arrayList;

    public HardBillAdapter(Context context, ArrayList<HardBill> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hardbill,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
