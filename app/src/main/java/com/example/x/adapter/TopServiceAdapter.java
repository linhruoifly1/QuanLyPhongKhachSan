package com.example.x.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.R;
import com.example.x.model.TopService;

import java.util.ArrayList;

public class TopServiceAdapter extends RecyclerView.Adapter<TopServiceAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<TopService> list;

    public TopServiceAdapter(Context context, ArrayList<TopService> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top_servive,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txttenDv.setText("Tên Dịch Vụ: "+ list.get(position).getTenDichVu());
        holder.txtsoLuot.setText("Số Lượt Sử Dụng:"+ list.get(position).getSoLuong());

    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenDv, txtsoLuot;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txttenDv = itemView.findViewById(R.id.txttenDv);
            txtsoLuot = itemView.findViewById(R.id.txtsoLuong);

        }
    }
}

