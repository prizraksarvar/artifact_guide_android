package com.sarvarcorp.artifactguide.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GuideType {
    @PrimaryKey
    @NonNull
    public int id;
    public String parentId;
    public String name;
    public String image;
}
