package com.sarvarcorp.artifactguide.models;


import com.sarvarcorp.artifactguide.App;
import com.sarvarcorp.artifactguide.base.BaseViewModel;
import com.sarvarcorp.artifactguide.entities.GuideType;
import com.sarvarcorp.artifactguide.repositories.GuideTypesRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class GuideTypesViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<List<GuideType>> guideTypesList;
    private GuideTypesRepository guideTypesRepository;

    @Inject
    public GuideTypesViewModel() {
        this.guideTypesRepository = App.getComponent().provideGuideTypesRepository();
    }

    public void init() {
        if (this.guideTypesList != null) {
            return;
        }
        guideTypesList = guideTypesRepository.getList();
    }


    public LiveData<List<GuideType>> getList() {
        return guideTypesList;
    }
}

