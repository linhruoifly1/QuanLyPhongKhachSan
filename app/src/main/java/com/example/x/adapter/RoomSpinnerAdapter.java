package com.example.x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.model.Room;
import com.example.x.model.Type;

import java.util.ArrayList;

public class RoomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Room> arrayList;

    public RoomSpinnerAdapter(Context context, ArrayList<Room> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //set view
        View view = LayoutInflater.from(context).inflate(R.layout.spinner_room,parent,false);
        TextView tvNumberRoom = view.findViewById(R.id.tvNumberRoomSpinner);
        TextView tvTypeRoom = view.findViewById(R.id.tvNameTypeRoomSpinner);
        TextView tvPriceRoom = view.findViewById(R.id.tvPriceRoomSpinner);
        Room room = arrayList.get(position);
        if(room != null){
            tvNumberRoom.setText(""+room.getNumber());
            TypeDAO typeDAO = new TypeDAO(context);
            Type type = typeDAO.getId(String.valueOf(room.getIdType()));
            tvTypeRoom.setText(type.getName());
            tvPriceRoom.setText("$ "+room.getPrice());
        }
        return view;
    }
}
