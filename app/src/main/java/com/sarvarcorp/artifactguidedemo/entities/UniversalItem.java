package com.sarvarcorp.artifactguidedemo.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UniversalItem {
    @PrimaryKey
    @NonNull
    public int id;
    public int parentId;
    public String viewType;
    public String name;
    public String image;
    public String smallImage;
    public String description;
    public boolean isDetail;
    public String backgroundColor;
}
