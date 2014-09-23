package com.test.vue.vuetest.presenters;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.ProductBase;
import com.test.vue.vuetest.domain.ProductProviderBase;
import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.domain.client.ClientProduct;
import com.test.vue.vuetest.domain.client.ClientProductImage;
import com.test.vue.vuetest.domain.client.ClientProductProvider;
import com.test.vue.vuetest.domain.client.ClientProductTag;
import com.test.vue.vuetest.login.VueFacebookLoginActivity;
import com.test.vue.vuetest.personal.aisle.AisleManager;
import com.test.vue.vuetest.personal.user.SaveUser;

import java.util.ArrayList;



public class CardFragment extends Fragment {

    private ListView mCardList;
    private CardWithFlipper mCard;
    public static boolean sIsListScrolling;
    public static boolean sIsTouchScrollingCall;

  //  private PullToRefreshLayout mPullToRefreshLayoutTemp;

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
        RelativeLayout gPlus_LayoutId = (RelativeLayout) cardHeaderView.findViewById(R.id.g_plus_layout_id);
        facebook_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VueFacebookLoginActivity.class);
                startActivity(intent);
            }
        });
        gPlus_LayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(getActivity(),GooglePlayServicesActivity.class);
                // startActivity(intent);
                AisleManager productManager = new AisleManager();
                // productManager.retrieveAisleByUser(6419807607980032L);
            }
        });
        // mCardList.addHeaderView(cardHeaderView);
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

/*    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewGroup viewGroup = (ViewGroup) view;

        // We need to create a PullToRefreshLayout manually
        mPullToRefreshLayoutTemp = new PullToRefreshLayout(
                viewGroup.getContext());

        Log.v("CardFragment", "onViewCreated is created");

        // We can now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())

                // We need to insert the PullToRefreshLayout into the Fragment's
                // ViewGroup
                .insertLayoutInto(viewGroup)

                        // We need to mark the ListView and it's Empty View as pullable
                        // This is because they are not dirent children of the ViewGroup
                .theseChildrenArePullable(android.R.id.empty,
                        android.R.id.empty)

                        // We can now complete the setup as desired
                .listener(new RefListener()).setup(mPullToRefreshLayoutTemp);
        //if (!callbacksCompleted)
        //    mPullToRefreshLayoutTemp.setRefreshing(true);

    }*/

 /*   private class RefListener implements OnRefreshListener {

        @Override
        public void onRefreshStarted(View view) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(5000); // 5 seconds


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    super.onPostExecute(result);


                    // Notify PullToRefreshLayout that the refresh has finished
                    mPullToRefreshLayoutTemp.setRefreshComplete();

                    // if you set the "setListShown(false)" then you have to
                    //uncomment the below code segment

//                        if (getView() != null) {
//                            // Show the list again
//                            setListShown(true);
//                        }
                }
            }.execute();
        }

    }*/
    /**
     * test code
     */
    private ClientProduct createProduct() {
        ClientProduct product = new ClientProduct();
        product.setDescription("Need a Shirt");
        product.setOwnerAisleId(Long.valueOf("5732910535540736"));
        product.setCurrentProductState(ProductBase.ProductStateEnum.USER_CREATED);
        product.setTitle("White Shirts");
        return product;
    }

    private ClientProductTag createClientProductTag() {
        ClientProductTag productTag = new ClientProductTag();
        productTag.setTagCategory("PRODUCT_TYPE");
        productTag.setTagString("Dress");
        productTag.setTagSubCategory(null);
        return productTag;
    }

    /**
     * test code.
     */
    private ClientProduct updateClientProductObject() {
        ClientProduct product = new ClientProduct();
        product.setId(5748418722922496L);
        product.setOwnerProductListId(null);
        product.setOwnerAisleId(5732910535540736L);
        product.setOrignalCreatorId(null);
        product.setDescription("shirts blue,white");
        product.setCurrentProductState(ProductBase.ProductStateEnum.CURATED);
        product.setCurrentProductState(ProductBase.ProductStateEnum.USER_CREATED);
        product.setTitle("Mobile shopping");
        ArrayList<ClientProductTag> images = new ArrayList<ClientProductTag>();
        images.add(getClientProductTag());
        product.setProductTags(images);
        return product;
    }

    /**
     * test code to be deleted later.
     */
    private ClientProductImage getSampleImage() {
        ClientProductImage image = new ClientProductImage();
        image.setDescription("imageDescripiton");
        image.setExternalURL("http://ecx.images-amazon.com/images/I/91kHfDTB1-L._UL1500_.jpg");
        image.setOrignalHeight(400f);
        image.setOrignalWidth(400f);
        image.setModifiedHeight(583f);
        image.setModifiedWidth(380f);
        image.setOwnerProductId(5308304431513600L);
        return image;
    }

    private ClientProductTag getClientProductTag() {
        ClientProductTag clientProductTag = new ClientProductTag();
        clientProductTag.setTagCategory("Occassion");
        clientProductTag.setTagString("Dress");
        clientProductTag.setTagSubCategory(null);
        return clientProductTag;
    }

    /**
     * test code to be deleted.
     */
    private ClientAisle getSampleAisle() {
        long userId = SaveUser.getUserFromFile().getId();
        ClientAisle clientAisle = new ClientAisle();
        clientAisle.setOwnerUserId(userId);
        clientAisle.setLookingFor("marriage suit");
        clientAisle.setCategory("Party");
        clientAisle.setName("raju's party suit aisle");
        clientAisle.setDescription("Pary shirt");
        return clientAisle;
    }

    /**
     *
     */
    private ClientProductProvider getProvider() {

        ClientProductProvider provider = new ClientProductProvider();
        provider.setExternalURL("http://shop.nordstrom.com/s/bb-dakota-renley-lace-fit-flare-dress-nordstrom-exclusive/3524970?origin=category-personalizedsort&contextualcategoryid=0&fashionColor=Sapphire&resultback=196&cm_sp=personalizedsort-_-browseresults-_-1_1_A");
        provider.setCurrencyCode("USD");
        provider.setAvailability(ProductProviderBase.ProductAvailabilityEnum.IN_STOCK);
        provider.setPrice(88.00);
        provider.setOnSale(false);
        provider.setStore("Nordstrom");
        return provider;
    }
}
