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

public class AdapterB extends RecyclerView.Adapter<AdapterB.ViewHolderB> {
    Context contextB;
    List<Articles> articlesB;

    public AdapterB(Context contextB, List<Articles> articlesB) {
        this.contextB = contextB;
        this.articlesB = articlesB;
    }


    @NonNull
    @Override
    public AdapterB.ViewHolderB onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsb, parent, false);
        return new AdapterB.ViewHolderB(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterB.ViewHolderB holder, int position) {
        final Articles a = articlesB.get(position);

        String imageUrl = a.getUrlToImage();

        holder.tvTitleB.setText(a.getTitle());
        holder.tvSourceB.setText(a.getSource().getName());
        holder.tvDateB.setText(a.getPublishedAt());


        Picasso.with(contextB).load(imageUrl).into(holder.imageViewB);
    }

    @Override
    public int getItemCount() {
        return articlesB.size();
    }

    public static class ViewHolderB extends RecyclerView.ViewHolder {

        TextView tvTitleB,tvSourceB,tvDateB;
        ImageView imageViewB;
        CardView cardViewB;

        public ViewHolderB(@NonNull View itemView) {
            super(itemView);

            tvTitleB = itemView.findViewById(R.id.tvTitleB);
            tvSourceB = itemView.findViewById(R.id.tvSourceB);
            tvDateB = itemView.findViewById(R.id.tvDateB);
            imageViewB = itemView.findViewById(R.id.imageB);
            cardViewB = itemView.findViewById(R.id.cardViewB);

        }
    }

    public void setArticlesB(List<Articles> articlesB) {
        this.articlesB.addAll(articlesB);
        int count = getItemCount();
        notifyItemRangeInserted(count, count + articlesB.size());
    }
}
