package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.models.VueContentModelImpl;
import com.test.vue.vuetest.personal.OnResult;
import com.test.vue.vuetest.personal.aisle.AisleManager;
import com.test.vue.vuetest.personal.aisle.AisleWindow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class DataAdapter extends BaseAdapter implements DataContainer {
    //ArrayList<AisleWindowContent> mAisleWindowList = new ArrayList<AisleWindowContent>();
 VueContentModelImpl mVueContentModel;
    ArrayList<ClientAisle> windowList = new ArrayList<ClientAisle>();

    DataAdapter(Context context) {
        mVueContentModel = (VueContentModelImpl) VueContentModelImpl.getContentModel();
        mVueContentModel.setDataRegisterListener(this);
        ClientAisle tempAisle = new ClientAisle();
        windowList.add(tempAisle);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        // return mAisleWindowList.size();
        return windowList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyAdapters() {
        notifyDataSetChanged();

    }
    
   /* @Override
    public void addMoreData(ArrayList<AisleWindowContent> aisleList) {
        mAisleWindowList.addAll(aisleList);
        notifyDataSetChanged();
    }*/

    @Override
    public void clearAllData() {
        //mAisleWindowList.clear();
        notifyDataSetChanged();

    }

    @Override
    public void addMoreData(ArrayList<ClientAisle> aisleList) {
        if(windowList.size() == 1){
            windowList.remove(0);
        }
        windowList.addAll(aisleList);
        messageHandler.sendEmptyMessage(0);

    }

    public void getData(){
        AisleManager aisleManager = new AisleManager();
        WeakReference<AisleManager> aisleManagerWeakReference = new WeakReference<AisleManager>(aisleManager);

         aisleManager.retrieveAisleByUser(1234123432434L,new ResultCallBack());
    }
    private class  ResultCallBack implements OnResult {
        @Override
        public void onResultComplete(boolean status, Object object) {
            if(status) {
                ArrayList<ClientAisle> clientAisle = ( ArrayList<ClientAisle>) object;


                for(int i=0;i<clientAisle.size();i++) {
                    if(clientAisle.get(i).getProductList() != null && clientAisle.get(i).getCurrentAisleState() != ClientAisle.AisleStateEnum.DELETED ) {
                        AisleWindow aisleWindow = new AisleWindow( clientAisle.get(i));
                        aisleWindow.setAisleData();
                        //windowList.add(aisleWindow);
                    }
                }
                Log.i("aisleSize","aisle Size: "+windowList.size());
                 clientAisle = null;
               notifyAdapters();
            } else {

            }
        }
    }
    private Handler messageHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            notifyAdapters();
        }
    };
}
