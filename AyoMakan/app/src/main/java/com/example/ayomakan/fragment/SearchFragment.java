package com.example.ayomakan.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayomakan.R;
import com.example.ayomakan.adapter.RestoItemAdapter;
import com.example.ayomakan.api.ApiConfig;
import com.example.ayomakan.api.ApiService;
import com.example.ayomakan.model.Resto;
import com.example.ayomakan.response.RestoSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ImageView ivNoResto;
    private TextView tvNetwork;
    private RestoItemAdapter itemRestoAdapter;
    private List<Resto> filteredRestos;
    private ProgressBar progressBar;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.rv_resto);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        filteredRestos = new ArrayList<>();
        itemRestoAdapter = new RestoItemAdapter(filteredRestos);
        recyclerView.setAdapter(itemRestoAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        searchView = view.findViewById(R.id.search);
        ivNoResto = view.findViewById(R.id.iv_noResto);
        tvNetwork = view.findViewById(R.id.error);

        apiService = ApiConfig.getClient().create(ApiService.class);

        setupSearchView();
        return view;
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    ivNoResto.setVisibility(View.GONE);
                    tvNetwork.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    ivNoResto.setVisibility(View.GONE);
                    tvNetwork.setVisibility(View.GONE);

                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> searchRestos(newText), 1000);
                }
                return true;
            }
        });
    }

    private void searchRestos(String query) {
        Call<RestoSearchResponse> call = apiService.searchResto(query);
        call.enqueue(new Callback<RestoSearchResponse>() {
            @Override
            public void onResponse(Call<RestoSearchResponse> call, Response<RestoSearchResponse> response) {
                progressBar.setVisibility(View.GONE);
                ivNoResto.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    filteredRestos.clear();
                    filteredRestos.addAll(response.body().getRestos());

                    if (!filteredRestos.isEmpty()) {
                        recyclerView.setVisibility(View.VISIBLE);
                        itemRestoAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        ivNoResto.setVisibility(View.VISIBLE);
                    }
                } else {
                    ivNoResto.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<RestoSearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNetwork.setVisibility(View.VISIBLE);
            }
        });
    }
}
