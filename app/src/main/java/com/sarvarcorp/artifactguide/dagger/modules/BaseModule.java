package com.sarvarcorp.artifactguide.dagger.modules;

import android.content.Context;

import com.sarvarcorp.artifactguide.base.BaseExecuter;
import com.sarvarcorp.artifactguide.dagger.StaticData;
import com.sarvarcorp.artifactguide.workers.FragmentWorker;
import com.sarvarcorp.artifactguide.workers.SettingWorker;

import java.util.concurrent.Executor;

import dagger.Module;
import dagger.Provides;

@Module
public class BaseModule {
    private StaticData staticData;

    public BaseModule(StaticData staticData) {
        this.staticData = staticData;
    }

    @Provides
    public Executor provideExecutor() {
        return new BaseExecuter();
    }

    @Provides
    public StaticData provideStaticData() {
        return staticData;
    }

    @Provides
    public SettingWorker provideSettingWorker(Context context) {
        return new SettingWorker(context);
    }

    @Provides
    public FragmentWorker provideFragmentWorker(StaticData staticData) {
        return staticData.getFragmentWorker();
    }
}
