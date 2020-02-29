package com.pavlovnsk.emotionsdiary;

import com.pavlovnsk.emotionsdiary.AddEmotion;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsHistoryFragment;
import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Fragments.EmotionsFragment;
import com.pavlovnsk.emotionsdiary.ListEmotionsItemActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {GlobalModule.class})
@Singleton
public interface EmotionsFragmentComponent {
    void inject(EmotionsFragment emotionsFragment);
    void inject(AddEmotion addEmotion);
    void inject(ListEmotionsItemActivity listEmotionsItemActivity);
    void inject(EmotionsHistoryFragment emotionsHistoryFragment);
    void inject(MainActivity mainActivity);
}
