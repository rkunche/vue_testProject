package com.test.vue.vuetest.personal.aisle;

import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.personal.OnResult;
import com.test.vue.vuetest.personal.user.SaveUser;

public class AisleManager {
    private String mLogMessage;
    private String TAG = "AisleManager";
    /**
     *
     */
    public void createAisle(ClientAisle clientAisle){
        mLogMessage = "createAisle";
        AisleHelper aisleHelper = new AisleHelper();
        aisleHelper.requestType = AisleManagerTask.CREATE_AISLE;
        aisleHelper.callback = new ResultCallBack();
        aisleHelper.clientAisle = clientAisle;
        callAisleTask(aisleHelper);
    }

    /**
     *
     *
     */
    public void retrieveAisleByUser(){
        mLogMessage = "retrieveAisle";
        AisleHelper aisleHelper = new AisleHelper();
        aisleHelper.requestType = AisleManagerTask.GET_AISLES_BY_USER;
        aisleHelper.callback = new ResultCallBack();
        callAisleTask(aisleHelper);

    }

    /**
     *
     */
    public void updateAisle(ClientAisle updateAisle){
        mLogMessage = "updateAisle";
        AisleHelper aisleHelper = new AisleHelper();
        aisleHelper.requestType = AisleManagerTask.UPDATE_AISLE;
        aisleHelper.callback = new ResultCallBack();
        aisleHelper.clientAisle = updateAisle;
        callAisleTask(aisleHelper);
    }

    /**
     *
     */
    public void deleteAisle(Long aisleId){
        mLogMessage = "deleteAisle";
        AisleHelper aisleHelper = new AisleHelper();
        aisleHelper.requestType = AisleManagerTask.DELETE_AISLE;
        aisleHelper.callback = new ResultCallBack();
        aisleHelper.aisleId = aisleId;
        callAisleTask(aisleHelper);
    }

    /**
     *
     */
    private ClientAisle getSampleAisle(){
      long userId = SaveUser.getUserFromFile().getId();
        ClientAisle clientAisle = new ClientAisle();
        clientAisle.setOwnerUserId(userId);
        clientAisle.setLookingFor("marriage suit");
        clientAisle.setCategory("Party");
        clientAisle.setName("raju's party suit aisle");
        clientAisle.setOwnerUserId(userId);
        clientAisle.setDescription("Creating a test aisle for unit test");
        return clientAisle;
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
        public void onResultComplete(boolean status) {
            if(status) {
                Log.i(TAG, TAG + " " + mLogMessage + " Success");
            } else {
                Log.i(TAG, TAG + " " + mLogMessage+" Failed");
            }
        }
    }
}
