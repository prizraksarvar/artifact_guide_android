package com.sarvarcorp.artifactguidedemo.models;


import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.base.BaseViewModel;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;
import com.sarvarcorp.artifactguidedemo.repositories.UniversalItemRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;

public class UniversalItemViewModel extends BaseViewModel {
    private String userToken;
    private LiveData<UniversalItem> universalItem;
    private UniversalItemRepository universalItemRepository;

    @Inject
    public UniversalItemViewModel() {
        this.universalItemRepository = App.getComponent().provideUniversalItemRepository();
    }

    public void init(int id) {
        if (this.universalItem != null) {
            return;
        }
        universalItem = universalItemRepository.get(id);
    }


    public LiveData<UniversalItem> get() {
        return universalItem;
    }
}

