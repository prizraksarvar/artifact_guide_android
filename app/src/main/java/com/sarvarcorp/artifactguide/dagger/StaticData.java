package com.sarvarcorp.artifactguide.dagger;

import android.content.Context;

import com.sarvarcorp.artifactguide.MainActivity;
import com.sarvarcorp.artifactguide.workers.AppDatabase;
import com.sarvarcorp.artifactguide.workers.FragmentWorker;
import com.sarvarcorp.artifactguide.workers.ImageCacheWorker;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

@Singleton
public class StaticData {
    @Inject
    public StaticData() {
    }

    private FragmentManager fragmentManager;
    private AppDatabase appDatabase;
    private String userToken;
    private FragmentWorker fragmentWorker;
    private ImageCacheWorker imageCacheWorker;
    private MainActivity mainActivity;

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public FragmentWorker getFragmentWorker() {
        if (fragmentWorker==null) {
            fragmentWorker = new FragmentWorker(getFragmentManager());
        }
        return fragmentWorker;
    }

    public AppDatabase getAppDatabase(Context context) {
        if (appDatabase==null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                    .addMigrations(AppDatabase.MIGRATION_1_2, AppDatabase.MIGRATION_2_3, AppDatabase.MIGRATION_3_4, AppDatabase.MIGRATION_4_5, AppDatabase.MIGRATION_5_6)
                    .build();
        }
        return appDatabase;
    }

    public ImageCacheWorker getImageCacheWorker() {
        if (imageCacheWorker==null) {
            imageCacheWorker = new ImageCacheWorker();
        }
        return imageCacheWorker;
    }
}
