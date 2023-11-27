package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.R;
import com.example.x.model.TopRoom;

import java.util.ArrayList;

public class TopRoomAdapter extends RecyclerView.Adapter<TopRoomAdapter.viewHolder>{
    private Context context;
    private  ArrayList<TopRoom> arrayList;
    public TopRoomAdapter(Context context, ArrayList<TopRoom> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_room,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TopRoom top = arrayList.get(position);
        holder.tvNameTypeTop.setText(top.getNameType());
        holder.tvNumberRoomTop.setText(""+top.getNumberRoom());
        holder.tvQuantityRoom.setText(""+top.getQuantity());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvNameTypeTop,tvNumberRoomTop,tvQuantityRoom;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameTypeTop = itemView.findViewById(R.id.tvNameTypeTop);
            tvNumberRoomTop = itemView.findViewById(R.id.tvNumberRoomTop);
            tvQuantityRoom = itemView.findViewById(R.id.tvQuantityRoom);
        }
    }
}
