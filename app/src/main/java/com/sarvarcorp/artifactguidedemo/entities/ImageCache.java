package com.sarvarcorp.artifactguidedemo.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ImageCache {
    @PrimaryKey
    @NonNull
    public int id;
    public String url;
    public int created;
}
