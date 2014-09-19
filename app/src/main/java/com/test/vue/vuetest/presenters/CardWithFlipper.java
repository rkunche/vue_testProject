package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.test.vue.vuetest.R;
import com.test.vue.vuetest.models.ImageLoaderTask;
import com.test.vue.vuetest.personal.product.ImageManager;
import com.test.vue.vuetest.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;


public class CardWithFlipper extends DataAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private int mPagerCardBottomMargin = /*22 +*/ 48;
    private int mTempHeight = 1000;
    SpecialCardCreator creator;
    private static final String BIRTH_DAY_CARD = "birth_day_card";
    private static final String FRIENDS_CARD = "friends_card";

    View birthDaySpecial;
    WeakHashMap<String, View> weekSpecialCards;


    CardWithFlipper(Context context) {
        super(context);
        mContext = context;
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) convertView.getTag();
        }
        //TODO: Algarthm to decide when to show the special card.
        if (position % 2 == 0) {
            viewHolder.aisleCard.setVisibility(View.VISIBLE);
            viewHolder.specialCard.setVisibility(View.GONE);
            // set the params based on the best image height in the aisle.
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
                    Utils.getCurrentCardHeight(mTempHeight, mContext)
                            + Utils.getPixel(mContext, mPagerCardBottomMargin)
            );

            Bitmap suggesterIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.vuetest);
            viewHolder.productSuggesterPic.setImageBitmap(suggesterIcon);
            viewHolder.aisleContentBrowser.setLayoutParams(params);
            viewHolder.productImage.setImageResource(R.drawable.common_signin_btn_text_focus_dark);
            String url = null;
          //loadBitMap(viewHolder.productImage,windowList.get(position).imageList.get(0).getExternalURL());

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
  private void loadBitMap(ImageView imageView,String url){
      if(ImageLoaderTask.cancelPotentialWork(url,imageView)){
       ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageView, url);
          ((ProductCustomImageVeiw)  imageView).setWorkerTaskObject(imageLoaderTask);
          imageLoaderTask.execute();
      }
  }
}
