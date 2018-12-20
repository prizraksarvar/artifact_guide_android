package com.sarvarcorp.artifactguide.workers;

import android.content.Context;

import com.sarvarcorp.artifactguide.App;
import com.sarvarcorp.artifactguide.base.Base;

import java.io.File;

public class ImageCacheWorker extends Base {
    public boolean fileExist(String url) {
        Context context = App.getComponent().porvideContext();
        File file = new File(context.getCacheDir(), url);
        return file.exists();
    }
}
