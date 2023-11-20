package com.example.x;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.x.DAO.ReceptionistDAO;
import com.example.x.fragment.AddUserFragment;
import com.example.x.fragment.BillFragment;
import com.example.x.fragment.ChangePassFragment;
import com.example.x.fragment.CustomerFragment;
import com.example.x.fragment.RevenueFragment;
import com.example.x.fragment.RoomFragment;
import com.example.x.fragment.ServiceFragment;
import com.example.x.fragment.TopRoomFragment;
import com.example.x.fragment.TopServiceFragment;
import com.example.x.fragment.TypeFragment;
import com.example.x.model.Receptionist;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout frameNav;
    private NavigationView navView;
    private View mHeaderView;
    TextView tvFullNameUser;
    ReceptionistDAO receptionistDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        frameNav = findViewById(R.id.frameNav);
        receptionistDAO = new ReceptionistDAO(this);
        navView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        RoomFragment roomFragment = new RoomFragment();
        replaceFragment(roomFragment);
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        Receptionist librarian = receptionistDAO.getUsername(user);
        mHeaderView = navView.getHeaderView(0);
        tvFullNameUser = mHeaderView.findViewById(R.id.tvFullNameUser);
        tvFullNameUser.setText(librarian.getName());
        if (user.equalsIgnoreCase("admin")) {
            navView.getMenu().findItem(R.id.addUser).setVisible(true);
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.room) {
                    RoomFragment roomFragment = new RoomFragment();
                    replaceFragment(roomFragment);
                } else if (item.getItemId() == R.id.type) {
                    TypeFragment typeFragment = new TypeFragment();
                    replaceFragment(typeFragment);
                } else if (item.getItemId() == R.id.service) {
                    ServiceFragment serviceFragment = new ServiceFragment();
                    replaceFragment(serviceFragment);
                } else if (item.getItemId() == R.id.bill) {
                    BillFragment billFragment = new BillFragment();
                    replaceFragment(billFragment);
                } else if (item.getItemId() == R.id.client) {
                    CustomerFragment clientFragment = new CustomerFragment();
                    replaceFragment(clientFragment);
                } else if (item.getItemId() == R.id.revenue) {
                    RevenueFragment revenueFragment = new RevenueFragment();
                    replaceFragment(revenueFragment);
                } else if (item.getItemId() == R.id.topRoom) {
                    TopRoomFragment topRoomFragment = new TopRoomFragment();
                    replaceFragment(topRoomFragment);
                } else if (item.getItemId() == R.id.topService) {
                    TopServiceFragment topServiceFragment = new TopServiceFragment();
                    replaceFragment(topServiceFragment);
                } else if (item.getItemId() == R.id.change) {
                    ChangePassFragment changePassFragment = new ChangePassFragment();
                    replaceFragment(changePassFragment);
                } else if (item.getItemId() == R.id.addUser) {
                    AddUserFragment addUserFragment = new AddUserFragment();
                    replaceFragment(addUserFragment);

                } else if (item.getItemId() == R.id.profile) {

                } else if (item.getItemId() == R.id.signout) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.close();
                toolbar.setTitle(item.getTitle());
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frameNav, fragment).commit();
    }
}