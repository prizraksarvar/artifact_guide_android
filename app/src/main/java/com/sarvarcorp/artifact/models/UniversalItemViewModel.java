package com.sarvarcorp.artifact.models;


import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.BaseViewModel;
import com.sarvarcorp.artifact.entities.UniversalItem;
import com.sarvarcorp.artifact.repositories.UniversalItemRepository;

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

