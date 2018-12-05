package com.sarvarcorp.artifact.models;


import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.BaseViewModel;
import com.sarvarcorp.artifact.entities.GuideType;
import com.sarvarcorp.artifact.repositories.GuideTypesRepository;

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

