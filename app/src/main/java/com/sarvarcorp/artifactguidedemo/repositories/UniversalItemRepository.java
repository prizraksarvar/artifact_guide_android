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
                long time = System.currentTimeMillis();
                if (universalItemDao.unfreshCount(parentId,time-FRESH_TIMEOUT)>0 || universalItemDao.count(parentId)==0) {
                    response = webservice.getUniversalItems(App.getComponent().provideStaticData().getUserToken(), parentId).execute();
                boolean updatesOnly = true;
                if (universalItemDao.unfreshCount(parentId,time-FRESH_TIMEOUT)>0) {
                    updatesOnly = false;
                }
                response = webservice.getUniversalItems(App.getComponent().provideStaticData().getUserToken(), updatesOnly, parentId).execute();

                if (response.isSuccessful() && response.body() != null) {
                    int[] ids = new int[response.body().data.size()];
                    int i = 0;
                    for (UniversalItem universalItem : response.body().data) {
                        universalItem.updatedDate = time;
                        universalItemDao.save(universalItem);
                        ids[i] = universalItem.id;
                        i++;
                    }
                    if (updatesOnly)
                        universalItemDao.deleteNotIds(parentId, ids);
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
                long time = System.currentTimeMillis();
                if (universalItemDao.isUnfresh(id,time-FRESH_TIMEOUT) || !universalItemDao.exist(id)) {
                    response = webservice.getUniversalItem(App.getComponent().provideStaticData().getUserToken(), id).execute();
                boolean updatesOnly = true;
                if (universalItemDao.isUnfresh(id,time-FRESH_TIMEOUT)) {
                    updatesOnly = false;
                }
                response = webservice.getUniversalItem(App.getComponent().provideStaticData().getUserToken(), updatesOnly, id).execute();

                if (response.isSuccessful() && response.body() != null)
                    universalItemDao.save(response.body().data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

