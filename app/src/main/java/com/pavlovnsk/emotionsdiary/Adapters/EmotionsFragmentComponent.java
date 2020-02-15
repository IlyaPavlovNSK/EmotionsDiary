package com.pavlovnsk.emotionsdiary.Adapters;

import com.pavlovnsk.emotionsdiary.ArrayEmotions;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsFragment;

import dagger.Component;

@Component(modules = {ArrayEmotions.class})
public interface EmotionsFragmentComponent {
    void inject(EmotionsFragment emotionsFragment);
}
