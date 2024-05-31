package com.example.ayomakan.api;

import com.example.ayomakan.response.DetailRestoResponse;
import com.example.ayomakan.response.RestoResponse;
import com.example.ayomakan.response.RestoSearchResponse;
import com.example.ayomakan.response.ReviewResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("list")
    Call<RestoResponse> getResto(@Query("count") int count);

    @GET("detail/{id}")
    Call<DetailRestoResponse> getRestoDetail(@Path("id") String restoId);

    @GET("search")
    Call<RestoSearchResponse> searchResto(@Query("q") String query);

    @FormUrlEncoded
    @POST("review")
    Call<ReviewResponse> postReview(@Field("id") String id, @Field("name") String name, @Field("review") String review);
}