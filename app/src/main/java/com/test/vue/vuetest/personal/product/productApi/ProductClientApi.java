package com.test.vue.vuetest.personal.product.productApi;

import com.test.vue.vuetest.utils.UrlConstants;

/**
 * Created by advisors on 25/8/14.
 */
public class ProductClientApi implements ProductApiInterface {
    @Override
    public String getUrl() {
        return UrlConstants.GET_PRODUCT_RESTURL;
    }

    @Override
    public String createUrl() {
        return UrlConstants.CREATE_PRODUCT_RESTURL;
    }

    @Override
    public String updateUrl() {
        return UrlConstants.UPDATE_PRODUCT_RESTURL;
    }

    @Override
    public String deleteUrl() {
        return UrlConstants.DELETE_PRODUCT_RESTURL;
    }
}
