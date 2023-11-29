package com.example.x.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.EditProfileActivity;
import com.example.x.R;
import com.example.x.model.Receptionist;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }
    Button btn_edit_profile;
    ReceptionistDAO receptionistDAO;
    TextView tv_username, tv_name, tv_email;
    Receptionist librarian;
    ImageView img_avt;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_edit_profile =  view.findViewById(R.id.btn_edit_profile);
        tv_email = view.findViewById(R.id.tv_email);
        tv_name = view.findViewById(R.id.tv_name);
        tv_username = view.findViewById(R.id.tv_username);
        img_avt = view.findViewById(R.id.img_avt);
        receptionistDAO = new ReceptionistDAO(getContext());
        Intent intent = getActivity().getIntent();
        String user = intent.getStringExtra("user");

        if (user!= null){
             librarian = receptionistDAO.getUsername(user);
            tv_email.setText("Email: " +librarian.getEmail());
            tv_username.setText("Username: "+librarian.getUsername());
            tv_name.setText("Họ tên: "+librarian.getName());

        }


        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("client",librarian.getUsername());
                startActivity(intent);

            }
        });

    }
}
