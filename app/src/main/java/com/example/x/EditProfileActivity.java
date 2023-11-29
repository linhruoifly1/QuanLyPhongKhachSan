package com.example.x;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.model.Receptionist;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {
    ImageView img_edit_avt;
    TextView tv_change_avt;
    EditText ed_edit_username, ed_edit_name, ed_edit_email;
    Button btn_update_profile;
    ReceptionistDAO receptionistDAO;
    Receptionist librarian;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        img_edit_avt = findViewById(R.id.img_edit_avt);
//        tv_change_avt = findViewById(R.id.tv_changeImg);
        ed_edit_email = findViewById(R.id.ed_edit_email);
        ed_edit_name = findViewById(R.id.ed_edit_name);
        receptionistDAO = new ReceptionistDAO(this);
        Intent intent = getIntent();
        String user = intent.getStringExtra("client");
             librarian = receptionistDAO.getUsername(user);

        if (user!= null){
            ed_edit_name.setText(librarian.getName());
            ed_edit_email.setText(librarian.getEmail());
        }
        btn_update_profile = findViewById(R.id.btn_update_profile);

//        tv_change_avt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openGallery();
//
//            }
//        });
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = ed_edit_name.getText().toString();
               String email = ed_edit_email.getText().toString();

                if (!name.isEmpty() && ! email.isEmpty()){
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        librarian.setEmail(ed_edit_email.getText().toString());
                        librarian.setName(ed_edit_name.getText().toString());
                        int kq = receptionistDAO.updateProfile(librarian);
                        if (kq >0){
                            Toast.makeText(EditProfileActivity.this, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ed_edit_email.setError("Email không đúng định dạng");
                    }


                } else {
                    ed_edit_email.setError("Email không được để trống");
                    ed_edit_name.setError("Tên không được để trống");
                }


            }
        });
    }

//    public void openGallery(){
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        openGallery.launch(intent);
//    }

//    ActivityResultLauncher<Intent > openGallery = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result!=null && result.getData()!= null){
//                        Uri uriImage = result.getData().getData();
//                        if (uriImage != null){
//                            img_edit_avt.setImageURI(uriImage);
//                        }
//                    } else {
//
//                    }
//
//                }
//            });
}