package com.sarvarcorp.artifactguidedemo.dagger;

import android.content.Context;

import com.sarvarcorp.artifactguidedemo.dagger.modules.BaseModule;
import com.sarvarcorp.artifactguidedemo.dagger.modules.ContextModule;
import com.sarvarcorp.artifactguidedemo.dagger.modules.StorageModule;
import com.sarvarcorp.artifactguidedemo.repositories.GuideRepository;
import com.sarvarcorp.artifactguidedemo.repositories.GuideTypesRepository;
import com.sarvarcorp.artifactguidedemo.workers.AppDatabase;
import com.sarvarcorp.artifactguidedemo.workers.FragmentWorker;
import com.sarvarcorp.artifactguidedemo.workers.ImageCacheWorker;
import com.sarvarcorp.artifactguidedemo.workers.SettingWorker;

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
}