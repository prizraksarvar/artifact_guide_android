package com.sarvarcorp.artifactguide.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GuideColor {
    @PrimaryKey
    @NonNull
    public int id;
    public String name;
    public String image;
}
