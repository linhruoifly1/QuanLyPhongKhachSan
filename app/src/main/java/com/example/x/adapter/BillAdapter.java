package com.example.x.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.x.DAO.BillDAO;
import com.example.x.DAO.CustomerDAO;
import com.example.x.DAO.HardBillDAO;
import com.example.x.DAO.ReceptionistDAO;
import com.example.x.DAO.RoomDAO;
import com.example.x.DAO.ServiceDAO;
import com.example.x.MainActivity;
import com.example.x.R;
import com.example.x.model.Bill;
import com.example.x.model.Customer;
import com.example.x.model.HardBill;
import com.example.x.model.Receptionist;
import com.example.x.model.Room;
import com.example.x.model.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.viewHolder>{
    private Context context;
    private ArrayList<Bill> arrayList;
    private BillDAO billDAO;
    private ServiceDAO serviceDAO;
    private CustomerDAO customerDAO;
    private ReceptionistDAO receptionistDAO;
    private RoomDAO roomDAO;
    private HardBillDAO hardBillDAO;
    private int idRoom;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public BillAdapter(Context context, ArrayList<Bill> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        billDAO = new BillDAO(context);
        serviceDAO = new ServiceDAO(context);
        customerDAO = new CustomerDAO(context);
        receptionistDAO = new ReceptionistDAO(context);
        roomDAO = new RoomDAO(context);
        hardBillDAO = new HardBillDAO(context);
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
        Service service = serviceDAO.getId(String.valueOf(bill.getIdService()));
        holder.tvServiceBill.setText(service.getName());
        // lấy ngày giờ lúc tọa hóa đơn

        holder.tvCheckIn.setText(bill.getCheckIn()+" 14:00 UTC");
        holder.tvCheckOut.setText(bill.getCheckOut()+" 12:00 UTC");
        int costService = service.getPrice();
        holder.tvCostService.setText("$ "+costService);
        int statusBill = bill.getStatus();
        if(statusBill==0){
            holder.tvStatusBill.setText("Chưa thanh toán");
            holder.tvStatusBill.setTextColor(Color.RED);
            holder.imgRoomBill.setVisibility(View.VISIBLE);
            holder.imgDeleteBill.setVisibility(View.VISIBLE);
            holder.imgStatusBill.setVisibility(View.VISIBLE);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDiaLogUpdate(bill);
                    return true;
                }
            });
        }else if(statusBill==1){
            holder.tvStatusBill.setText("Đã thanh toán");
            holder.tvStatusBill.setTextColor(Color.BLUE);
            holder.imgRoomBill.setVisibility(View.INVISIBLE);
            holder.imgDeleteBill.setVisibility(View.INVISIBLE);
            holder.imgStatusBill.setVisibility(View.INVISIBLE);
        }else{
            holder.tvStatusBill.setText("Huỷ");
            holder.tvStatusBill.setTextColor(Color.YELLOW);
            holder.imgRoomBill.setVisibility(View.INVISIBLE);
            holder.imgDeleteBill.setVisibility(View.INVISIBLE);
            holder.imgStatusBill.setVisibility(View.INVISIBLE);
        }
        // Chọn thêm phòng trong hóa đơn, có thể chọn nhiều phòng
        holder.imgRoomBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.insert_hardbill,null);
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                Spinner spinnerRoomAdd = view.findViewById(R.id.spinnerRoomAdd);
                EditText edQuantityPeople = view.findViewById(R.id.edQuantityPeople);
                Button btnAdd = view.findViewById(R.id.btnAddHardNew);
                Button btnCancel = view.findViewById(R.id.btnCancelHardNew);
                ArrayList<Room> roomArrayList = roomDAO.getReadyRoom();
                RoomSpinnerAdapter roomSpinnerAdapter = new RoomSpinnerAdapter(context,roomArrayList);
                spinnerRoomAdd.setAdapter(roomSpinnerAdapter);
                spinnerRoomAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idRoom = roomArrayList.get(position).getId();
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
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<HardBill> hardBillArrayList = hardBillDAO.getAll();
                        HardBillAdapter hardBillAdapter = new HardBillAdapter(context,hardBillArrayList);
                        if(edQuantityPeople.getText().length()==0){
                            edQuantityPeople.setError("Nhập số khách hàng");
                            return;
                        }
                        HardBill hardBill = new HardBill(bill.getId(),idRoom,Integer.parseInt(edQuantityPeople.getText().toString()));
                        if(hardBillDAO.insert(hardBill)){
                            roomDAO.changeOffStatus(idRoom);
                            hardBillArrayList.clear();
                            hardBillArrayList.addAll(hardBillDAO.getAll());
                            hardBillAdapter.notifyDataSetChanged();
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        // tổng tiền phòng theo số ngày thuê
        int costRoom = hardBillDAO.getCostRoom(bill.getId())*billDAO.getNumberDate(bill.getId());
        holder.tvCostRoom.setText("$ " +costRoom);
        // tổng tiền thuế 10%
        int VAT = (costService+costRoom)*bill.getVAT()/100;
        holder.tvVAT.setText("$ "+VAT);
        // thành tiền
        int sumCost = costService+costRoom+VAT;
        // push dữ liệu thành tiền vo database
        billDAO.updateSumCost(sumCost,bill.getId());
        holder.tvSumCost.setText("$ "+sumCost);
        //Xóa hóa đơn khi chưa thanh toán
        holder.imgDeleteBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo !!!");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Xác nhận hủy?");
                builder.setCancelable(false);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(billDAO.changeStatusCancel(bill.getId())){
                            arrayList.clear();
                            arrayList.addAll(billDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Hủy bỏ", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Thay đổi trạng thái hóa đơn
        holder.imgStatusBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo !!!");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("Khách hàng đã thanh toán?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đã thanh toán", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(billDAO.changeStatus(bill.getId())){
                            //lấy ngày giờ lúc thay đổi trạng thái là giờ thanh toán và trả phòng
                            ArrayList<Room> roomArrayList = roomDAO.getRoomInBill(bill.getId());
                            for(int i = 0; i<roomArrayList.size();i++){
                                Room room = roomArrayList.get(i);
                                roomDAO.changeWaitStatus(room.getId());
                            }
                            arrayList.clear();
                            arrayList.addAll(billDAO.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Chưa thanh toán", new DialogInterface.OnClickListener() {
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

    private void openDiaLogUpdate(Bill bill) {
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView tvIdBill,tvCustomerBill,tvReceptionistBill,tvCheckIn,tvCheckOut,tvCostRoom,tvCostService,tvVAT,tvStatusBill,tvSumCost,tvServiceBill;
        ImageView imgRoomBill,imgDeleteBill,imgStatusBill;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBill = itemView.findViewById(R.id.tvIdBill);
            tvCustomerBill = itemView.findViewById(R.id.tvCustomerBill);
            tvReceptionistBill = itemView.findViewById(R.id.tvReceptionistBill);
            tvServiceBill = itemView.findViewById(R.id.tvServiceBill);
            tvCheckIn = itemView.findViewById(R.id.tvCheckIn);
            tvCheckOut = itemView.findViewById(R.id.tvCheckOut);
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
