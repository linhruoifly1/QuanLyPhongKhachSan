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
import com.example.x.adapter.TopRoomAdapter;
import com.example.x.model.TopRoom;

import java.util.ArrayList;

public class TopRoomFragment extends Fragment {
    RecyclerView rcvTopRoom;
    StatisticsDAO statisticsDAO;
    ArrayList<TopRoom> arrayList;
    TopRoomAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvTopRoom = view.findViewById(R.id.rcvTopRoom);
        statisticsDAO = new StatisticsDAO(getContext());
        arrayList = statisticsDAO.getTopRoom();
        rcvTopRoom.setLayoutManager(new GridLayoutManager(getContext(),1));
        adapter = new TopRoomAdapter(getContext(),arrayList);
        rcvTopRoom.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}