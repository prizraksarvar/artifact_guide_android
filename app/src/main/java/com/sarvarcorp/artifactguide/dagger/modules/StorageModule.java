package com.sarvarcorp.artifactguide.dagger.modules;

import android.content.Context;

import com.sarvarcorp.artifactguide.dagger.StaticData;
import com.sarvarcorp.artifactguide.repositories.GuideRepository;
import com.sarvarcorp.artifactguide.repositories.GuideTypesRepository;
import com.sarvarcorp.artifactguide.repositories.UniversalItemRepository;
import com.sarvarcorp.artifactguide.workers.AppDatabase;
import com.sarvarcorp.artifactguide.workers.ImageCacheWorker;
import com.sarvarcorp.artifactguide.workers.Webservice;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ContextModule.class)
public class StorageModule {

    @Provides
    public AppDatabase provideAppDatabase(StaticData staticData, Context context) {
        return staticData.getAppDatabase(context);
    }

    @Provides
    public ImageCacheWorker provideImageCacheWorker(StaticData staticData) {
        return staticData.getImageCacheWorker();
    }

    @Provides
    public Webservice provideWebservice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://artifact-guide.sarvarcorp.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(Webservice.class);
    }

    @Provides
    public GuideTypesRepository provideGuideTypesRepository(Webservice webservice, AppDatabase appDatabase, Executor executor) {
        return new GuideTypesRepository(webservice, appDatabase.guideTypeDao(), executor);
    }

    @Provides
    public GuideRepository provideGuidesRepository(Webservice webservice, AppDatabase appDatabase, Executor executor) {
        return new GuideRepository(webservice, appDatabase.guideDao(), executor);
    }

    @Provides
    public UniversalItemRepository provideUniversalItemRepository(Webservice webservice, AppDatabase appDatabase, Executor executor) {
        return new UniversalItemRepository(webservice, appDatabase.universalItemDao(), executor);
    }
}