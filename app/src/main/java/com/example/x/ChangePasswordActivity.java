package com.example.x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.model.Receptionist;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText ed_mkm, ed_xn_mk;
    Button btn_confirm;
    Receptionist receptionist;
    String username;
    ReceptionistDAO receptionistDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ed_mkm = findViewById(R.id.ed_mkm);
        ed_xn_mk = findViewById(R.id.ed_xn_mk);
        btn_confirm= findViewById(R.id.btn_confirm);
        receptionistDAO = new ReceptionistDAO(getApplicationContext());
        username = getIntent().getStringExtra("receptionist");
        receptionist = receptionistDAO.getUsername(username);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkm = ed_mkm.getText().toString();
                String xn = ed_xn_mk.getText().toString();

                if (mkm.isEmpty() || xn.isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (xn.equals(mkm)){
                    if (receptionistDAO.changePassword(receptionist.getUsername(),receptionist.getPassword(),xn)==1){
                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}