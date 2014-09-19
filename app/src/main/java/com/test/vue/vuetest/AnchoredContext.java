package com.test.vue.vuetest;

import android.app.Application;

import com.test.vue.vuetest.utils.FileCache;

public class AnchoredContext extends Application {

    private static AnchoredContext sAnchoredContext;
    private FileCache fileCache;

    public static AnchoredContext getInstance() {
        return sAnchoredContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAnchoredContext = this;
        fileCache = new  FileCache(this);
    }
    public FileCache getFileCache(){
        return  fileCache;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }

}
