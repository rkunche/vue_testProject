package com.test.vue.vuetest.personal.product;

import android.util.Log;

import com.test.vue.vuetest.domain.ProductBase;
import com.test.vue.vuetest.domain.client.ClientProduct;
import com.test.vue.vuetest.personal.OnResult;


public class ProductManager {
       private String logMessage;
       private final String TAG = "ProductManager";
    public void createClientProduct(){
        logMessage = "CreateClientProduct";
        ClientProduct clientProduct =  createProduct();
        ProductHelper productHelper = new ProductHelper();
        productHelper.requestType = ProductManagerTask.CREATE_PRODUCT;
        productHelper.callback = new ResultCallBack();
        productHelper.clientProduct = clientProduct;
        callProductTask(productHelper);
    }
    public void updateClientProduct(){
        logMessage = "UpdateClientProduct";

    }
    public void getClientProduct(){
        logMessage = "GetClientProduct";
        ProductHelper productHelper = new ProductHelper();
        productHelper.requestType = ProductManagerTask.GET_PRODUCT;
        productHelper.callback = new ResultCallBack();
        callProductTask(productHelper);

    }
    public void deleteClientProduct(){
        logMessage = "DeleteClientProduct";
        ProductHelper productHelper = new ProductHelper();
        productHelper.requestType = ProductManagerTask.DELETE_PRODUCT;
        productHelper.callback = new ResultCallBack();
        callProductTask(productHelper);

    }

    private ClientProduct createProduct()
    {
        ClientProduct product = new ClientProduct();
        product.setDescription("Dummy description");
        product.setOwnerAisleId(Long.valueOf("6325587836665856"));
        product.setCurrentProductState(ProductBase.ProductStateEnum.USER_CREATED);
        product.setTitle("Test product");
        return product;
    }
    private class  ResultCallBack implements OnResult {
        @Override
        public void onResultComplete(boolean status) {
            if(status) {
                Log.i(TAG, TAG + " " + logMessage+" Success");
            } else {
                Log.i(TAG, TAG + " " + logMessage+" Failed");
            }
        }
    }

    /**
     *
     * background task for executing the user request.
     */
    private void callProductTask( ProductHelper helper){
        ProductManagerTask productManagerTask = new ProductManagerTask(helper);
        productManagerTask.execute();
    }
}
