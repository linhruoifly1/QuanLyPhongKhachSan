package com.example.x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.model.Receptionist;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText ed_username, ed_email;
    Button btn_confirm;
    ReceptionistDAO receptionistDAO;
    Receptionist receptionist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ed_email = findViewById(R.id.ed_email);
        ed_username = findViewById(R.id.ed_username);
        btn_confirm = findViewById(R.id.btn_confirm);
        receptionistDAO = new ReceptionistDAO(getApplicationContext());

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed_username.getText().toString().trim();
                String email = ed_email.getText().toString().trim();

                if (email.isEmpty() || username.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(receptionistDAO.getUsername(username)==null){
                    Toast.makeText(getApplicationContext(), "Tài khoản hoặc email không chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }
                receptionist = receptionistDAO.getUsername(username);
                if (receptionist.getUsername().equals(username) && receptionist.getEmail().equals(email)){
                    Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                    intent.putExtra("receptionist",receptionist.getUsername());
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Tài khoản hoặc email không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}