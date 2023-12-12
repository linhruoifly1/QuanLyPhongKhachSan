package com.example.x.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.RoomDAO;
import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.model.Room;
import com.example.x.model.Type;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.viewHolder>{
    private Context context;
    private ArrayList<Room> arrayList;
    RoomDAO roomDAO;
    private int idType;

    public RoomAdapter(Context context, ArrayList<Room> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        roomDAO = new RoomDAO(context);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Room room = arrayList.get(position);
        holder.tvIdRoom.setText(""+room.getId());
        TypeDAO typeDAO = new TypeDAO(context);
        Type type = typeDAO.getId(String.valueOf(room.getIdType()));
        holder.tvNameTypeRoom.setText(type.getName());
        holder.tvNumberRoom.setText(""+room.getNumber());
        int status = room.getStatus();
        if(status==0){
            holder.tvStatusRoom.setText("Sẵn sàng");
            holder.tvStatusRoom.setTextColor(Color.GREEN);
            holder.imgClean.setVisibility(View.INVISIBLE);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDiaLogUpdate(room);
                    return true;
                }
            });
        }else if(status==1){
            holder.tvStatusRoom.setText("Có khách");
            holder.tvStatusRoom.setTextColor(Color.RED);
            holder.imgClean.setVisibility(View.INVISIBLE);
        }else if(status==2){
            holder.tvStatusRoom.setText("Đang chờ");
            holder.tvStatusRoom.setTextColor(Color.YELLOW);
            holder.imgClean.setVisibility(View.VISIBLE);
        }
        holder.tvPriceRoom.setText("$ "+room.getPrice());
        holder.tvPriceRoom.setTextColor(Color.BLUE);
        holder.imgClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo !!!");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Phòng đã được dọn xong?");
                builder.setCancelable(false);
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(roomDAO.changeOnStatus(room.getId())){
                            arrayList.clear();
                            arrayList.addAll(roomDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Phòng đã sẵn sàng", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Hủy bỏ", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void openDiaLogUpdate(Room room) {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.update_room,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        Spinner spinnerType = view.findViewById(R.id.spinnerTypeUpdate);
        TextView tv_id = view.findViewById(R.id.tv_roomID);
        EditText edNumberRoom = view.findViewById(R.id.edNumberRoomUpdate);
        EditText edPriceRoom = view.findViewById(R.id.edPriceRoomUpdate);
        Button btnUpdateRoom = view.findViewById(R.id.btnUpdateRoomNew);
        Button btnCancel = view.findViewById(R.id.btnCancelRoomUpdate);
        edNumberRoom.setText(""+room.getNumber());
        edPriceRoom.setText(""+room.getPrice());
        tv_id.setText("Mã phòng: "+room.getId());
        TypeDAO typeDAO = new TypeDAO(context);
        ArrayList<Type> types = typeDAO.getAll();
        TypeSpinnerAdapter typeSpinnerAdapter = new TypeSpinnerAdapter(context,types);
        spinnerType.setAdapter(typeSpinnerAdapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idType = types.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpdateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNumberRoom.getText().toString().equals("")){
                    edNumberRoom.setError("Vui lòng nhập số phòng");
                    return;
                }
                if (edPriceRoom.getText().toString().equals("")){
                    edPriceRoom.setError("Vui lòng nhập giá");
                    return;
                }
//                for(int i =0;i<arrayList.size();i++){
//                    Room room1 = arrayList.get(i);
//                    if(edNumberRoom.getText().toString().equals(String.valueOf(room1.getNumber()))){
//                        Toast.makeText(context, "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
                room.setIdType(idType);
                room.setNumber(Integer.parseInt(edNumberRoom.getText().toString()));
                room.setStatus(0);
                room.setPrice(Integer.parseInt(edPriceRoom.getText().toString()));
                if(roomDAO.update(room)){
                    arrayList.clear();
                    arrayList.addAll(roomDAO.getAll());
                    notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdRoom,tvNameTypeRoom,tvNumberRoom,tvStatusRoom,tvPriceRoom;
        ImageView imgClean;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdRoom = itemView.findViewById(R.id.tvIdRoom);
            tvNameTypeRoom = itemView.findViewById(R.id.tvNameTypeRoom);
            tvNumberRoom = itemView.findViewById(R.id.tvNumberRoom);
            tvStatusRoom = itemView.findViewById(R.id.tvStatusRoom);
            tvPriceRoom = itemView.findViewById(R.id.tvPriceRoom);
            imgClean = itemView.findViewById(R.id.archive_room);
        }
    }
}
