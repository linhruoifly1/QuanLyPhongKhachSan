package com.example.x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edUsername,edPassword;
    private TextView errUs, errPass;
    private CheckBox chkRemember;
    private Button btnLogin,btnCancel;
    ReceptionistDAO receptionistDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUsername = findViewById(R.id.edtUser);
        edPassword = findViewById(R.id.edtPass);
        chkRemember = findViewById(R.id.chkRemember);
        errUs = findViewById(R.id.txterrUsername);
        errPass = findViewById(R.id.txterrPassword);
        btnLogin = findViewById(R.id.btndangNhap);
        receptionistDAO = new ReceptionistDAO(LoginActivity.this);
        SharedPreferences preferences = getSharedPreferences("userfile",MODE_PRIVATE);
        edUsername.setText(preferences.getString("username",""));
        edPassword.setText(preferences.getString("password",""));
        chkRemember.setChecked(preferences.getBoolean("remember",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    private void checkLogin() {
        String inputUser = edUsername.getText().toString();
        String inputPass = edPassword.getText().toString();
        if(inputUser.isEmpty()){
            errUs.setText("Vui lòng nhập tên đăng nhập");
        }else {
            errUs.setText("");
        }
        //
        if(inputPass.isEmpty()){
            errPass.setText("Vui lòng nhập mật khẩu");
        }else{
            errPass.setText("");
        }

            if(receptionistDAO.checkLogin(inputUser,inputPass)){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(inputUser,inputPass,chkRemember.isChecked());
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("user",inputUser);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }

    private void rememberUser(String inputUser, String inputPass, boolean checked) {
        SharedPreferences preferences = getSharedPreferences("userfile",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(!checked){
            editor.clear();
        }else{
            editor.putString("username",inputUser);
            editor.putString("password",inputPass);
            editor.putBoolean("remember",checked);
        }
        editor.commit();
    }
}