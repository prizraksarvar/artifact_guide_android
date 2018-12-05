package com.sarvarcorp.artifact.dagger;

import android.content.Context;

import com.sarvarcorp.artifact.dagger.modules.BaseModule;
import com.sarvarcorp.artifact.dagger.modules.ContextModule;
import com.sarvarcorp.artifact.dagger.modules.StorageModule;
import com.sarvarcorp.artifact.repositories.GuideRepository;
import com.sarvarcorp.artifact.repositories.GuideTypesRepository;
import com.sarvarcorp.artifact.repositories.UniversalItemRepository;
import com.sarvarcorp.artifact.workers.AppDatabase;
import com.sarvarcorp.artifact.workers.FragmentWorker;
import com.sarvarcorp.artifact.workers.ImageCacheWorker;
import com.sarvarcorp.artifact.workers.SettingWorker;

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