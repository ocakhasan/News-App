package com.example.hasanocakhomework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<NewsItem> news;
    Context context;
    NewsAdapterListener listener;

    public NewsAdapter(List<NewsItem> news, Context context, NewsAdapterListener listener) {
        this.news = news;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        holder.newDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(news.get(position).getNewsDate()));
        holder.newTitle.setText(news.get(position).getTitle());

        holder.lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(news.get(position));
            }
        });

        if(news.get(position).getBitmap()==null){
            new ImageDownloadTask(holder.newImg).execute(news.get(position));
        }else{
            holder.newImg.setImageBitmap(news.get(position).getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface NewsAdapterListener{
        public void onClick(NewsItem selectedNewItem);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView newImg;
        TextView newTitle;
        TextView newDate;
        ConstraintLayout lyt;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg = itemView.findViewById(R.id.newImage);
            newTitle = itemView.findViewById(R.id.newTitle);
            newDate = itemView.findViewById(R.id.newDate);
            lyt = itemView.findViewById(R.id.container);

        }
    }
}
