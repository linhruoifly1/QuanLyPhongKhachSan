package com.example.x.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.BillDAO;
import com.example.x.DAO.HardBillDAO;
import com.example.x.DAO.RoomDAO;
import com.example.x.R;
import com.example.x.model.Bill;
import com.example.x.model.HardBill;
import com.example.x.model.Room;

import java.util.ArrayList;
import java.util.Collections;

public class HardBillAdapter extends RecyclerView.Adapter<HardBillAdapter.viewHolder>{
    private Context context;
    private ArrayList<HardBill> arrayList;
    HardBillDAO hardBillDAO;
    RoomDAO roomDAO;
    BillDAO billDAO;

    private int idRoom;

    public HardBillAdapter(Context context, ArrayList<HardBill> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        hardBillDAO = new HardBillDAO(context);
        roomDAO = new RoomDAO(context);
        billDAO = new BillDAO(context);
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
        Bill bill = billDAO.getId(String.valueOf(hardBill.getIdBill()));
        if(bill.getStatus()==0) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    updateHardBill(hardBill);
                    return false;
                }
            });
        }
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

    public void updateHardBill(HardBill hardBill){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_hardbill,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        // ánh xạ

        TextView txtidHard = view.findViewById(R.id.txtupdateIdHard);
        TextView txtidBillHard = view.findViewById(R.id.txtupdatebillHard);
        Spinner SpnNumberRoom = view.findViewById(R.id.spnNumberRoomUp);
        EditText edtQuantyPeople = view.findViewById(R.id.edtquantyPeopleUp);
        Button btnUphardBill = view.findViewById(R.id.btnUpHardBill);
        Button btnCancleHard = view.findViewById(R.id.btnCancleHardBillUp);

        txtidHard.setText("Mã: "+hardBill.getId());
        txtidBillHard.setText("Mã hóa đơn: "+hardBill.getIdBill());
        edtQuantyPeople.setText(String.valueOf(hardBill.getQuantityPeople()));
        roomDAO = new RoomDAO(context);
        ArrayList<Room> rooms = roomDAO.getAll();
        RoomSpinnerAdapter roomSpinnerAdapter = new RoomSpinnerAdapter(context,rooms);
        SpnNumberRoom.setAdapter(roomSpinnerAdapter);

        SpnNumberRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idRoom = rooms.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancleHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnUphardBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtQuantyPeople.getText().toString().equals("")){
                    edtQuantyPeople.setError("Vui lòng điền số lượng người");
                    return;
                }
                try {
                  int  edtQuantyPeople1 = Integer.parseInt(edtQuantyPeople.getText().toString());
                }catch (Exception e){
                    edtQuantyPeople.setError("Số lượng phải nhập số");
                    return;
                }
                hardBill.setIdRoom(idRoom);
                hardBill.setQuantityPeople(Integer.parseInt(edtQuantyPeople.getText().toString()));

                if (hardBillDAO.update(hardBill)){
                    arrayList.clear();
                    arrayList.addAll(hardBillDAO.getAll());
                    notifyDataSetChanged();
                    Collections.reverse(arrayList);
                    dialog.dismiss();
                    Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        });



    }
}
