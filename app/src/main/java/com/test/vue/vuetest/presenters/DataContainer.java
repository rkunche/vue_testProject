package com.test.vue.vuetest.presenters;

import com.test.vue.vuetest.domain.client.ClientAisle;

import java.util.ArrayList;

/**
 *
 *
 *
 */
public interface DataContainer {
    public void notifyAdapters();

    public void addMoreData(ArrayList<ClientAisle> aisleList);

    public void clearAllData();
}
