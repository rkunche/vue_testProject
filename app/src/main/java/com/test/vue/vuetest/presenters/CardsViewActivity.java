package com.test.vue.vuetest.presenters;

import android.app.Activity;
import android.os.Bundle;

import com.test.vue.vuetest.R;
import com.test.vue.vuetest.models.VueContentModelImpl;

public class CardsViewActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.landing_page);
    }

    @Override
    public void onResume() {
        super.onResume();
        VueContentModelImpl.getContentModel().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        VueContentModelImpl.getContentModel().onPause();
    }
}
