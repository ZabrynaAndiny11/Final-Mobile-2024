package com.example.ayomakan.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ayomakan.R;
import com.example.ayomakan.adapter.RestoItemAdapter;

public class FavoriteFragment extends Fragment {

    private RestoItemAdapter itemRestoAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.rv_resto);

        // Set up the RecyclerView with a layout manager and the adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        itemRestoAdapter = new RestoItemAdapter();
        recyclerView.setAdapter(itemRestoAdapter);
    }
}
