package com.test.vue.vuetest.presenters;


import android.content.Context;

public interface IAisleContentAdapter {
    public void setContentSource(String uniqueAisleId);

    public void releaseContentSource();

    public void setPivot(int index);

    public int getAisleItemsCount();

    public boolean setAisleContent(AisleContentBrowser contentBrowser,
                                   int currentIndex, int wantedIndex, boolean shiftPivot,
                                   int imagesCount,Context context);

    public String getAisleId();

    public String getImageId(int mCurrentIndex);


}
