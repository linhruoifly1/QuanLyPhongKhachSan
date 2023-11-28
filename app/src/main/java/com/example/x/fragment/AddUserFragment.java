package com.example.x.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.R;
import com.example.x.model.Receptionist;

import java.util.regex.Pattern;


public class AddUserFragment extends Fragment {
    EditText edthoTen, edtEmail, edtUsername, edtpass;
    Button btnthem;
    ReceptionistDAO receptionistDAO;

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        // ánh xạ
        edthoTen = view.findViewById(R.id.edthoTenadd);
        edtEmail = view.findViewById(R.id.edtEmailadd);
        edtUsername = view.findViewById(R.id.edtUsernameadd);
        edtpass = view.findViewById(R.id.edtpassadd);

        btnthem = view.findViewById(R.id.btnthemUser);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edthoTen.getText().toString();
                String email = edtEmail.getText().toString();
                String user = edtUsername.getText().toString();
                String pass = edtpass.getText().toString();

                if (hoten.equals("")){
                    edthoTen.setError("Không Bỏ Trống Tên");
                    return;

                }
                if ( email.equals("") ){
                    edtEmail.setError("Không Bỏ Trống Email");
                    return;

                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edtEmail.setError("Email không đúng định dạng");
                    return;

                }

                if ( user.equals("")){
                    edtUsername.setError("Không Bỏ Trống Username");
                    return;

                }
                if (pass.equals("")){
                    edtpass.setError("Không Bỏ Trống Password");
                    return;
                }

                Receptionist receptionist = new Receptionist(hoten,email,user,pass);
                receptionistDAO = new ReceptionistDAO(getContext());

                if (receptionistDAO.checkUser(String.valueOf(edtUsername.getText()))){
                    Toast.makeText(getContext(), "Đã tồn tại tài khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
                long kq= receptionistDAO.insert(receptionist);
                if (kq > 0){
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
//        Receptionist receptionist = new Receptionist(getContext());
//        boolean check = receptionistDAO.checkUser(String.valueOf(edtUsername.getText()));

        return view;
    }
}