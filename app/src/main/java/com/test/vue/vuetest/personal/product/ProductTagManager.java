package com.test.vue.vuetest.personal.product;


import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientProductProvider;
import com.test.vue.vuetest.domain.client.ClientProductTag;
import com.test.vue.vuetest.personal.OnResult;
import com.test.vue.vuetest.personal.product.ProductHelper;
import com.test.vue.vuetest.personal.product.ProductManagerTask;

public class ProductTagManager {
    String logMessage;
    final String TAG = "ProductTagManager" ;
    ProductHelper productHelper;
    public  ProductTagManager(){
        productHelper = new ProductHelper();
        productHelper.callback = new ResultCallBack();
     }
     public void createClientProductTag(ClientProductTag clientProductTag){
         logMessage ="createClientProductTag";
         productHelper.object = clientProductTag;
         productHelper.requestType = ProductManagerTask.CREATE_PRODUCT;
         productHelper.type = ClientProductTag.class;
         callProductTask(productHelper);
     }
     public void updateClientProductTag(ClientProductTag clientProductTag){
         logMessage ="updateClientProductTag";
         productHelper.object = clientProductTag;
         productHelper.requestType = ProductManagerTask.UPDATE_PRODUCT;
         productHelper.type = ClientProductTag.class;
         callProductTask(productHelper);
     }
     public void deleteProductTag(){
         logMessage ="deleteProductTag";
         productHelper.requestType = ProductManagerTask.DELETE_PRODUCT;
         productHelper.type = ClientProductTag.class;
         callProductTask(productHelper);

     }
    /**
     *
     * background task for executing the user request.
     */
    private void callProductTask( ProductHelper helper){
        ProductManagerTask productManagerTask = new ProductManagerTask(helper);
        productManagerTask.execute();
    }
    private class  ResultCallBack implements OnResult {
        @Override
        public void onResultComplete(boolean status,Object object) {
            if(status) {
                //ClientProductTag clientTag = (ClientProductTag) object;
                Log.i(TAG, TAG + " " + logMessage + " Success");
            } else {
                Log.i(TAG, TAG + " " + logMessage+" Failed");

            }
        }
    }
}
