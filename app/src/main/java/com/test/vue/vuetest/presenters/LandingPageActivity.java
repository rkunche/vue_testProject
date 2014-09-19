package com.test.vue.vuetest.presenters;

//internal Vue imports

//android imports

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.models.VueContentModelImpl;
import com.test.vue.vuetest.services.logging.Logger;
import com.test.vue.vuetest.services.sidekick.PersistentWatcher;
import com.test.vue.vuetest.utils.VueConstants;


/**
 * This is the landing page activity for Vue. By and large we should keep this activity simple. The purpose of this activity
 * is to determine exactly where to take the user in the Vue experience and also to determine whether any prerequisites we have
 * are already taken care of. Here are some examples of valid things we can do here:
 * 1. Check to see if this is the first time that Vue is launched on this device. If so, we might want to redirect the user to a quick
 * tour of the app before redirecting them to the actual Vue Experience
 * 2. Check to see if the app was launched because the user clicked on notifications - maybe we might redirect them to a new activity in that case
 */

public class LandingPageActivity extends FragmentActivity implements Trending_Menu_Fragment.OnFragmentInteractionListener {
    private CardFragment mLandingAislesFrag;
    private GoogleApiClient mGoogleApiClient;

    private TextView trending_list;
    private boolean mTrendingFragLoaded = false;
    private  Fragment  mTrendingFragment;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Logger.console("onCreate of LandingPage invoked", "VueDebug");
        setContentView(R.layout.landing_page);
        VueContentModelImpl.getContentModel();
        setActionBar();
        Message message = new Message();
        message.what = VueContentModelImpl.AISLE_DATA_GET;
        VueContentModelImpl.sendMessage(message);
        boolean isAvailable = arePlayServicesAccessible();
        Intent watcherServiceIntent = new Intent(this, PersistentWatcher.class);
        watcherServiceIntent.putExtra(PersistentWatcher.REASON, PersistentWatcher.START_SIDEKICK);
        startService(watcherServiceIntent);
        //load cards fragment into screen.
        loadVueCardsFragment();
        getDeviceId();
    }

    private void setActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LayoutInflater mInflater = LayoutInflater.from(this);

       // View mCustomView = mInflater.inflate(R.layout.custom_actionbar,null);
        actionBar.setCustomView(R.layout.custom_actionbar);
        trending_list = (TextView) actionBar.getCustomView().findViewById(R.id.my_feed_text_id);
        trending_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mTrendingFragLoaded){
                    mTrendingFragLoaded = true;
                    AddTrendingFrag();
                }else {
                    RemoveTrendingFrag();
                    mTrendingFragLoaded = false;
                }

            }
        });


    }

    private void RemoveTrendingFrag() {
        if (mTrendingFragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_left,
                    R.animator.slide_out_right);
            transaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.remove(mTrendingFragment);
            transaction.commit();
        }
    }

    private void AddTrendingFrag() {
        mTrendingFragment = new Trending_Menu_Fragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left,
                R.animator.slide_out_right);
        transaction
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.trending_frag, mTrendingFragment);
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.console("VueDebug", "onResume of LandingPage invoked");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.console("VueDebug", "onPause of LandingPage invoked");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.console("VueDebug", "onStop of LandingPage invoked");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.console("VueDebug", "onDestroy of LandingPage invoked");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class LocationErrorFragment extends DialogFragment {

        private Dialog mDialog;

        public LocationErrorFragment() {
            super();
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceData) {
            return mDialog;
        }

    }

    private boolean arePlayServicesAccessible() {
        int isAccessible = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        boolean returnCode = false;
        if (ConnectionResult.SUCCESS == isAccessible) {
            returnCode = true;
        } else {
            //TODO: we need a way to catch errors

        }
        return returnCode;
    }

    /**
     * Attaching the cards fragment to main screen, which holds the landing screen scrolling cards view fragment.
     */
    private void loadVueCardsFragment() {
        mLandingAislesFrag = new CardFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, mLandingAislesFrag).commit();
    }
     public void getDeviceId(){
         TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
         VueConstants.deviceId = telephonyManager.getDeviceId();
     }
}
