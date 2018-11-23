package com.sarvarcorp.artifactguidedemo.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Guide {
    @PrimaryKey
    @NonNull
    public int id;
    public int typeId;
    public String name;
    public String image;
    public String imageAdditional;
    public String description;
    public int classId;
    public int rarityId;
    public int colorId;
    public int armor;
    public int attack;
    public int health;
}
