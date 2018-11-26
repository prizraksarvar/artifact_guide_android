package com.sarvarcorp.artifactguidedemo.repositories;

import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.daos.UniversalItemDao;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.workers.Webservice;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Response;

@Singleton
public class UniversalItemRepository {
    private static final long FRESH_TIMEOUT = 300000; //5min
    private final Webservice webservice;
    private final UniversalItemDao universalItemDao;
    private final Executor executor;

    @Inject
    public UniversalItemRepository(Webservice webservice, UniversalItemDao universalItemDao, Executor executor) {
        this.webservice = webservice;
        this.universalItemDao = universalItemDao;
        this.executor = executor;
    }

    public LiveData<UniversalItem> get(int id) {
        refreshUniversalItem(id);
        return universalItemDao.get(id);
    }

    public LiveData<List<UniversalItem>> getList(int parentId) {
        refreshUniversalItems(parentId);
        return universalItemDao.getList(parentId);
    }

    private void refreshUniversalItems(final int parentId) {
        executor.execute(() -> {
            Response<ResponseAdapter<List<UniversalItem>>> response = null;
            try {
                //TODO: Запрашивать нужно только если данные не обновляли давно
                response = webservice.getUniversalItems(App.getComponent().provideStaticData().getUserToken(), parentId).execute();

                if (response.isSuccessful() && response.body()!=null)
                    for (UniversalItem universalItem: response.body().data) {
                        universalItemDao.save(universalItem);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshUniversalItem(final int id) {
        executor.execute(() -> {
            Response<ResponseAdapter<UniversalItem>> response = null;
            try {
                //TODO: Запрашивать нужно только если данные не обновляли давно
                response = webservice.getUniversalItem(App.getComponent().provideStaticData().getUserToken(), id).execute();

                if (response.isSuccessful() && response.body()!=null)
                    universalItemDao.save(response.body().data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

