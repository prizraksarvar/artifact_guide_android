package com.sarvarcorp.artifactguidedemo.daos;

import com.sarvarcorp.artifactguidedemo.entities.GuideType;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GuideTypeDao {
    @Insert(onConflict = REPLACE)
    void save(GuideType guideType);

    @Query("SELECT * FROM GuideType WHERE id = :id")
    LiveData<GuideType> get(int id);

    @Query("SELECT * FROM GuideType")
    LiveData<List<GuideType>> getList();
}
