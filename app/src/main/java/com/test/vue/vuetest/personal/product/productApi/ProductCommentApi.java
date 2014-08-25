package com.test.vue.vuetest.personal.product.productApi;

import com.test.vue.vuetest.personal.product.productApi.ProductApiInterface;
import com.test.vue.vuetest.utils.UrlConstants;

/**
 * Created by advisors on 23/8/14.
 */
public class ProductCommentApi implements ProductApiInterface {
    @Override
    public String getUrl() {
        return UrlConstants.GET_PRODUCTCOMMENT_RESTURL;
    }

    @Override
    public String createUrl() {
        return UrlConstants.CREATE_PRODUCTCOMMENT_RESTURL;
    }

    @Override
    public String updateUrl() {
        return UrlConstants.UPDATE_PRODUCTCOMMENT_RESTURL;
    }

    @Override
    public String deleteUrl() {
        return UrlConstants.DELETE_PRODUCTCOMMENT_RESTURL;
    }
}
