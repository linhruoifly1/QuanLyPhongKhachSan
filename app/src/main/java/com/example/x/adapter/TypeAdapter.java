package com.example.x.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.x.DAO.TypeDAO;
import com.example.x.R;
import com.example.x.model.Type;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.viewHolder>{
    private Context context;
    private ArrayList<Type> arrayList;
    TypeDAO typeDAO;

    public TypeAdapter(Context context, ArrayList<Type> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        typeDAO = new TypeDAO(context);
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_type,null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Type type = arrayList.get(position);
        holder.tvIdType.setText(""+type.getId());
        holder.tvNameType.setText(type.getName());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDiaLogUpdate(type);
                return true;
            }
        });
    }

    private void openDiaLogUpdate(Type type) {
        AlertDialog.Builder builder =new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.update_type,null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        EditText edNameTypeUpdate = view.findViewById(R.id.edNameTypeUpdate);
        Button btnUpdateTypeNew = view.findViewById(R.id.btnUpdateTypeNew);
        Button btnCancelTypeUpdate = view.findViewById(R.id.btnCancelTypeUpdate);
        edNameTypeUpdate.setText(type.getName());
        btnCancelTypeUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnUpdateTypeNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edNameTypeUpdate.getText().length()==0){
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
//                for(int i =0;i<arrayList.size();i++){
//                    Type type1 = arrayList.get(i);
//                    if(edNameTypeUpdate.getText().toString().equals(type1.getName())){
//                        Toast.makeText(context, "Đã có dữ liệu", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                }
                type.setName(edNameTypeUpdate.getText().toString());
                if(typeDAO.update(type)){
                    arrayList.clear();
                    arrayList.addAll(typeDAO.getAll());
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
        TextView tvIdType,tvNameType;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdType = itemView.findViewById(R.id.tvIdType);
            tvNameType = itemView.findViewById(R.id.tvNameType);
        }
    }
}
