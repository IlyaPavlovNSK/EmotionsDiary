package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

import com.pavlovnsk.emotionsdiary.POJO.EmotionItem;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismissLeft(int position);
    void onItemDismissRight(int position);
    void returnItem(int position);
    void removeItem();
}
