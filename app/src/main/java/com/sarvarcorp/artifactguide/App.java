package com.sarvarcorp.artifactguide;

import android.app.Application;

import com.sarvarcorp.artifactguide.dagger.AppComponent;
import com.sarvarcorp.artifactguide.dagger.DaggerAppComponent;
import com.sarvarcorp.artifactguide.dagger.StaticData;
import com.sarvarcorp.artifactguide.dagger.modules.BaseModule;
import com.sarvarcorp.artifactguide.dagger.modules.ContextModule;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class App extends Application {
    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .baseModule(new BaseModule(new StaticData()))
                .build();

    }

    protected void initYandexAppMetrica() {
        String API_key = "790f38f3-e5df-419f-a51f-4731ba830fd2";
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder(API_key).build();
        YandexMetrica.activate(getApplicationContext(), config);
        YandexMetrica.enableActivityAutoTracking(this);
    }

    public static AppComponent getComponent() {
        return component;
    }
}