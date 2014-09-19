package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.test.vue.vuetest.models.ImageLoaderTask;


public class ProductCustomImageVeiw extends ImageView {
    ImageLoaderTask task;
    public ProductCustomImageVeiw(Context context) {
        super(context);
        init();
    }

    public ProductCustomImageVeiw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProductCustomImageVeiw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }
    public void setWorkerTaskObject(ImageLoaderTask task){
        this.task = task;
    }
    public  ImageLoaderTask getTask(){
        return  task;
    }
}
