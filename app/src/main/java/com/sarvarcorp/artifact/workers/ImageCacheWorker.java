package com.sarvarcorp.artifact.workers;

import android.content.Context;

import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.base.Base;

import java.io.File;

public class ImageCacheWorker extends Base {
    public boolean fileExist(String url) {
        Context context = App.getComponent().porvideContext();
        File file = new File(context.getCacheDir(), url);
        return file.exists();
    }
}
