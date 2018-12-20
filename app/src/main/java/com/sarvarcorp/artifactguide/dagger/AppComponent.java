package com.sarvarcorp.artifactguide.dagger;

import android.content.Context;

import com.sarvarcorp.artifactguide.dagger.modules.BaseModule;
import com.sarvarcorp.artifactguide.dagger.modules.ContextModule;
import com.sarvarcorp.artifactguide.dagger.modules.StorageModule;
import com.sarvarcorp.artifactguide.repositories.GuideRepository;
import com.sarvarcorp.artifactguide.repositories.GuideTypesRepository;
import com.sarvarcorp.artifactguide.repositories.UniversalItemRepository;
import com.sarvarcorp.artifactguide.workers.AppDatabase;
import com.sarvarcorp.artifactguide.workers.FragmentWorker;
import com.sarvarcorp.artifactguide.workers.ImageCacheWorker;
import com.sarvarcorp.artifactguide.workers.SettingWorker;

import java.util.concurrent.Executor;

import dagger.Component;

@Component(modules = {
        BaseModule.class,
        StorageModule.class,
        ContextModule.class
})
public interface AppComponent {
    //Context
    Context porvideContext();

    //Base
    SettingWorker provideSettingWorker();
    FragmentWorker provideFragmentWorker();
    StaticData provideStaticData();

    //Storage
    AppDatabase provideAppDatabase();
    ImageCacheWorker provideImageCacheWorker();
    Executor provideExecutor();
    GuideTypesRepository provideGuideTypesRepository();
    GuideRepository provideGuidesRepository();
    UniversalItemRepository provideUniversalItemRepository();
}