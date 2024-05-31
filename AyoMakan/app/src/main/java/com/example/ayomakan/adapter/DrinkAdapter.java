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

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>{
    private List<MenuItem> drinks;

    public DrinkAdapter(List<MenuItem> drinks) {
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public DrinkAdapter.DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_drink, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkAdapter.DrinkViewHolder holder, int position) {
        MenuItem menuItem = drinks.get(position);
        holder.tv_drinks.setText(menuItem.getName());
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder {
        TextView tv_drinks;
        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_drinks = itemView.findViewById(R.id.tv_nameDrink);
        }
    }
}