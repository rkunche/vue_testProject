package com.test.vue.vuetest.personal.product.productApi;

import com.test.vue.vuetest.utils.UrlConstants;


public class ProductRatingApi implements ProductApiInterface {
    @Override
    public String getUrl() {
        return UrlConstants.GET_PRODUCTRATING_RESTURL;
    }

    @Override
    public String createUrl() {
        return UrlConstants.CREATE_PRODUCTRATING_RESTURL;
    }

    @Override
    public String updateUrl() {
        return UrlConstants.UPDATE_PRODUCTRATING_RESTURL;
    }

    @Override
    public String deleteUrl() {
        return UrlConstants.DELETE_PRODUCTRATING_RESTURL;
    }
}
