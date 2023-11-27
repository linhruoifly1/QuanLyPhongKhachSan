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
    private ArrayList<TopService> arrayList;

    public TopServiceAdapter(Context context, ArrayList<TopService> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        TopService top = arrayList.get(position);
        holder.tvNameServiceTop.setText(top.getNameService());
        holder.tvQuantityService.setText(""+top.getQuantityService());
    }

    @Override
    public int getItemCount() {
        return arrayList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameServiceTop, tvQuantityService;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNameServiceTop = itemView.findViewById(R.id.tvNameServiceTop);
            tvQuantityService = itemView.findViewById(R.id.tvQuantityService);

        }
    }
}

