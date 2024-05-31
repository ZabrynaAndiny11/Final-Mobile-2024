package com.example.ayomakan.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayomakan.DetailActivity;
import com.example.ayomakan.R;
import com.example.ayomakan.model.Resto;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RestoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_DEFAULT = 0;
    private static final int VIEW_TYPE_TOP_RATED = 1;

    private List<Resto> restoList;
    private boolean isTopRated;

    public RestoAdapter(List<Resto> restoList) {
        this.restoList = restoList;
    }
    public RestoAdapter(List<Resto> restoList, boolean isTopRated) {
        this.restoList = restoList;
        this.isTopRated = isTopRated;
    }

    @Override
    public int getItemViewType(int position) {
        return isTopRated ? VIEW_TYPE_TOP_RATED : VIEW_TYPE_DEFAULT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TOP_RATED) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_resto2, parent, false);
            return new TopRatedRestoViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_resto, parent, false);
            return new DefaultRestoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Resto resto = restoList.get(position);
        if (holder instanceof TopRatedRestoViewHolder) {
            ((TopRatedRestoViewHolder) holder).bind(resto);
        } else {
            ((DefaultRestoViewHolder) holder).bind(resto);
        }
    }

    @Override
    public int getItemCount() {
        return restoList.size();
    }

    public class DefaultRestoViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_resto;
        private TextView tv_namaResto;
        private TextView tv_cityResto;
        private TextView tv_ratingResto;

        public DefaultRestoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_resto = itemView.findViewById(R.id.iv_resto);
            tv_namaResto = itemView.findViewById(R.id.tv_namaResto);
            tv_cityResto = itemView.findViewById(R.id.tv_cityResto);
            tv_ratingResto = itemView.findViewById(R.id.tv_ratingResto);
        }

        public void bind(final Resto resto) {
            tv_namaResto.setText(resto.getName());
            tv_cityResto.setText(resto.getCity());
            tv_ratingResto.setText(String.valueOf(resto.getRating()));
            Picasso.get()
                    .load("https://restaurant-api.dicoding.dev/images/medium/" + resto.getPictureId())
                    .into(iv_resto);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("RESTO_ID", resto.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public class TopRatedRestoViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_resto2;
        private TextView tv_namaResto;
        private TextView tv_cityResto;
        private TextView tv_ratingResto;

        public TopRatedRestoViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_resto2 = itemView.findViewById(R.id.iv_resto);
            tv_namaResto = itemView.findViewById(R.id.tv_namaResto);
            tv_cityResto = itemView.findViewById(R.id.tv_cityResto);
            tv_ratingResto = itemView.findViewById(R.id.tv_ratingResto);
        }

        public void bind(final Resto resto) {
            tv_namaResto.setText(resto.getName());
            tv_cityResto.setText(resto.getCity());
            tv_ratingResto.setText(String.valueOf(resto.getRating()));
            Picasso.get()
                    .load("https://restaurant-api.dicoding.dev/images/medium/" + resto.getPictureId())
                    .into(iv_resto2);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("RESTO_ID", resto.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}