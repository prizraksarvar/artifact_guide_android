package com.sarvarcorp.artifact.workers;

import com.sarvarcorp.artifact.daos.GuideDao;
import com.sarvarcorp.artifact.daos.GuideTypeDao;
import com.sarvarcorp.artifact.daos.ImageCacheDao;
import com.sarvarcorp.artifact.daos.UniversalItemDao;
import com.sarvarcorp.artifact.entities.Guide;
import com.sarvarcorp.artifact.entities.GuideClass;
import com.sarvarcorp.artifact.entities.GuideColor;
import com.sarvarcorp.artifact.entities.GuideRarity;
import com.sarvarcorp.artifact.entities.GuideRel;
import com.sarvarcorp.artifact.entities.GuideType;
import com.sarvarcorp.artifact.entities.ImageCache;
import com.sarvarcorp.artifact.entities.UniversalItem;

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
                UniversalItem.class,
        },
        version = 6
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GuideDao guideDao();
    public abstract GuideTypeDao guideTypeDao();
    public abstract ImageCacheDao imageCacheDao();
    public abstract UniversalItemDao universalItemDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS ImageCache");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ImageCache` (`id` INTEGER NOT NULL, `url` TEXT, `created` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS GuideType");
            database.execSQL("CREATE TABLE IF NOT EXISTS `GuideType` (`id` INTEGER NOT NULL, `parentId` TEXT, `name` TEXT, `image` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("DROP TABLE IF EXISTS UniversalItem");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UniversalItem` (`id` INTEGER NOT NULL, `parentId` INTEGER NOT NULL, `viewType` TEXT, `name` TEXT, `image` TEXT, `description` TEXT, PRIMARY KEY(`id`))");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS UniversalItem");
            database.execSQL("CREATE TABLE IF NOT EXISTS `UniversalItem` (`id` INTEGER NOT NULL, `parentId` INTEGER NOT NULL, `viewType` TEXT, `name` TEXT, `image` TEXT, `description` TEXT, `isDetail` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `UniversalItem` ADD COLUMN `smallImage` TEXT");
            database.execSQL("ALTER TABLE `UniversalItem` ADD COLUMN `backgroundColor` TEXT");
        }
    };

    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `UniversalItem` ADD COLUMN `updatedDate` INTEGER NOT NULL default 0");
        }
    };
}

