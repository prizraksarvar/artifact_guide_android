package com.sarvarcorp.artifactguidedemo.models;


import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.base.BaseViewModel;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.repositories.GuideTypesRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class UniversalItemViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<List<UniversalItem>> universalItemList;
    private UniversalItemRepository universalItemRepository;

    @Inject
    public UniversalItemViewModel() {
        this.universalItemRepository = App.getComponent().provideUniversalItemRepository();
    }

    public void init() {
        if (this.universalItemList != null) {
            return;
        }
        universalItemList = universalItemRepository.getList();
    }


    public LiveData<List<UniversalItem>> getList() {
        return universalItemList;
    }
}

