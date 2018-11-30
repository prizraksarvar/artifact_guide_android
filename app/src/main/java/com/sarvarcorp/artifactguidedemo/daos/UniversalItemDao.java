package com.sarvarcorp.artifactguidedemo.daos;

import com.sarvarcorp.artifactguidedemo.entities.Guide;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UniversalItemDao {
    @Insert(onConflict = REPLACE)
    void save(UniversalItem universalItem);

    @Query("SELECT * FROM UniversalItem WHERE id = :id")
    LiveData<UniversalItem> get(int id);

    @Query("SELECT * FROM UniversalItem WHERE parentId = :parentId")
    LiveData<List<UniversalItem>> getList(int parentId);

    @Query("DELETE FROM UniversalItem WHERE parentId = :parentId")
    void delete(int parentId);

    @Query("DELETE FROM UniversalItem WHERE parentId = :parentId AND id not IN (:ids)")
    void deleteNotIds(int parentId, int[] ids);
}
