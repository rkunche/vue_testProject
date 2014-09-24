package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.models.ListDataContainer;
import com.test.vue.vuetest.models.VueContentModelImpl;

import java.util.ArrayList;


public class DataAdapter extends BaseAdapter implements DataContainer {

    private VueContentModelImpl mVueContentModel;
    protected ListDataContainer listListDataContainer;


    DataAdapter(Context context) {
        mVueContentModel = (VueContentModelImpl) VueContentModelImpl.getContentModel();
        mVueContentModel.setDataRegisterListener(this);
        listListDataContainer = ListDataContainer.getInstance();
    }

    public int getCount() {
        return listListDataContainer.getWindowList().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void notifyAdapters() {
        notifyDataSetChanged();

    }

    @Override
    public void clearAllData() {
        notifyDataSetChanged();

    }

    @Override
    public void addMoreData(ArrayList<ClientAisle> aisleList) {
        if (listListDataContainer.getWindowList().size() == 1) {
            listListDataContainer.getWindowList().remove(0);
        }
        listListDataContainer.getWindowList().addAll(aisleList);
        messageHandler.sendEmptyMessage(0);

    }

    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyAdapters();
        }
    };
}
