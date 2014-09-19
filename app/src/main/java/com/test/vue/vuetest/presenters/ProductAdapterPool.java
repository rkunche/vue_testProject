package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.test.vue.vuetest.R;

import java.util.ArrayList;

/**
 * Created by advisors on 19/9/14.
 */
public class ProductAdapterPool {
    String TAG = "ProductAdapterPool";
    static ProductAdapterPool sProductAdapterPool;
    final static int PRODUCT_LIST_INITIAL_SIZE = 3;
    final static int PRODUCT_LIST_EXPAND = 3;
    private LayoutInflater mInflater;
    ArrayList<View> productLayoutList = new ArrayList<View>();

    public ProductAdapterPool(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < PRODUCT_LIST_INITIAL_SIZE; i++) {
            productLayoutList.add(mInflater.inflate(R.layout.pager_card_one, null));
        }
    }

    public static ProductAdapterPool getInstance(Context context) {
        if (sProductAdapterPool == null) {
            sProductAdapterPool = new ProductAdapterPool(context);
        }
        return sProductAdapterPool;
    }

    public View getProductLayout() {
        View view;
        if(productLayoutList.size() >= 1) {
              view = productLayoutList.remove(0);
            if (view != null) {
                return view;
            } else {
                view = expandProductLayoutList();
            }
        } else {
            view = expandProductLayoutList();
        }
        return view;
    }

    private View expandProductLayoutList() {
        for (int i = 0; i < PRODUCT_LIST_EXPAND; i++) {
            productLayoutList.add(mInflater.inflate(R.layout.pager_card_one, null));
        }
        return productLayoutList.remove(0);
    }

    public void returnUsedViewToPool(View view) {
        if (view != null) {
            productLayoutList.add(view);
            Log.i(TAG, TAG + " View returned to pool");
        } else {
            Log.i(TAG, TAG + " returned View is null");
        }
    }
}
