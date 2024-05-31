package com.example.ayomakan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayomakan.adapter.DrinkAdapter;
import com.example.ayomakan.adapter.FoodAdapter;
import com.example.ayomakan.adapter.ReviewsAdapter;
import com.example.ayomakan.api.ApiConfig;
import com.example.ayomakan.api.ApiService;
import com.example.ayomakan.model.CustomerReview;
import com.example.ayomakan.model.MenuItem;
import com.example.ayomakan.response.DetailRestoResponse;
import com.example.ayomakan.response.ReviewResponse;
import com.example.ayomakan.sqlite.DbConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private ApiService apiService;
    private DbConfig dbConfig;
    private Context context;

    private ImageView ivResto, iv_back, ivArrowDown, ivArrowDown1, iv_send, btn_favorit;
    private TextView tvName, tvDescription, tvCity, tvAddress, tvRating, tvError;
    private TextInputEditText et_addReview;
    private Button btnMenuFood, btnMenuDrink, btnRetry;
    private ProgressBar progressBar;
    private LinearLayout llDetailContent;
    private RecyclerView rv_reviews, rv_food, rv_drink;
    private CardView cvFav;

    private List<CustomerReview> customerReviews;
    private List<MenuItem> foodList, drinkList;
    private String restoId, username;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();
        setupRecyclerViews();

        context = this;
        dbConfig = new DbConfig(this);
        username = dbConfig.getUsername();

        Intent intent = getIntent();
        restoId = intent.getStringExtra("RESTO_ID");

        if (restoId != null && !restoId.isEmpty()) {
            fetchRestoDetail(restoId);
            int loggedInUserId = getLoggedInUserId();
            isFavorite = isRestoFavorit(loggedInUserId, restoId);
            updateFavoriteIcon();
        } else {
            Toast.makeText(this, "No restaurant ID received", Toast.LENGTH_SHORT).show();
        }

        setupListeners();
    }

    private void initViews() {
        progressBar = findViewById(R.id.progressBar1);
        rv_reviews = findViewById(R.id.rv_reviews);
        rv_food = findViewById(R.id.rv_food);
        rv_drink = findViewById(R.id.rv_drink);
        iv_back = findViewById(R.id.btn_back);
        ivResto = findViewById(R.id.ivResto);
        ivArrowDown = findViewById(R.id.iv_arrow_down);
        ivArrowDown1 = findViewById(R.id.iv_arrow_down1);
        iv_send = findViewById(R.id.iv_send);
        tvName = findViewById(R.id.tvNamaResto);
        tvDescription = findViewById(R.id.tv_descResto);
        tvCity = findViewById(R.id.tvCity);
        tvAddress = findViewById(R.id.tv_address);
        tvRating = findViewById(R.id.tvRatingResto);
        tvError = findViewById(R.id.error);
        btnMenuFood = findViewById(R.id.btn_menu_food);
        btnMenuDrink = findViewById(R.id.btn_menu_drink);
        btn_favorit = findViewById(R.id.btn_fav);
        btnRetry = findViewById(R.id.retry);
        llDetailContent = findViewById(R.id.ll_detail_content);
        et_addReview = findViewById(R.id.et_addReview);
        cvFav = findViewById(R.id.cvFav);
    }

    private void setupRecyclerViews() {
        rv_drink.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv_food.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rv_reviews.setLayoutManager(new LinearLayoutManager(context));

        rv_food.setVisibility(View.VISIBLE);

        customerReviews = new ArrayList<>();
        foodList = new ArrayList<>();
        drinkList = new ArrayList<>();
    }

    private void setupListeners() {
        iv_back.setOnClickListener(v -> finish());

        btnMenuFood.setOnClickListener(v -> {
            rv_food.setVisibility(View.VISIBLE);
            rv_drink.setVisibility(View.GONE);
        });

        btnMenuDrink.setOnClickListener(v -> {
            rv_food.setVisibility(View.GONE);
            rv_drink.setVisibility(View.VISIBLE);
        });

        ivArrowDown.setOnClickListener(v -> toggleVisibility(rv_reviews, ivArrowDown));
        ivArrowDown1.setOnClickListener(v -> toggleVisibility(tvDescription, ivArrowDown1));

        btnRetry.setOnClickListener(v -> fetchRestoDetail(restoId));

        btn_favorit.setOnClickListener(v -> toggleFavorite());

        iv_send.setOnClickListener(v -> {
            String reviewText = et_addReview.getText().toString();
            postReview(reviewText, username);
        });
    }

    private void fetchRestoDetail(String restoId) {
        progressBar.setVisibility(View.VISIBLE);
        cvFav.setVisibility(View.VISIBLE);
        llDetailContent.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);

        apiService = ApiConfig.getClient().create(ApiService.class);
        Call<DetailRestoResponse> call = apiService.getRestoDetail(restoId);
        call.enqueue(new Callback<DetailRestoResponse>() {
            @Override
            public void onResponse(Call<DetailRestoResponse> call, Response<DetailRestoResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    DetailRestoResponse restoDetailResponse = response.body();
                    displayRestoDetail(restoDetailResponse);
                    loadReviews(restoDetailResponse.getDetailResto().getCustomerReviews());
                    loadFoodItems(restoDetailResponse.getDetailResto().getMenus().getFoods());
                    loadDrinkItems(restoDetailResponse.getDetailResto().getMenus().getDrinks());
                    llDetailContent.setVisibility(View.VISIBLE);
                } else {
                    tvError.setVisibility(View.VISIBLE);
                    btnRetry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DetailRestoResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                cvFav.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                btnRetry.setVisibility(View.VISIBLE);
            }
        });
    }

    private void toggleVisibility(View view, ImageView arrow) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            arrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
        } else {
            view.setVisibility(View.VISIBLE);
            arrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24);
        }
    }

    private void postReview(String reviewText, String username) {
        apiService = ApiConfig.getClient().create(ApiService.class);
        Call<ReviewResponse> call = apiService.postReview(restoId, username, reviewText);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    loadReviews(response.body().getCustomerReviews());
                    Toast.makeText(DetailActivity.this, "Successful review added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "No connection internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReviews(List<CustomerReview> reviews) {
        Collections.sort(reviews, Comparator.comparing(CustomerReview::getDate).reversed());
        customerReviews = reviews;
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(customerReviews);
        rv_reviews.setAdapter(reviewsAdapter);
        et_addReview.setText("");
    }

    private void loadFoodItems(List<MenuItem> foods) {
        foodList = foods;
        FoodAdapter foodAdapter = new FoodAdapter(foodList);
        rv_food.setAdapter(foodAdapter);
    }

    private void loadDrinkItems(List<MenuItem> drinks) {
        drinkList = drinks;
        DrinkAdapter drinkAdapter = new DrinkAdapter(drinkList);
        rv_drink.setAdapter(drinkAdapter);
    }

    private void displayRestoDetail(DetailRestoResponse resto) {
        tvName.setText(resto.getDetailResto().getName());
        tvDescription.setText(resto.getDetailResto().getDescription());
        tvCity.setText(resto.getDetailResto().getCity());
        tvAddress.setText(resto.getDetailResto().getAddress());
        tvRating.setText(String.valueOf(resto.getDetailResto().getRating()));
        Picasso.get()
                .load("https://restaurant-api.dicoding.dev/images/medium/" + resto.getDetailResto().getPictureId())
                .into(ivResto);
    }

    private void toggleFavorite() {
        int loggedInUserId = getLoggedInUserId();
        String restoId = getIntent().getStringExtra("RESTO_ID");

        if (restoId != null && !restoId.isEmpty()) {
            if (isFavorite) {
                dbConfig.deleteFavorite(loggedInUserId, restoId);
                btn_favorit.setImageResource(R.drawable.baseline_favorite_border_24);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                dbConfig.insertFavorite(loggedInUserId, restoId);
                btn_favorit.setImageResource(R.drawable.baseline_favorite_24);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            isFavorite = !isFavorite;
        } else {
            Toast.makeText(this, "No restaurant ID received", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            btn_favorit.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            btn_favorit.setImageResource(R.drawable.baseline_favorite_border_24);
        }
    }

    private boolean isRestoFavorit(int userId, String restoId) {
        Cursor cursor = dbConfig.getFavoriteRestoUserId(userId);
        boolean isFavorite = false;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String resto = cursor.getString(cursor.getColumnIndexOrThrow(DbConfig.COLUMN_RESTO_ID));
                if (resto.equals(restoId)) {
                    isFavorite = true;
                    break;
                }
            }
            cursor.close();
        }
        return isFavorite;
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }
}
