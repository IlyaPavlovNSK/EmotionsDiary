package com.pavlovnsk.emotionsdiary.Adapters.MenuList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class MenuViewHolder extends RecyclerView.ViewHolder implements MenuRowView, View.OnClickListener {

    private TextView title;
    private ImageView icon;
    private OnItemListener onItemListener;

    @Inject
    public MenuViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
        super(itemView);

        title = itemView.findViewById(R.id.text_view_menu);
        icon = itemView.findViewById(R.id.image_view_menu);
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void setTitle(String s) {
        title.setText(s);
    }

    @Override
    public void setIcon(int id) {
        icon.setImageResource(id);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition());
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
