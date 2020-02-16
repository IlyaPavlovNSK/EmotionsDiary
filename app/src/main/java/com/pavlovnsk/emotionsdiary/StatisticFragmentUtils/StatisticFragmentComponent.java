package com.pavlovnsk.emotionsdiary.StatisticFragmentUtils;

import com.pavlovnsk.emotionsdiary.GlobalModule;
import com.pavlovnsk.emotionsdiary.Fragments.StatisticsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = {GlobalModule.class})
public interface StatisticFragmentComponent {
    void inject (StatisticsFragment statisticsFragment);
}
