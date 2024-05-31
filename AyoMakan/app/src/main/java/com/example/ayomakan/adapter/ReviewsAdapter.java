package com.example.ayomakan.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayomakan.R;
import com.example.ayomakan.model.CustomerReview;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    private List<CustomerReview> reviews;

    public ReviewsAdapter(List<CustomerReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        CustomerReview review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewerName, tvReviewText, tvReviewDate;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvReviewerName = itemView.findViewById(R.id.tv_namaCust);
            tvReviewText = itemView.findViewById(R.id.tv_review);
            tvReviewDate = itemView.findViewById(R.id.tv_date);
        }

        void bind(CustomerReview review) {
            tvReviewerName.setText(review.getName());
            tvReviewText.setText(review.getReview());
            tvReviewDate.setText(review.getDate());
        }
    }
}
