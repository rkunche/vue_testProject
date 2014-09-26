package com.test.vue.vuetest.models;

//Vue imports


//android and java imports

import com.android.volley.DefaultRetryPolicy;
import com.test.vue.vuetest.services.networking.GetAislesRequest;
import com.test.vue.vuetest.services.networking.GetAislesResponse;
import com.test.vue.vuetest.utils.UrlConstants;

public class NetworkContentAccessor {
    private VueContentModelImpl mContentModel;
    private static NetworkContentAccessor sNetworkContentAccessor;
    private final int  REQUEST_TIME_OUT = 10000;

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

        builder.append("https://3dot1.vue-server-dev.appspot.com/api/aisles/trending/modifiedtime?limit=");
        builder.append(String.valueOf(limit));
        builder.append("&offset=");
        builder.append(String.valueOf(offset));
        //builder.append("https://3dot1.vue-server-dev.appspot.com/api/aisles/user/"+5769457217568768L);
       // builder.append(UrlConstants.GET_TRENDINGAISLES_RESTURL).append("/").
       //                                                         append(String.valueOf(limit)).
        //                                                        append("/").
       //                                                         append(String.valueOf(offset));
        GetAislesResponse responseHandler = new GetAislesResponse();
        GetAislesRequest request = new GetAislesRequest(offset, limit, builder.toString(), responseHandler);
        responseHandler.setRequestObject(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                REQUEST_TIME_OUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mContentModel.getRequestQueue().add(request);
    }
}
