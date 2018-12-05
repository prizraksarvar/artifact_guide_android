package com.sarvarcorp.artifact.models;


import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.BaseViewModel;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.repositories.GuideRepository;

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

