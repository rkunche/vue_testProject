package com.test.vue.vuetest.services.networking;

//android and java imports
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.HashMap;
import java.util.Map;

public class GetAislesRequest extends JsonArrayRequest {
    private int mOffset;
    private int mLimit;

    public GetAislesRequest(int offset, int limit, String url, GetAislesResponse responseHandler) {//Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, responseHandler, responseHandler);
        mOffset = offset;
        mLimit = limit;
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Accept-Encoding", "gzip");
        headerMap.put("Content-Type", "application/json");
        return headerMap;
    }

    public int getOffset() {
        return mOffset;
    }

    public int getLimit() {
        return mLimit;
    }
}
