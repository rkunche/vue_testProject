package com.test.vue.vuetest.services.networking;

public abstract class GenericJsonRequest {
    public enum RequestType {
        GET,
        POST,
        PUT,
        DELETE
    };

    public RequestType getRequestType() {
        return RequestType.GET;
    }
}
