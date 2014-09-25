package com.test.vue.vuetest.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.models.ImageLoaderTask;
import com.test.vue.vuetest.uihelper.CircularImageView;
import com.test.vue.vuetest.utils.BitmapLruCache;
import com.test.vue.vuetest.utils.Utils;
import com.test.vue.vuetest.utils.logs.Logging;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public class CardWithFlipper extends DataAdapter {

    private static final String BIRTH_DAY_CARD = "birth_day_card";
    private static final String FRIENDS_CARD = "friends_card";

    private Context mContext;
    private LayoutInflater mInflater;
    private int mCardHeight;
    private SpecialCardCreator creator;
    private Typeface typeface;
    private View birthDaySpecial;
    private final String TAG = "CardWithFlipper";
    private boolean classLevelLogsEnabled = true;

    //to save the special cards and avoid the inflation when list scrolling
    private WeakHashMap<String, View> weekSpecialCards;

    private Bitmap suggesterIcon;
    private BitmapLruCache bitmapLruCache;

    CardWithFlipper(Context context) {
        super(context);
        Logging.i(TAG, "Constructor Starts AT" + System.currentTimeMillis(), false, classLevelLogsEnabled);
        mContext = context;
        mCardHeight = Utils.getMaxCardHeight(context);
        weekSpecialCards = new WeakHashMap<String, View>();
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resources resources = mContext.getResources();
        creator = new SpecialCardCreator(mContext);
        birthDaySpecial = mInflater.inflate(R.layout.surprice_card, null);
        WeakReference<View> bSpecialCardRef = new WeakReference<View>(birthDaySpecial);
        weekSpecialCards.put(BIRTH_DAY_CARD, birthDaySpecial);
        //   View friendsList = creator.createFriendsListCard();
        View friendsList = mInflater.inflate(R.layout.friends_list_spl_card, null);

        CircularImageView img1 = (CircularImageView) friendsList.findViewById(R.id.friend_1);
        img1.setImageResource(R.drawable.vuetest);

        CircularImageView img2 = (CircularImageView) friendsList.findViewById(R.id.friend_2);
        img2.setImageResource(R.drawable.vuetest);

        CircularImageView img3 = (CircularImageView) friendsList.findViewById(R.id.friend_3);
        img3.setImageResource(R.drawable.vuetest);
        typeface = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Regular.ttf");


        WeakReference<View> friendsListRef = new WeakReference<View>(friendsList);
        weekSpecialCards.put(FRIENDS_CARD, friendsList);
        suggesterIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.vuetest);

        Logging.i(TAG, "Constructor Ends AT "+System.currentTimeMillis(), false,classLevelLogsEnabled);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CardViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CardViewHolder();
            convertView = mInflater.inflate(R.layout.card_flipper, null);
            viewHolder.aisleContentBrowser = (AisleContentBrowser) convertView
                    .findViewById(R.id.horizontal_flipper);
            viewHolder.productSuggesterPic = (ImageView) convertView.findViewById(R.id.product_suggester_image_id);
            viewHolder.suggesterLayout = (RelativeLayout) convertView.findViewById(R.id.product_suggester_lay_id);
            viewHolder.commentsShowId = (RelativeLayout) convertView.findViewById(R.id.product_comments_show_id);
            viewHolder.productAddPhotoLayId = (RelativeLayout) convertView.findViewById(R.id.product_add_photo_lay_id);
            viewHolder.aisleCard = (RelativeLayout) convertView.findViewById(R.id.aisle_card);
            viewHolder.specialCard = (RelativeLayout) convertView.findViewById(R.id.special_card);
            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
            viewHolder.productCreateAisle = (RelativeLayout) convertView.findViewById(R.id.card_create_aisle_id);
            viewHolder.productMenuId = (RelativeLayout) convertView.findViewById(R.id.product_menu_id);
            viewHolder.aisleSettings = (RelativeLayout) convertView.findViewById(R.id.aisle_card_user_window_id);
            viewHolder.aisleCardUserNameId = (TextView) convertView.findViewById(R.id.aisle_card_user_name_id);
            viewHolder.cardUserHeadingId = (TextView) convertView.findViewById(R.id.card_user_heading_id);
            viewHolder.productSuggesterText = (TextView) convertView.findViewById(R.id.product_suggest_text_id);
            viewHolder.productSuggesterName = (TextView) convertView.findViewById(R.id.product_card_suggester_name);

            // set the params based on the best image height in the aisle.
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    android.widget.RelativeLayout.LayoutParams.MATCH_PARENT, mCardHeight);
            params.setMargins(Utils.getPixel(mContext, 16), 0, Utils.getPixel(mContext, 16), 0);
            viewHolder.aisleContentBrowser.setLayoutParams(params);

            viewHolder.cardUserHeadingId.setTypeface(typeface);
            viewHolder.aisleCardUserNameId.setTypeface(typeface);
            viewHolder.productSuggesterText.setTypeface(typeface);
            viewHolder.productSuggesterName.setTypeface(typeface);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) convertView.getTag();
        }
        //TODO: Algarthm to decide when to show the special card.
        if (position != 3 && position != 5) {
            viewHolder.aisleCard.setVisibility(View.VISIBLE);
            viewHolder.specialCard.setVisibility(View.GONE);

            viewHolder.productSuggesterPic.setImageBitmap(suggesterIcon);

            if (listListDataContainer.getWindowList().size() != 1) {
                viewHolder.aisleCardUserNameId.setText(listListDataContainer.getWindowList().get(position).getName().trim());
                viewHolder.cardUserHeadingId.setText(listListDataContainer.getWindowList().get(position).getLookingFor().trim());
                loadBitMap(viewHolder.productImage, listListDataContainer.getWindowList().get(position).getProductList().get(0).getProductImages().get(0).getExternalURL(), viewHolder.aisleContentBrowser, listListDataContainer.getWindowList().get(position));
            }

            viewHolder.commentsShowId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(mContext, "click received", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.productCreateAisle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(mContext, "click received", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.productMenuId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopup(view);
                }
            });
            viewHolder.aisleSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            viewHolder.aisleCard.setVisibility(View.GONE);
            viewHolder.specialCard.setVisibility(View.VISIBLE);
            if (position == 3) {
                viewHolder.specialCard.removeAllViews();
                ViewGroup parentView = (ViewGroup) weekSpecialCards.get(FRIENDS_CARD).getParent();
                if (parentView != null)
                    parentView.removeAllViews();

                viewHolder.specialCard.addView(weekSpecialCards.get(FRIENDS_CARD));
            } else {
                viewHolder.specialCard.removeAllViews();
                ViewGroup parentView = (ViewGroup) weekSpecialCards.get(BIRTH_DAY_CARD).getParent();
                if (parentView != null)
                    parentView.removeAllViews();
                viewHolder.specialCard.addView(weekSpecialCards.get(BIRTH_DAY_CARD));
            }
        }

        return convertView;
    }

    /**
     * Loads the current bitmap if it is the new window, and sets the browser if the aisle has more images
     * to allow horizontal swipe
     */
    private void loadBitMap(ImageView imageView, String url, AisleContentBrowser browser, ClientAisle aisleWindow) {
        if (browser.getAisleUniqueId() == aisleWindow.getId()) {
            return;
        }
        bitmapLruCache = BitmapLruCache.getInstance(AnchoredContext
                .getInstance());
        if (browser.getChildCount() > 1) {
            //clear the aisle browser window before using for another window.
            View initialView = browser.getChildAt(0);
            int count = browser.getChildCount();
            for (int i = 0; i < count; i++) {
                View removedView = browser.getChildAt(0);
                ((ImageView) removedView.findViewById(R.id.product_image)).setImageBitmap(null);
                ((ProductCustomImageVeiw) removedView.findViewById(R.id.product_image)).setWorkerTaskObject(null);
                browser.removeViewAt(0);
                //don't add initial view to pool, it is a static view.
                if (i == 0) continue;
                ProductAdapterPool.getInstance(mContext).returnUsedViewToPool(removedView);
            }
            browser.addView(initialView);
        }

        browser.setAisleUniqueId(aisleWindow.getId());
        browser.setClientAisle(aisleWindow);
        Bitmap bitmap = bitmapLruCache.getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else if (ImageLoaderTask.cancelPotentialWork(url, imageView)) {
            imageView.setImageBitmap(null);
            ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageView, url);
            ((ProductCustomImageVeiw) imageView).setWorkerTaskObject(imageLoaderTask);
            imageLoaderTask.execute();
        }
    }
    public void showPopup(View anchorView) {

        View popupView = mInflater.inflate(R.layout.popup_layout, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                Utils.getPixel(mContext,200), AbsListView.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        ListView list = (ListView) popupView.findViewById(R.id.popup_list);
        list.setAdapter(new PopupListAdapter());
        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

    }
    private class PopupListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 8;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = mInflater.inflate(R.layout.popup_item, null);
            TextView item = (TextView)view.findViewById(R.id.popup_item);
            item.setText("Product");
            return view;
        }
    }
}
