package com.example.ayomakan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayomakan.R;
import com.example.ayomakan.model.MenuItem;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<MenuItem> foods;

    public FoodAdapter(List<MenuItem> foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        MenuItem menuItem = foods.get(position);
        holder.tv_food.setText(menuItem.getName());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView tv_food;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_food = itemView.findViewById(R.id.tv_nameFood);
        }
    }
}