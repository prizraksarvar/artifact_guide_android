package com.sarvarcorp.artifactguidedemo.models;


import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.base.BaseViewModel;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.repositories.GuideTypesRepository;
import com.sarvarcorp.artifactguidedemo.repositories.UniversalItemRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class UniversalItemsViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<List<UniversalItem>> universalItemList;
    private UniversalItemRepository universalItemRepository;

    @Inject
    public UniversalItemsViewModel() {
        this.universalItemRepository = App.getComponent().provideUniversalItemRepository();
    }

    public void init(int parentId) {
        if (this.universalItemList != null) {
            return;
        }
        universalItemList = universalItemRepository.getList(parentId);
    }


    public LiveData<List<UniversalItem>> getList() {
        return universalItemList;
    }
}

