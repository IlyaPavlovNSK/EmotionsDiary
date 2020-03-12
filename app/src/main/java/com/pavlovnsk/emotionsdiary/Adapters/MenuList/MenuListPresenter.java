package com.pavlovnsk.emotionsdiary.Adapters.MenuList;
import com.pavlovnsk.emotionsdiary.POJO.MenuItem;

import java.util.ArrayList;

import javax.inject.Inject;

public class MenuListPresenter{

    @Inject
    ArrayList<MenuItem> itemsMenu;

    @Inject
    MenuListPresenter(ArrayList<MenuItem> items) {
        this.itemsMenu = items;
    }

    void onBindMenuItemRowViewAtPosition(int position, MenuRowView rowView) {
        MenuItem item = itemsMenu.get(position);
        rowView.setIcon(item.getIdIcon());
        rowView.setTitle(item.getTitle());
    }

    int getMenuItemRowsCount() {
        return itemsMenu.size();
    }
}
