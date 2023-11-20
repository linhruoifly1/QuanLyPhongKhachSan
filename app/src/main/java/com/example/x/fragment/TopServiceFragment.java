package com.example.x.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.DAO.StatisticDAO;
import com.example.x.R;
import com.example.x.adapter.ServiceAdapter;
import com.example.x.adapter.TopServiceAdapter;
import com.example.x.model.TopService;

import java.util.ArrayList;

public class TopServiceFragment extends Fragment {

    StatisticDAO dao;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_service, container, false);

        RecyclerView rcvTopSV  = view.findViewById(R.id.rcvtopService);
        dao = new StatisticDAO(getContext());
        ArrayList<TopService> list =dao.getService();

        LinearLayoutManager manager =new LinearLayoutManager(getContext());
        rcvTopSV.setLayoutManager(manager);
        TopServiceAdapter adapter = new TopServiceAdapter(getContext(),list);
        rcvTopSV.setAdapter(adapter);


        return view;
    }
}