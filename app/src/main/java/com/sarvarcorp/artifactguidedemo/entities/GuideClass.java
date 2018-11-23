package com.sarvarcorp.artifactguidedemo.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GuideClass {
    @PrimaryKey
    @NonNull
    public int id;
    public String name;
    public String image;
}
