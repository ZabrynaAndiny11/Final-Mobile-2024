package com.example.ayomakan.response;

import com.example.ayomakan.model.CustomerReview;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    private boolean error;
    private String massage;

    @SerializedName("restaurant")
    private List<CustomerReview> customerReview;
    private List<CustomerReview> customerReviews;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public List<CustomerReview> getCustomerReview() {
        return customerReview;
    }

    public void setCustomerReview(List<CustomerReview> customerReview) {
        this.customerReview = customerReview;
    }

    public List<CustomerReview> getCustomerReviews() {
        return customerReviews;
    }

    public void setCustomerReviews(List<CustomerReview> customerReviews) {
        this.customerReviews = customerReviews;
    }
}
