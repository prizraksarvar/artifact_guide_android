package com.sarvarcorp.artifact.models;


import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.BaseViewModel;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.repositories.GuideRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class GuidesViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<List<Guide>> guideList;

    private GuideRepository guideRepository;

    @Inject
    public GuidesViewModel() {
        this.guideRepository = App.getComponent().provideGuidesRepository();
    }

    public void init(int typeId) {
        if (this.guideList != null) {
            return;
        }
        guideList = guideRepository.getList(typeId);
    }


    public LiveData<List<Guide>> getList() {
        return guideList;
    }
}

