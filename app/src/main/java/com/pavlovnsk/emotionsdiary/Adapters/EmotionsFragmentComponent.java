package com.pavlovnsk.emotionsdiary.Adapters;

import com.pavlovnsk.emotionsdiary.Fragments.AddEmotionFragment;
import com.pavlovnsk.emotionsdiary.Fragments.ListEmotionsItemFragment;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GlobalModule.class})
@Singleton
public interface EmotionsFragmentComponent {
    void inject(EmotionsFragment emotionsFragment);
    void inject(AddEmotionFragment addEmotionFragment);
    void inject(ListEmotionsItemFragment listEmotionsItemFragment);
}
