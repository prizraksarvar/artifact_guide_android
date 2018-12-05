package com.sarvarcorp.artifact.daos;

import com.sarvarcorp.artifact.entities.Guide;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface GuideDao {
    @Insert(onConflict = REPLACE)
    void save(Guide guide);

    @Query("SELECT * FROM Guide WHERE id = :id")
    LiveData<Guide> get(int id);

    @Query("SELECT * FROM Guide WHERE typeId = :typeId")
    LiveData<List<Guide>> getList(int typeId);
}
