package com.sarvarcorp.artifact.repositories;

import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.daos.GuideDao;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.workers.Webservice;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Response;

@Singleton
public class GuideRepository {
    private static final long FRESH_TIMEOUT = 300000; //5min
    private final Webservice webservice;
    private final GuideDao guideDao;
    private final Executor executor;

    @Inject
    public GuideRepository(Webservice webservice, GuideDao guideDao, Executor executor) {
        this.webservice = webservice;
        this.guideDao = guideDao;
        this.executor = executor;
    }

    public LiveData<Guide> get(int id) {
        refreshGuide(id);
        return guideDao.get(id);
    }

    public LiveData<List<Guide>> getList(int typeId) {
        refreshGuides(typeId);
        return guideDao.getList(typeId);
    }

    private void refreshGuides(final int typeId) {
        executor.execute(() -> {
            Response<ResponseAdapter<List<Guide>>> response = null;
            try {
                //TODO: Запрашивать нужно только если данные не обновляли давно
                response = webservice.getGuides(App.getComponent().provideStaticData().getUserToken(), typeId).execute();

                if (response.isSuccessful() && response.body()!=null)
                    for (Guide guide: response.body().data) {
                        guideDao.save(guide);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshGuide(final int id) {
        executor.execute(() -> {
            Response<ResponseAdapter<Guide>> response = null;
            try {
                //TODO: Запрашивать нужно только если данные не обновляли давно
                response = webservice.getGuide(App.getComponent().provideStaticData().getUserToken(), id).execute();

                if (response.isSuccessful() && response.body()!=null)
                    guideDao.save(response.body().data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

