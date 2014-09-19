package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.models.ImageLoaderTask;
import com.test.vue.vuetest.utils.BitmapLruCache;
import com.test.vue.vuetest.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public class CardWithFlipper extends DataAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private int mPagerCardBottomMargin = /*22 +*/ 48;
    private int mCardHeight = 1000;
    SpecialCardCreator creator;
    private static final String BIRTH_DAY_CARD = "birth_day_card";
    private static final String FRIENDS_CARD = "friends_card";

    View birthDaySpecial;
    WeakHashMap<String, View> weekSpecialCards;
    Bitmap suggesterIcon;
    BitmapLruCache bitmapLruCache;

    CardWithFlipper(Context context) {
        super(context);
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
        View friendsList = creator.createFriendsListCard();
        WeakReference<View> friendsListRef = new WeakReference<View>(friendsList);
        weekSpecialCards.put(FRIENDS_CARD, friendsList);
          suggesterIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.vuetest);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CardViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new CardViewHolder();
            convertView = mInflater.inflate(R.layout.card_flipper, null);
            viewHolder.aisleContentBrowser = (AisleContentBrowser) convertView
                    .findViewById(R.id.pager);
            viewHolder.productSuggesterPic = (ImageView) convertView.findViewById(R.id.product_suggester_image_id);
            viewHolder.suggesterLayout = (RelativeLayout) convertView.findViewById(R.id.product_suggester_lay_id);
            viewHolder.commentsShowId = (RelativeLayout) convertView.findViewById(R.id.product_comments_show_id);
            viewHolder.productAddPhotoLayId = (RelativeLayout) convertView.findViewById(R.id.product_add_photo_lay_id);
            viewHolder.aisleCard = (RelativeLayout) convertView.findViewById(R.id.aisle_card);
            viewHolder.specialCard = (RelativeLayout) convertView.findViewById(R.id.special_card);
            viewHolder.productImage = (ImageView) convertView.findViewById(R.id.product_image);
            viewHolder.productCreateAisle = (RelativeLayout) convertView.findViewById(R.id.card_create_aisle_id);
            viewHolder.productMenuId = (ImageView) convertView.findViewById(R.id.product_menu_id);
            viewHolder.aisleSettings = (RelativeLayout) convertView.findViewById(R.id.aisle_card_user_window_id);
            viewHolder.aisleCardUserNameId = (TextView) convertView.findViewById(R.id.aisle_card_user_name_id);
            viewHolder.cardUserHeadingId = (TextView) convertView.findViewById(R.id.card_user_heading_id);

            // set the params based on the best image height in the aisle.
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,mCardHeight);
            params.setMargins(Utils.getPixel(mContext,16),0,Utils.getPixel(mContext,16),0);
            viewHolder.aisleContentBrowser.setLayoutParams(params);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) convertView.getTag();
        }
        //TODO: Algarthm to decide when to show the special card.
        if (position % 2 == 0) {
            viewHolder.aisleCard.setVisibility(View.VISIBLE);
            viewHolder.specialCard.setVisibility(View.GONE);


            viewHolder.productSuggesterPic.setImageBitmap(suggesterIcon);



        viewHolder.aisleCardUserNameId.setText(windowList.get(position).getName());
        viewHolder.cardUserHeadingId.setText(windowList.get(position).getLookingFor());


          loadBitMap(viewHolder.productImage, windowList.get(position).getProductList().get(0).getProductImages().get(0).getExternalURL(),viewHolder.aisleContentBrowser, windowList.get(position));


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

                }
            });
            viewHolder.aisleSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else {
            Log.i("profile","profile start *************************************");
            viewHolder.aisleCard.setVisibility(View.GONE);
            viewHolder.specialCard.setVisibility(View.VISIBLE);
            if (position == 3 || position == 5) {
                viewHolder.specialCard.removeAllViews();
                viewHolder.specialCard.addView(weekSpecialCards.get(FRIENDS_CARD));
            } else {
                viewHolder.specialCard.removeAllViews();
                viewHolder.specialCard.addView(weekSpecialCards.get(BIRTH_DAY_CARD));
            }
            Log.i("profile","profile end ######################################## ");

        }

        return convertView;
    }
  private void loadBitMap(ImageView imageView,String url,AisleContentBrowser browser,ClientAisle aisleWindow) {
      Log.i("browsercall","browsercall same call 1 "+browser.getAisleUniqueId()+" aisleId: "+aisleWindow.getId().toString());
        if(browser.getAisleUniqueId().equals(aisleWindow.getId().toString())){
            Log.i("browsercall","browsercall same call returns");
            return;
        }
      browser.setAisleUniqueId(aisleWindow.getId().toString());
      browser.setClientAisle(aisleWindow);
      //TODO: CLEAR ALL THE CONTENT FROM THE BROWSER.
      bitmapLruCache = BitmapLruCache.getInstance(AnchoredContext
              .getInstance());
     if( browser.getChildCount() > 1){
         for(int i=1;i<browser.getChildCount();i++){
             View removedView =  browser. getChildAt(i);
             ((ImageView) removedView.findViewById(R.id.product_image)).setImageBitmap(null);
               browser.removeViewAt(i);
             ProductAdapterPool.getInstance(mContext).returnUsedViewToPool(removedView);
         }
     }

      Bitmap bitmap = bitmapLruCache.getBitmap(url);
      if (bitmap != null) {
          imageView.setImageBitmap(bitmap);
          Log.i("browsercall","browsercall cached");
      } else if (ImageLoaderTask.cancelPotentialWork(url, imageView)) {
          Log.i("BitmapLoading", "BitmapLoading... 2");
          imageView.setImageBitmap(null);
          ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageView, url);
          ((ProductCustomImageVeiw) imageView).setWorkerTaskObject(imageLoaderTask);
          imageLoaderTask.execute();
      }
  }
}
