package com.test.vue.vuetest.models;

//Vue imports


//android and java imports

import com.test.vue.vuetest.services.networking.GetAislesRequest;
import com.test.vue.vuetest.services.networking.GetAislesResponse;
import com.test.vue.vuetest.utils.UrlConstants;

public class NetworkContentAccessor {
    private VueContentModelImpl mContentModel;
    private static NetworkContentAccessor sNetworkContentAccessor;

    private NetworkContentAccessor() {
        mContentModel = (VueContentModelImpl)VueContentModelImpl.getContentModel();
    }

    public static NetworkContentAccessor getInstance() {
        if(null == sNetworkContentAccessor) {
            sNetworkContentAccessor = new NetworkContentAccessor();
        }
        return sNetworkContentAccessor;
    }

    public void getAislesInRange(int offset, int limit) {
        StringBuilder builder = new StringBuilder();
        builder.append(UrlConstants.GET_TRENDINGAISLES_RESTURL).append("/").
                                                                append(String.valueOf(limit)).
                                                                append("/").
                                                                append(String.valueOf(offset));
        GetAislesResponse responseHandler = new GetAislesResponse();
        GetAislesRequest request = new GetAislesRequest(offset, limit, builder.toString(), responseHandler);
        responseHandler.setRequestObject(request);
        mContentModel.getRequestQueue().add(request);
    }
}
