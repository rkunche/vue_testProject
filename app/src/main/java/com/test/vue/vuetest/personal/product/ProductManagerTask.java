package com.test.vue.vuetest.personal.product;

import android.os.AsyncTask;
import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientProduct;
import com.test.vue.vuetest.personal.GenericNetWorker;
import com.test.vue.vuetest.utils.UrlConstants;

import junit.framework.Assert;


public class ProductManagerTask extends AsyncTask<Void,Void,ClientProduct> {
    private static final String TAG = "productMangerTask";
    public static final int CREATE_PRODUCT = 0;
    public static final int UPDATE_PRODUCT = 1;
    public static final int GET_PRODUCT = 2;
    public static final int DELETE_PRODUCT = 3;


    GenericNetWorker<ClientProduct> clientProductHelper;
    ProductHelper productHelper;
   public  ProductManagerTask(ProductHelper productHelper){

       this.productHelper = productHelper;
       clientProductHelper = new GenericNetWorker<ClientProduct>(ClientProduct.class);
    }
    @Override
    protected ClientProduct doInBackground(Void... voids) {
        try{
            String url;
            switch (productHelper.requestType) {

                case CREATE_PRODUCT:
                    url = UrlConstants.CREATE_PRODUCT_RESTURL;
                    Assert.assertNotNull(productHelper.clientProduct);
                    productHelper.clientProduct =  clientProductHelper.createObject(productHelper.clientProduct,url);
                    return  productHelper.clientProduct;

                case UPDATE_PRODUCT: break;

                case GET_PRODUCT:

                    url = UrlConstants.GET_PRODUCT_RESTURL;
                    Assert.assertNotNull(productHelper.clientProduct.getId());
                    productHelper.clientProduct =  clientProductHelper.getObject(url,productHelper.clientProduct.getId());
                    break;

                case DELETE_PRODUCT:
                    url = UrlConstants.DELETE_PRODUCT_RESTURL;
                    Assert.assertNotNull(productHelper.clientProduct.getId());
                   clientProductHelper.deleteObject(productHelper.clientProduct.getId(),url);
                    break;
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ClientProduct clientProduct) {
        super.onPostExecute(clientProduct);
        if(clientProduct != null){
            Log.i(TAG,TAG+" "+clientProduct.getDescription());
        } else {
            Log.i(TAG,TAG+" Product null ");
        }
    }
}
