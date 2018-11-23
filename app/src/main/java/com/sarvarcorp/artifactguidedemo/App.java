package com.sarvarcorp.artifactguidedemo;

import android.app.Application;

import com.sarvarcorp.artifactguidedemo.dagger.AppComponent;
import com.sarvarcorp.artifactguidedemo.dagger.DaggerAppComponent;
import com.sarvarcorp.artifactguidedemo.dagger.StaticData;
import com.sarvarcorp.artifactguidedemo.dagger.modules.BaseModule;
import com.sarvarcorp.artifactguidedemo.dagger.modules.ContextModule;

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

    public static AppComponent getComponent() {
        return component;
    }
}