package com.example.x.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.LoginActivity;
import com.example.x.R;


public class ChangePassFragment extends Fragment {


    public ChangePassFragment() {
        // Required empty public constructor
    }

    EditText edtmkCu, edtmkMoi, edtxacNhan;
    Button btnxacNhan;

    ReceptionistDAO receptionistDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);

        // ánh xạ
        edtmkCu = view.findViewById(R.id.edtmkCu);
        edtmkMoi = view.findViewById(R.id.edtmkMoi);
        edtxacNhan = view.findViewById(R.id.edtxacNhan);
        TextView tv_mkc = view.findViewById(R.id.errorMKC);
        TextView tv_mkm = view.findViewById(R.id.errorMKM);
        TextView tv_xn = view.findViewById(R.id.errorXN);

        btnxacNhan = view.findViewById(R.id.btnxacNhan);

        btnxacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // tạo 1 file lưu trữ
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("receptionist", Context.MODE_PRIVATE);
                String user = sharedPreferences.getString("username", "");

                receptionistDAO = new ReceptionistDAO(getContext());

                String mkCu = edtmkCu.getText().toString();
                String mkMoi = edtmkMoi.getText().toString();
                String xacNhan = edtxacNhan.getText().toString();

                //validate mkc
                if (mkCu.equals("")) {
                    tv_mkc.setText("Vui lòng điền mật khẩu cũ");
                } else {
                    tv_mkc.setText("");
                }
                // validate mkm
                if (mkMoi.equals("")) {
                    tv_mkm.setText("Vui lòng điền mật khẩu mới");
                } else if
                (edtmkMoi.getText().length() < 6) {
                    tv_mkm.setText("Mật khẩu phải có 6 kí tự");
                } else {
                    tv_mkm.setText("");
                }
                //validate xn
                if (xacNhan.equals("")) {
                    tv_xn.setText("Vui lòng xác nhận mật khẩu");
                    return;
                }
                if (xacNhan.equals(mkMoi)) {
                    if (receptionistDAO.changePassword(user, mkCu, mkMoi) == 1) {
                        Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (receptionistDAO.changePassword(user, mkCu, mkMoi) == -1) {
                        Toast.makeText(getContext(), "Đổi Mật Khẩu Thất bại", Toast.LENGTH_SHORT).show();
                    } else {
                        tv_mkc.setText("Mật khẩu cũ không đúng");
                    }
                } else {
                    tv_xn.setText("2 mật khẩu không khớp");
                }
            }
        });

        return view;
    }
}