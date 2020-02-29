package com.pavlovnsk.emotionsdiary.Adapters.EmotionListSmall;

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismissLeft(int position);
    void onItemDismissRight(int position);
}
