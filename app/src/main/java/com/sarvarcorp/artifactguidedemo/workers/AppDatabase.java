package com.sarvarcorp.artifactguidedemo.workers;

import com.sarvarcorp.artifactguidedemo.daos.GuideDao;
import com.sarvarcorp.artifactguidedemo.daos.GuideTypeDao;
import com.sarvarcorp.artifactguidedemo.daos.ImageCacheDao;
import com.sarvarcorp.artifactguidedemo.entities.Guide;
import com.sarvarcorp.artifactguidedemo.entities.GuideClass;
import com.sarvarcorp.artifactguidedemo.entities.GuideColor;
import com.sarvarcorp.artifactguidedemo.entities.GuideRarity;
import com.sarvarcorp.artifactguidedemo.entities.GuideRel;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.ImageCache;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(
        entities = {
                GuideType.class,
                Guide.class,
                GuideClass.class,
                GuideColor.class,
                GuideRarity.class,
                GuideRel.class,
                ImageCache.class,
        },
        version = 2
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GuideDao guideDao();
    public abstract GuideTypeDao guideTypeDao();
    public abstract ImageCacheDao imageCacheDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS ImageCache");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ImageCache` (`id` INTEGER NOT NULL, `url` TEXT, `created` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };
}

