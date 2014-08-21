package com.test.vue.vuetest.services.networking;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.vue.vuetest.domain.client.ClientUser;
import com.test.vue.vuetest.models.VueContentModelImpl;


import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CreateUserRequest {
    ClientUser clientUser;
    String url;

    public CreateUserRequest(ClientUser clientUser,String url) {
        this.clientUser = clientUser;
        this.url = url;

    }

    public void creteNewUser() {
        Response.Listener listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String jsonArray) {
                Log.i("user creation", "user creation onResponse 1");
                if (null != jsonArray) {
                    Log.i("user creation", "user creation onResponse jsonArray");
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("user creation", "user creation error response 1");
                if (null != error.networkResponse
                        && null != error.networkResponse.data) {
                    String errorData = error.networkResponse.data.toString();
                    Log.i("user creation", "user creation   errorData " + error.networkResponse);
                    Log.i("user creation", "user creation   errorData " + error.networkResponse.data);
                    Log.i("user creation", "user creation   errorData " + errorData);
                }
            }
        };
        try {

            ObjectMapper mapper = new ObjectMapper();
            String userAsString = mapper.writeValueAsString(clientUser);
            UserCreateOrUpdateRequest request = new UserCreateOrUpdateRequest(
                    userAsString, url,
                    listener, errorListener);
            VueContentModelImpl.getContentModel().getRequestQueue().add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UserCreateOrUpdateRequest extends Request<String> {
        // ... other methods go here
        private Response.Listener<String> mListener;
        private Response.ErrorListener mErrorListener;
        private String muserAsString;
        private StringEntity mEntity;

        public UserCreateOrUpdateRequest(String userAsString, String url,
                                         Response.Listener<String> listener,
                                         Response.ErrorListener errorListener) {
            super(Method.PUT, url, errorListener);
            mListener = listener;
            mErrorListener = errorListener;
            muserAsString = userAsString;
            try {
                mEntity = new StringEntity(muserAsString);
            } catch (UnsupportedEncodingException ex) {
            }
        }

        @Override
        public String getBodyContentType() {
            return mEntity.getContentType().getValue();
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                mEntity.writeTo(bos);
            } catch (IOException e) {
                VolleyLog.e("IOException writing to ByteArrayOutputStream");
            }
            return bos.toByteArray();
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> header = new HashMap<String, String>();
            header.put("Content-Type", "application/json;charset=UTF-8");


            return header;
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String parsed;
            try {
                parsed = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }
            return Response.success(parsed,
                    HttpHeaderParser.parseCacheHeaders(response));
        }

        @Override
        protected void deliverResponse(String s) {
            mListener.onResponse(s);
        }

        @Override
        public void deliverError(VolleyError error) {
            mErrorListener.onErrorResponse(error);
        }

    }
}
