package com.example.ayomakan.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ayomakan.R;
import com.example.ayomakan.adapter.FavoriteAdapter;
import com.example.ayomakan.api.ApiConfig;
import com.example.ayomakan.api.ApiService;
import com.example.ayomakan.model.Resto;
import com.example.ayomakan.response.RestoResponse;
import com.example.ayomakan.sqlite.DbConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private ImageView ivNoResto;
    private TextView tvNetwork;
    private ProgressBar progressBar;
    private FavoriteAdapter favoriteAdapter;
    private RecyclerView recyclerView;
    private DbConfig dbConfig;
    private ApiService service;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        ivNoResto = view.findViewById(R.id.iv_noResto);
        tvNetwork = view.findViewById(R.id.error);
        recyclerView = view.findViewById(R.id.rv_resto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        service = ApiConfig.getClient().create(ApiService.class);

        dbConfig = new DbConfig(requireActivity());
        preferences = requireActivity().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);

        refreshFavoriteResto();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFavoriteResto();
    }

    private void refreshFavoriteResto() {
        progressBar.setVisibility(View.VISIBLE);
        ivNoResto.setVisibility(View.GONE);
        tvNetwork.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        int userId = preferences.getInt("user_id", 0);

        Cursor cursor = dbConfig.getFavoriteRestoUserId(userId);
        ArrayList<String> favoriteRestoIds = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String restoId = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_RESTO_ID));
                favoriteRestoIds.add(restoId);
            } while (cursor.moveToNext());
        }

        Call<RestoResponse> call = service.getResto(1); // Change the call type to RestoResponse
        call.enqueue(new Callback<RestoResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestoResponse> call, @NonNull Response<RestoResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && isAdded()) {
                    RestoResponse restoResponse = response.body();
                    List<Resto> restoList = restoResponse.getRestoList();
                    List<Resto> favoriteResto = new ArrayList<>();
                    if (restoList != null) {
                        for (Resto resto : restoList) {
                            if (favoriteRestoIds.contains(resto.getId())) {
                                favoriteResto.add(resto);
                            }
                        }
                    }
                    favoriteAdapter = new FavoriteAdapter(getParentFragmentManager(), favoriteResto, userId);
                    recyclerView.setAdapter(favoriteAdapter);

                    if (favoriteResto.isEmpty()) {
                        ivNoResto.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        ivNoResto.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestoResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNetwork.setVisibility(View.VISIBLE);
            }
        });
    }
}
