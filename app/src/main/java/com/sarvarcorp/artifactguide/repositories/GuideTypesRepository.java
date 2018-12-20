package com.sarvarcorp.artifactguide.repositories;

import com.sarvarcorp.artifactguide.App;
import com.sarvarcorp.artifactguide.daos.GuideTypeDao;
import com.sarvarcorp.artifactguide.entities.GuideType;
import com.sarvarcorp.artifactguide.workers.Webservice;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import retrofit2.Response;

@Singleton
public class GuideTypesRepository {
    private static final long FRESH_TIMEOUT = 300000; //5min
    private final Webservice webservice;
    private final GuideTypeDao guideTypeDao;
    private final Executor executor;

    @Inject
    public GuideTypesRepository(Webservice webservice, GuideTypeDao guideTypeDao, Executor executor) {
        this.webservice = webservice;
        this.guideTypeDao = guideTypeDao;
        this.executor = executor;
    }

    public LiveData<List<GuideType>> getList() {
        refreshGuideTypes();
        return guideTypeDao.getList();
    }

    private void refreshGuideTypes() {
        executor.execute(() -> {
            Response<ResponseAdapter<List<GuideType>>> response = null;
            try {
                //TODO: Запрашивать нужно только если данные не обновляли давно
                response = webservice.getGuideTypes(App.getComponent().provideStaticData().getUserToken()).execute();

                if (response.isSuccessful() && response.body()!=null)
                    for (GuideType guideType: response.body().data) {
                        guideTypeDao.save(guideType);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

