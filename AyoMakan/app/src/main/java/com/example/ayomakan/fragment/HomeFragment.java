package com.example.ayomakan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayomakan.R;
import com.example.ayomakan.adapter.RestoAdapter;
import com.example.ayomakan.api.ApiConfig;
import com.example.ayomakan.api.ApiService;
import com.example.ayomakan.model.Resto;
import com.example.ayomakan.response.RestoResponse;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ApiService apiService;
    private TextView network, tv_list, tv_rating;
    private RecyclerView rvResto, rvResto2;
    private ProgressBar progressBar, progressBar2;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiService = ApiConfig.getClient().create(ApiService.class);

        rvResto = view.findViewById(R.id.rv_resto);
        rvResto2 = view.findViewById(R.id.rv_resto2);
        network = view.findViewById(R.id.error);
        tv_list = view.findViewById(R.id.textViewlist);
        tv_rating = view.findViewById(R.id.textViewrating);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar2 = view.findViewById(R.id.progressBar2);

        rvResto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvResto2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadDataRating();
        loadDataList();
    }

    private void loadDataRating() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);

        Call<RestoResponse> restoResponseCall = apiService.getResto(1);
        restoResponseCall.enqueue(new Callback<RestoResponse>() {
            @Override
            public void onResponse(Call<RestoResponse> call, Response<RestoResponse> response) {
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        List<Resto> restoList = response.body().getRestoList();
                        if (restoList != null && !restoList.isEmpty()) {
                            RestoAdapter adapter = new RestoAdapter(restoList);
                            rvResto.setAdapter(adapter);

                            Collections.sort(restoList, (r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
                            RestoAdapter topRatedAdapter = new RestoAdapter(restoList, true);
                            rvResto2.setAdapter(topRatedAdapter);
                        } else {
                            Toast.makeText(getContext(), "No restaurant data available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RestoResponse> call, Throwable t) {
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                    tv_list.setVisibility(View.GONE);
                    tv_rating.setVisibility(View.GONE);
                    network.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    private void loadDataList() {
        progressBar.setVisibility(View.VISIBLE);

        Call<RestoResponse> restoResponseCall = apiService.getResto(1);
        restoResponseCall.enqueue(new Callback<RestoResponse>() {
            @Override
            public void onResponse(Call<RestoResponse> call, Response<RestoResponse> response) {
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        List<Resto> restoList = response.body().getRestoList();
                        if (restoList != null && !restoList.isEmpty()) {
                            RestoAdapter adapter = new RestoAdapter(restoList);
                            rvResto.setAdapter(adapter);
                        } else {
                            Toast.makeText(getContext(), "No restaurant data available", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<RestoResponse> call, Throwable t) {
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    progressBar2.setVisibility(View.GONE);
                    tv_list.setVisibility(View.GONE);
                    tv_rating.setVisibility(View.GONE);
                    network.setVisibility(View.VISIBLE);
                });
            }
        });
    }
}