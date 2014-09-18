package com.test.vue.vuetest.personal.aisle;

import android.util.Log;

import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.domain.client.ClientProduct;
import com.test.vue.vuetest.domain.client.ClientProductImage;

import java.util.ArrayList;

/**
 * Created by advisors on 17/9/14.
 */
public class AisleWindow {
    ClientAisle aisle;
    int windowMaxHeight;
    String mAisleOwnerProfilePicUrl;
    String mAisleTitle;
    String mAisleOwnerName;
    String mProductPrice;
    String mProductSource;
    String mProductSuggesterName;
   public  ArrayList<ClientProductImage> imageList;

    public AisleWindow(ClientAisle aisle){
        this.aisle = aisle;
    }
    public void setAisleData(){
       ArrayList<ClientProduct> productList;
       imageList = new ArrayList<ClientProductImage>();
        productList = (ArrayList<ClientProduct>)aisle.getProductList();
        if(productList != null){
            for(int i=0;i<productList.size();i++){
                imageList.addAll(productList.get(i).getProductImages());
            }
        }
        for(int j=0;j<imageList.size();j++){
            Log.i("image","image width: "+imageList.get(j).getOrignalWidth()+" height: "+imageList.get(j).getOrignalHeight());
            Log.i("image","image url:"+imageList.get(j).getExternalURL() );
        }
    }
}
