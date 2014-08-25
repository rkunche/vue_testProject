package com.test.vue.vuetest.presenters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.client.ClientUser;
import com.test.vue.vuetest.login.VueFacebookLoginActivity;
import com.test.vue.vuetest.personal.aisle.AisleManager;
import com.test.vue.vuetest.personal.product.ImageManager;
import com.test.vue.vuetest.personal.product.ProductManager;
import com.test.vue.vuetest.personal.product.ProviderManager;
import com.test.vue.vuetest.personal.user.UserManager;
import com.test.vue.vuetest.utils.Utils;
import com.test.vue.vuetest.utils.VueConstants;


public class CardFragment extends Fragment {

    private ListView mCardList;
    private CardWithFlipper mCard;
    public static boolean sIsListScrolling;
    public static boolean sIsTouchScrollingCall;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment_listview_holder, null);
        mCardList = (ListView) view.findViewById(R.id.card_list);
        //create adapter for card fragment.
        mCard = new CardWithFlipper(getActivity());
        //add header view to the list.
        View cardHeaderView = inflater.inflate(
                 R.layout.card_fragment_listview_header, null);
        //add header view to the list.
        RelativeLayout facebook_id = (RelativeLayout) cardHeaderView.findViewById(R.id.facebook_id);
        RelativeLayout gPlus_LayoutId = (RelativeLayout) cardHeaderView.findViewById(R.id.g_plus__layout_id);
        facebook_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),VueFacebookLoginActivity.class);
                startActivity(intent);
            }
        });
        gPlus_LayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Intent intent = new Intent(getActivity(),GooglePlayServicesActivity.class);
               // startActivity(intent);
            }
        });
        mCardList.addHeaderView(cardHeaderView);
        mCardList.setAdapter(mCard);
        mCardList.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        sIsListScrolling = true;
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        sIsTouchScrollingCall = true;
                        break;
                    case SCROLL_STATE_IDLE:
                        sIsListScrolling = false;
                        sIsTouchScrollingCall = false;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        });
        return view;
    }

}
