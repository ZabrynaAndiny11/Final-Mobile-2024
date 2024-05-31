package com.example.ayomakan.adapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ayomakan.DetailActivity;
import com.example.ayomakan.R;
import com.example.ayomakan.model.Resto;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RestoViewHolder> {

    private final FragmentManager fragmentManager;
    private List<Resto> restoList;
    private final int userId;

    public FavoriteAdapter(FragmentManager fragmentManager, List<Resto> restoList,int userId) {
        this.fragmentManager = fragmentManager;
        this.restoList = restoList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public RestoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_resto3, parent, false);
        return new RestoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoViewHolder holder, int position) {
        Resto restoItem = restoList.get(position);
        holder.bind(restoItem);
    }

    @Override
    public int getItemCount() {
        return restoList.size();
    }

    static class RestoViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_resto;
        private TextView tv_namaResto;
        private TextView tv_cityResto;
        private TextView tv_ratingResto;

        public RestoViewHolder(@NonNull View itemView) {
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
}