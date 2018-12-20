package com.sarvarcorp.artifactguide.models;


import com.sarvarcorp.artifactguide.App;
import com.sarvarcorp.artifactguide.base.BaseViewModel;
import com.sarvarcorp.artifactguide.entities.UniversalItem;
import com.sarvarcorp.artifactguide.repositories.UniversalItemRepository;

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

