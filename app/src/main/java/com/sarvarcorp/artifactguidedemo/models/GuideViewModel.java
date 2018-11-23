package com.sarvarcorp.artifactguidedemo.models;


import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.base.BaseViewModel;
import com.sarvarcorp.artifactguidedemo.entities.Guide;
import com.sarvarcorp.artifactguidedemo.repositories.GuideRepository;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class GuideViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<Guide> guide;

    private GuideRepository guideRepository;

    @Inject
    public GuideViewModel() {
        this.guideRepository = App.getComponent().provideGuidesRepository();
    }

    public void init(int id) {
        if (this.guide != null) {
            return;
        }
        guide = guideRepository.get(id);
    }


    public LiveData<Guide> get() {
        return guide;
    }
}

