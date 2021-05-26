package com.example.newsapp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp4.Model.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterC extends RecyclerView.Adapter<AdapterC.ViewHolderC> {
    Context contextC;
    List<Articles> articlesC;

    public AdapterC(Context contextC, List<Articles> articlesC) {
        this.contextC = contextC;
        this.articlesC = articlesC;
    }


    @NonNull
    @Override
    public AdapterC.ViewHolderC onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsc, parent, false);
        return new AdapterC.ViewHolderC(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterC.ViewHolderC holder, int position) {
        final Articles a = articlesC.get(position);

        String imageUrl = a.getUrlToImage();

        holder.tvTitleC.setText(a.getTitle());
        holder.tvSourceC.setText(a.getSource().getName());
        holder.tvDateC.setText(a.getPublishedAt());


        Picasso.with(contextC).load(imageUrl).into(holder.imageViewC);
    }

    @Override
    public int getItemCount() {
        return articlesC.size();
    }

    public static class ViewHolderC extends RecyclerView.ViewHolder {

        TextView tvTitleC,tvSourceC,tvDateC;
        ImageView imageViewC;
        CardView cardViewC;

        public ViewHolderC(@NonNull View itemView) {
            super(itemView);

            tvTitleC = itemView.findViewById(R.id.tvTitleC);
            tvSourceC = itemView.findViewById(R.id.tvSourceC);
            tvDateC = itemView.findViewById(R.id.tvDateC);
            imageViewC = itemView.findViewById(R.id.imageC);
            cardViewC = itemView.findViewById(R.id.cardViewC);

        }
    }

    public void setArticlesC(List<Articles> articlesC) {
        this.articlesC.addAll(articlesC);
        int count = getItemCount();
        notifyItemRangeInserted(count, count + articlesC.size());
    }
}
