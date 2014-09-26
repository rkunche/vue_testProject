package com.test.vue.vuetest.personal.product;

import android.os.AsyncTask;
import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientProduct;
import com.test.vue.vuetest.domain.client.ClientProductImage;
import com.test.vue.vuetest.domain.client.ClientProductProvider;
import com.test.vue.vuetest.domain.client.ClientProductTag;
import com.test.vue.vuetest.personal.GenericNetWorker;
import com.test.vue.vuetest.personal.product.productApi.ProductApiConnector;
import com.test.vue.vuetest.personal.product.productApi.ProductApiInterface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Generic Async task for all product types(product,image,providers,comment,ratings.
 */
public class ProductManagerTask extends AsyncTask<Void,Void,Object> {
    private static final String TAG = "productMangerTask";
    public static final int CREATE_PRODUCT = 0;
    public static final int UPDATE_PRODUCT = 1;
    public static final int GET_PRODUCT = 2;
    public static final int DELETE_PRODUCT = 3;
    private ProductHelper productHelper;
    private ProductApiInterface api;
   public  ProductManagerTask(ProductHelper productHelper){
       this.productHelper = productHelper;
       api = ProductApiConnector.getUrlInterface(productHelper.type);
    }
    @Override
    protected Object doInBackground(Void... voids) {
        try{
            String url;
            switch (productHelper.requestType) {

                case CREATE_PRODUCT:
                      url =    api.createUrl();
                    if(productHelper.type.equals(ClientProduct.class)) {
                        GenericNetWorker<ClientProduct> clientProductHelper = new GenericNetWorker<ClientProduct>(ClientProduct.class);
                         ClientProduct clientProduct = (ClientProduct) productHelper.object;
                        productHelper.object = clientProductHelper.createObject(clientProduct, url);
                    } else if(productHelper.type.equals(ClientProductImage.class)){
                        GenericNetWorker<ClientProductImage> clientProductHelper = new GenericNetWorker<ClientProductImage>(ClientProductImage.class);
                        ClientProductImage clientProductImage = (ClientProductImage) productHelper.object;
                        productHelper.object = clientProductHelper.createObject(clientProductImage, url);
                    } else if(productHelper.type.equals(ClientProductProvider.class)){
                        GenericNetWorker<ClientProductProvider> clientProductHelper = new GenericNetWorker<ClientProductProvider>(ClientProductProvider.class);
                        ClientProductProvider clientProductProvider = (ClientProductProvider) productHelper.object;
                        productHelper.object = clientProductHelper.createObject(clientProductProvider, url);
                    } else if(productHelper.type.equals(ClientProductTag.class)){
                        GenericNetWorker<ClientProductTag> clientProductHelper = new GenericNetWorker<ClientProductTag>(ClientProductTag.class);
                        ClientProductTag clientProductTag = (ClientProductTag) productHelper.object;
                        productHelper.object = clientProductHelper.createObject(clientProductTag, url);
                    }
                    return   productHelper.object;

                case UPDATE_PRODUCT:
                    url =    api.updateUrl();
                    if(productHelper.type.equals(ClientProduct.class)) {
                        GenericNetWorker<ClientProduct> clientProductHelper = new GenericNetWorker<ClientProduct>(ClientProduct.class);
                        ClientProduct clientProduct = (ClientProduct) productHelper.object;
                        productHelper.object = clientProductHelper.updateObject(clientProduct, clientProduct.getId(), url);
                    } else if(productHelper.type.equals(ClientProductImage.class)){
                        GenericNetWorker<ClientProductImage> clientProductHelper = new GenericNetWorker<ClientProductImage>(ClientProductImage.class);
                        ClientProductImage clientProductImage = (ClientProductImage) productHelper.object;
                        productHelper.object = clientProductHelper.updateObject(clientProductImage, clientProductImage.getId(), url);
                    } else if(productHelper.type.equals(ClientProductProvider.class)){
                        GenericNetWorker<ClientProductProvider> clientProductHelper = new GenericNetWorker<ClientProductProvider>(ClientProductProvider.class);
                        ClientProductProvider clientProductProvider = (ClientProductProvider) productHelper.object;
                        productHelper.object = clientProductHelper.updateObject(clientProductProvider, clientProductProvider.getId(), url);
                    }
                    return   productHelper.object;
                case GET_PRODUCT:

                    url =    api.getUrl();
                    if(productHelper.type.equals(ClientProduct.class)) {
                        GenericNetWorker<ClientProduct> clientProductHelper = new GenericNetWorker<ClientProduct>(ClientProduct.class);
                        productHelper.object = clientProductHelper.getObject(url, productHelper.id);
                    } else if(productHelper.type.equals(ClientProductImage.class)){
                        GenericNetWorker<ClientProductImage> clientProductHelper = new GenericNetWorker<ClientProductImage>(ClientProductImage.class);
                        productHelper.object = clientProductHelper.getObject(url, productHelper.id);
                    } else if(productHelper.type.equals(ClientProductProvider.class)){
                        GenericNetWorker<ClientProductProvider> clientProductHelper = new GenericNetWorker<ClientProductProvider>(ClientProductProvider.class);
                        productHelper.object = clientProductHelper.getObject(url, productHelper.id);
                    }
              return productHelper.object;
                case DELETE_PRODUCT:
                     url = api.deleteUrl();
                    if(productHelper.type.equals(ClientProduct.class)) {
                        GenericNetWorker<ClientProduct> clientProductHelper = new GenericNetWorker<ClientProduct>(ClientProduct.class);
                        clientProductHelper.deleteObject(productHelper.id,url);
                    } else if(productHelper.type.equals(ClientProductImage.class)){
                        GenericNetWorker<ClientProductImage> clientProductHelper = new GenericNetWorker<ClientProductImage>(ClientProductImage.class);
                        clientProductHelper.deleteObject(productHelper.id,url);
                    } else if(productHelper.type.equals(ClientProductProvider.class)){
                        GenericNetWorker<ClientProductProvider> clientProductHelper = new GenericNetWorker<ClientProductProvider>(ClientProductProvider.class);
                        clientProductHelper.deleteObject(productHelper.id,url);
                    } else if(productHelper.type.equals(ClientProductTag.class)) {
                       // GenericNetWorker<ClientProductTag> clientProductHelper = new GenericNetWorker<ClientProductTag>(ClientProductTag.class);
                        ArrayList<String> list = new ArrayList<String>();
                          list.add("scarf white");
                        list.add("scarf");
                        list.add("pants");
                        list.add("jeans");
                        list.add("black pants");
                        list.add("scarf white");
                        list.add("White Shirt,Blue Jeans,Tops");
                        list.add("Wedding Reception,Community Block");
                        list.add("Wedding Reception");
                        list.add("Tops,Blue Jeans,Black Shirts");
                        list.add("Wedding Reception,Community Block");
                        list.add("Wedding Reception");

                        list.add("Tops,Blue Jeans,Black Shirts");

                        list.add("Swim Suit");

                        list.add("Shirt");
                        list.add("Retirement Party,Invitation only party");
                        list.add("Jeans");
                        list.add("Invitation only party");
                        list.add("Graduation Party");

                  //  for(int i=0;i<list.size();i++) {
                        deleteObject("Graduation Party", url);
                    //}
                    }
                    break;

            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object clientProduct) {
        super.onPostExecute(clientProduct);
        if(clientProduct != null){
            if( productHelper.callback != null)
            productHelper.callback.onResultComplete(true,clientProduct);
        } else {
            if( productHelper.callback != null)
            productHelper.callback.onResultComplete(false,null);
        }
    }
    private void deleteObject(String id, String urlString) {
        printLog("deleteObject entered ");
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(urlString+"/"+id);
            printLog("deleteObject entered "+url);
            printLog("url : "+url.toString());
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                // throw some exception
                printLog(""+statusCode);
            } else {
                InputStream in =
                        new BufferedInputStream(urlConnection.getInputStream());
                String responseMessage = getStringFromInputStream(in);
                printLog("response: "+responseMessage);
            }
        }catch (MalformedURLException e) {
            // handle invalid URL
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            // handle timeout
            e.printStackTrace();
        } catch (IOException e) {
            // handle I/0
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                printLog("Connection Disconnected");
            }
        }
    }
    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    private void printLog(String message){
        if(true)
            Log.i(TAG, TAG + " " + message);
    }
}
