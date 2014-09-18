package com.test.vue.vuetest.personal.aisle;

import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.personal.OnResult;
import com.test.vue.vuetest.personal.user.SaveUser;

import java.util.ArrayList;

public class AisleManager {
    private String mLogMessage;
    private String TAG = "AisleManager";
    AisleHelper aisleHelper;
     public AisleManager(){
         aisleHelper = new AisleHelper();
         aisleHelper.callback = new ResultCallBack();
     }
    /**
     *
     */
    public void createAisle(ClientAisle clientAisle){
        mLogMessage = "createAisle";
        aisleHelper.requestType = AisleManagerTask.CREATE_AISLE;
        aisleHelper.clientAisle = clientAisle;
        callAisleTask(aisleHelper);
    }

    /**
     *
     *
     */
    public void retrieveAisleByUser(Long id,OnResult callBack){
        mLogMessage = "retrieveAisle";
        aisleHelper.requestType = AisleManagerTask.GET_AISLES_BY_USER;
        if(callBack != null)
        aisleHelper.callback = callBack;
        callAisleTask(aisleHelper);

    }
    public void getAisleById(){
        mLogMessage = "getAisleById";
        aisleHelper.requestType = AisleManagerTask.GET_AISLES_BY_ID;
        aisleHelper.callback = new ResultCallBack();
        callAisleTask(aisleHelper);
    }
    /**
     *
     */
    public void updateAisle(ClientAisle updateAisle){
        mLogMessage = "updateAisle";
        aisleHelper.requestType = AisleManagerTask.UPDATE_AISLE;
        aisleHelper.clientAisle = updateAisle;
        callAisleTask(aisleHelper);
    }

    /**
     *
     */
    public void deleteAisle(Long aisleId){
        mLogMessage = "deleteAisle";
        aisleHelper.requestType = AisleManagerTask.DELETE_AISLE;
        aisleHelper.aisleId = aisleId;
        callAisleTask(aisleHelper);
    }


    /**
     *
     * background task for executing the user request.
     */
    private void callAisleTask( AisleHelper helper){
        AisleManagerTask aisleManagerTask = new AisleManagerTask(helper);
        aisleManagerTask.execute();
    }
    private class  ResultCallBack implements OnResult {
        @Override
        public void onResultComplete(boolean status,Object object) {
            if(status) {
           ArrayList<ClientAisle> clientAisle = ( ArrayList<ClientAisle>) object;
                ArrayList<AisleWindow> aisleWindows = new ArrayList<AisleWindow>();
                Log.i(TAG, TAG + " Aisle size is:  "+clientAisle.size() );
                for(int i=0;i<clientAisle.size();i++) {
                    if(clientAisle.get(i).getProductList() != null && clientAisle.get(i).getCurrentAisleState() != ClientAisle.AisleStateEnum.DELETED ) {
                        AisleWindow aisleWindow = new AisleWindow( clientAisle.get(i));
                        aisleWindow.setAisleData();
                        aisleWindows.add(aisleWindow);
                    }
                }
                Log.i(TAG, TAG + " " + mLogMessage + " Success");
            } else {
                Log.i(TAG, TAG + " " + mLogMessage+" Failed");
            }
        }
    }

}
