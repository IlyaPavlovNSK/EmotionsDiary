package com.pavlovnsk.emotionsdiary.Adapters.MenuList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavlovnsk.emotionsdiary.R;

import javax.inject.Inject;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private MenuListPresenter menuListPresenter;
    private MenuViewHolder.OnItemListener onItemListener;

    @Inject
    public MenuAdapter(MenuListPresenter menuListPresenter, MenuViewHolder.OnItemListener onItemListener) {
        this.menuListPresenter = menuListPresenter;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_menu_item, parent, false), onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        menuListPresenter.onBindMenuItemRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return menuListPresenter.getMenuItemRowsCount();
    }
}
