package com.sarvarcorp.artifactguide.daos;

import com.sarvarcorp.artifactguide.entities.ImageCache;

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
