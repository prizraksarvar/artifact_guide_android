package com.sarvarcorp.artifact.models;


import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.BaseViewModel;
import com.sarvarcorp.artifact.entities.UniversalItem;
import com.sarvarcorp.artifact.repositories.UniversalItemRepository;

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

