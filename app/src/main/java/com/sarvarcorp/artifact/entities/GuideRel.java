package com.sarvarcorp.artifact.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GuideRel {
    @PrimaryKey
    @NonNull
    public int id;
    public int guideId;
    public int relGuideId;
}
