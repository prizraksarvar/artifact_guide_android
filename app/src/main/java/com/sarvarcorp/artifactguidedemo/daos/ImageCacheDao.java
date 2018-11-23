package com.sarvarcorp.artifactguidedemo.daos;

import android.content.Context;

import com.sarvarcorp.artifactguidedemo.App;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.ImageCache;

import java.io.File;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ImageCacheDao {
    @Insert(onConflict = REPLACE)
    void save(ImageCache imageCache);

    @Query("SELECT * FROM ImageCache WHERE id = :id")
    LiveData<ImageCache> get(int id);

    @Query("SELECT * FROM ImageCache WHERE url = :url")
    LiveData<ImageCache> get(String url);

}
