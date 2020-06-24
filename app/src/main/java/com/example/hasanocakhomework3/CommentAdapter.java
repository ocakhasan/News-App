package com.example.hasanocakhomework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    List<CommentItem> comments;
    Context context;
    CommentAdapterListener listener;

    public CommentAdapter(List<CommentItem> comments, Context context, CommentAdapterListener listener) {
        this.comments = comments;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_row_layout, parent, false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {
        holder.comment_name.setText(comments.get(position).getName());
        holder.comment_body.setText(comments.get(position).getMessage());

        holder.lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(comments.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public interface CommentAdapterListener{
        public void onClick(CommentItem selectedCommentItem);
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_name;
        TextView comment_body;
        ConstraintLayout lyt;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_body = itemView.findViewById(R.id.temp_body);
            comment_name = itemView.findViewById(R.id.temp_name);
            lyt = itemView.findViewById(R.id.comment_container);
        }
    }
}
