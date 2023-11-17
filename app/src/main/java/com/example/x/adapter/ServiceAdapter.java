package com.example.x.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.R;
import com.example.x.model.Service;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewHolder>{
    private Context context;
    private ArrayList<Service> arrayList;

    public ServiceAdapter(Context context, ArrayList<Service> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Service service = arrayList.get(position);
        holder.tvIdService.setText(""+service.getId());
        holder.tvNameService.setText(service.getName());
        holder.tvPriceService.setText("$ "+service.getPrice());
        holder.tvPriceService.setTextColor(Color.BLUE);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDiaLogUpdate(service);
                return true;
            }
        });
    }

    private void openDiaLogUpdate(Service service) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdService,tvNameService,tvPriceService;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdService = itemView.findViewById(R.id.tvIdService);
            tvNameService = itemView.findViewById(R.id.tvNameService);
            tvPriceService = itemView.findViewById(R.id.tvPriceService);
        }
    }
}
