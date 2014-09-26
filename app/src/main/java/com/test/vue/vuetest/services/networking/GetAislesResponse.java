package com.test.vue.vuetest.services.networking;

//Vue imports

import android.os.Message;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.test.vue.vuetest.models.VueContentModelImpl;
import com.test.vue.vuetest.presenters.CardFragment;
import com.test.vue.vuetest.utils.logs.Logging;


import org.json.JSONArray;

import java.lang.ref.WeakReference;

//android and java imports

public class GetAislesResponse implements Response.Listener<JSONArray>,
                                          Response.ErrorListener {
   private final String TAG = "GetAislesResponse";
   private boolean classLevelLogEnabled = true;
    WeakReference<GetAislesRequest> mWeakRequest;

    public GetAislesResponse () {

    }

    public void setRequestObject (GetAislesRequest request) {
        mWeakRequest = new WeakReference<GetAislesRequest>(request);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        volleyError.getMessage();
        //To change body of implemented methods use File | Settings | File Templates.
        Logging.i(TAG, "AisleGET form server failed", false,classLevelLogEnabled);
        CardFragment.sRequestForMoreAisles = false;
        volleyError.printStackTrace();
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        CardFragment.sRequestForMoreAisles = false;
        Logging.i(TAG, "AisleGET form server successfully", false,classLevelLogEnabled);
        if(mWeakRequest.get() != null) {
            Message msg = new Message();
            msg.what = VueContentModelImpl.AISLE_DATA_NOTIFY;
            msg.arg1 = mWeakRequest.get().getOffset();
            msg.arg2 = mWeakRequest.get().getLimit();
            msg.obj = jsonArray;
            VueContentModelImpl.sendMessage(msg);
        }
    }
}
