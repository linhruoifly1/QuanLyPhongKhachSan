package com.example.x.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.x.DAO.StatisticsDAO;
import com.example.x.R;
import com.example.x.adapter.TopServiceAdapter;
import com.example.x.model.TopService;

import java.util.ArrayList;

public class TopServiceFragment extends Fragment {
    RecyclerView rcvTopService;
    StatisticsDAO statisticsDAO;
    ArrayList<TopService> arrayList;
    TopServiceAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTopService = view.findViewById(R.id.rcvTopService);
        statisticsDAO = new StatisticsDAO(getContext());
        arrayList = statisticsDAO.getTopService();
        rcvTopService.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new TopServiceAdapter(getContext(),arrayList);
        rcvTopService.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}