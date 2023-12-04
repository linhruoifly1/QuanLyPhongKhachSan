package com.example.x.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.ServiceDAO;
import com.example.x.R;
import com.example.x.model.Service;
import com.example.x.model.Type;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.viewHolder>{
    private Context context;
    private ArrayList<Service> arrayList;
    private ServiceDAO serviceDAO;

    public ServiceAdapter(Context context, ArrayList<Service> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        serviceDAO = new ServiceDAO(context);
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
        //tạo layout dialog
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.update_service,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edNameService = view.findViewById(R.id.edNameServiceUpdate);
        EditText edPriceService = view.findViewById(R.id.edPriceServiceUpdate);
        Button btnUpdate = view.findViewById(R.id.btnUpdateServiceNew);
        Button btnCancel = view.findViewById(R.id.btnCancelServiceUpdate);
        edNameService.setText(service.getName());
        edPriceService.setText(""+service.getPrice());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNameService.getText().toString().equals("")){
                    edNameService.setError("Vui lòng nhập tên dịch vụ");
                    return;
                }
                if (edPriceService.getText().toString().equals("")){
                    edPriceService.setError("Vui lòng nhập giá dịch vụ");
                    return;
                }
//                for(int i =0;i<arrayList.size();i++){
//                    Service service1 = arrayList.get(i);
//                    if(edNameService.getText().toString().equals(service1.getName())){
//                        Toast.makeText(context, "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
                service.setName(edNameService.getText().toString());
                service.setPrice(Integer.parseInt(edPriceService.getText().toString()));
                if(serviceDAO.update(service)){
                    arrayList.clear();
                    arrayList.addAll(serviceDAO.getAll());
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
        TextView tvIdService,tvNameService,tvPriceService;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdService = itemView.findViewById(R.id.tvIdService);
            tvNameService = itemView.findViewById(R.id.tvNameService);
            tvPriceService = itemView.findViewById(R.id.tvPriceService);
        }
    }
}
