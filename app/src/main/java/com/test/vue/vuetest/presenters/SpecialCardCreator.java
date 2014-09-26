package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.test.vue.vuetest.R;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.test.vue.vuetest.models.ScreenDimensions;
import com.test.vue.vuetest.uihelper.CircularImageView;
import com.test.vue.vuetest.utils.Utils;


public class SpecialCardCreator {
    Context context;
    int imageSize = 72;

    SpecialCardCreator(Context context) {
        this.context = context;
    }

    public LinearLayout createBirthDayCard() {
        LinearLayout cardHolder = new LinearLayout(context);
        cardHolder.setOrientation(LinearLayout.VERTICAL);
        //cardHolder.setBackgroundResource((R.drawable.search_bg_shadow));
        cardHolder.setBackgroundColor(context.getResources().getColor(R.color.birthday_card_color));
        RelativeLayout.LayoutParams LLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParams.setMargins(0, 0, 0, Utils.getPixel(context, 8));
        cardHolder.setLayoutParams(LLParams);

        LinearLayout.LayoutParams textOneParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textOneParams.setMargins(0, Utils.getPixel(context, 8), 0, Utils.getPixel(context, 8));
        textOneParams.gravity = Gravity.CENTER_HORIZONTAL;

        TextView birth_text_one = new TextView(context);
        birth_text_one.setText("Like Surprises?");
        birth_text_one.setTextSize(context.getResources().getInteger(R.integer.large_text_size));
        birth_text_one.setTextColor(context.getResources().getColor(R.color.black));
        birth_text_one.setLayoutParams(textOneParams);
        cardHolder.addView(birth_text_one);

        TextView birth_text_two = new TextView(context);
        birth_text_two.setText("We send you only the good ones!");
        birth_text_one.setTextSize(context.getResources().getInteger(R.integer.medium_text_size));
        birth_text_two.setTextColor(context.getResources().getColor(R.color.black));
        birth_text_two.setLayoutParams(textOneParams);
        cardHolder.addView(birth_text_two);

        LinearLayout.LayoutParams buttonOneParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonOneParams.setMargins(0, Utils.getPixel(context, 8), 0, Utils.getPixel(context, 8));
        buttonOneParams.gravity = Gravity.CENTER_HORIZONTAL;




        Button birth_button = new Button(context);
        birth_button.setText("Add your birthday and let us surprise you");
        birth_button.setBackgroundColor(context.getResources().getColor(R.color.light_blue_button));
        birth_button.setBackgroundResource((R.drawable.round_corner_product));
        birth_button.setLayoutParams(buttonOneParams);
        cardHolder.addView(birth_button);

        TextView birth_text_dismiss = new TextView(context);
        textOneParams.setMargins(0, Utils.getPixel(context, 8), 0, Utils.getPixel(context, 16));
        birth_text_dismiss.setLayoutParams(textOneParams);
        birth_text_dismiss.setText("Dismiss");
        cardHolder.addView(birth_text_dismiss);


        return cardHolder;


    }

    public LinearLayout createFriendsListCard() {
        LinearLayout cardHolder = new LinearLayout(context);
        cardHolder.setOrientation(LinearLayout.VERTICAL);
        cardHolder.setBackgroundColor(context.getResources().getColor(R.color.birthday_card_color));
        RelativeLayout.LayoutParams LLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LLParams.setMargins(0, 0, 0, Utils.getPixel(context, 8));
        cardHolder.setLayoutParams(LLParams);

        LinearLayout.LayoutParams textOneParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textOneParams.setMargins(0, Utils.getPixel(context, 8), 0, Utils.getPixel(context, 8));
        textOneParams.gravity = Gravity.CENTER_HORIZONTAL;

        TextView add_friend_text_one = new TextView(context);
        add_friend_text_one.setText("Invite friends to shop with you!");
        add_friend_text_one.setTextSize(context.getResources().getInteger(R.integer.large_text_size));
        add_friend_text_one.setTextColor(context.getResources().getColor(R.color.text_color_black));
        add_friend_text_one.setLayoutParams(textOneParams);
        cardHolder.addView(add_friend_text_one);

        ScreenDimensions dimension = Utils.getScreenDimensions(context);
       // int itemWidth = Utils.getPixel(context, imageSize);
        int leftRightMargings = 16;
        int totalMaringGap = 5 * 6;
        int totalWidht = dimension.mScreenWidth - Utils.getPixel(context, leftRightMargings);
        int itemWidth = (totalWidht - totalMaringGap)/5;
       // int no = (dimension.mScreenWidth - Utils.getPixel(context, leftRightMargings)) / itemWidth;
       // int marginSpace = no * Utils.getPixel(context, 4);
       // no = (dimension.mScreenWidth - (Utils.getPixel(context, imageSize) - marginSpace)) / itemWidth;
        LinearLayout friendHolder = new LinearLayout(context);
        friendHolder.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams FLLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        FLLParams.setMargins(0, Utils.getPixel(context, 4), 0, Utils.getPixel(context, 4));
        FLLParams.gravity = Gravity.CENTER_HORIZONTAL;
        String tempNames[] = {"Taylor Swift","Jennifer Lopes","Katy Perry","Christina Hendricks"};
        for (int i = 0; i < 5; i++) {
            LinearLayout personHolder = new LinearLayout(context);
            personHolder.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams PLLParams = new LinearLayout.LayoutParams(itemWidth, itemWidth + itemWidth);
            PLLParams.setMargins(Utils.getPixel(context, 3), 0, Utils.getPixel(context, 3), 0);
            personHolder.setLayoutParams(PLLParams);

            if (i < 5-1 ) {
                CircularImageView profilePic = new CircularImageView(context);
                profilePic.setImageResource(R.drawable.vuetest);
                LinearLayout.LayoutParams profileParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
                profilePic.setLayoutParams(profileParams);

                TextView profileName = new TextView(context);
                profileName.setText(tempNames[i]);
                profileName.setTextColor(context.getResources().getColor(R.color.text_color_black));
                profileName.setLines(2);
                profileName.setEllipsize(TextUtils.TruncateAt.END);
                profileName.setTextSize(context.getResources().getInteger(R.integer.friend_text_size));
                profileName.setGravity(Gravity.CENTER_HORIZONTAL);
                LinearLayout.LayoutParams profileNameParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
                profileNameParams.setMargins(Utils.getPixel(context, 4), 0, Utils.getPixel(context, 3), 0);
                profileName.setLayoutParams(profileNameParams);

                personHolder.addView(profilePic);
                personHolder.addView(profileName);
                friendHolder.addView(personHolder);
            } else {
                Log.i("image","image else");
                TextView profileName = new TextView(context);

                LinearLayout.LayoutParams moreParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
                moreParams.gravity = Gravity.CENTER;
                profileName.setLayoutParams(moreParams);
                profileName.setText("+");
                profileName.setGravity(Gravity.CENTER);
                profileName.setTextSize(context.getResources().getInteger(R.integer.large_text_size));
                profileName.setTextColor(context.getResources().getColor(R.color.white));
                profileName.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.round_corners));

                TextView profileMore = new TextView(context);
                profileMore.setText("More friends");
                profileMore.setTextColor(context.getResources().getColor(R.color.text_color_black));
                profileMore.setLines(2);
                profileMore.setEllipsize(TextUtils.TruncateAt.END);
                profileMore.setTextSize(context.getResources().getInteger(R.integer.friend_text_size));
                LinearLayout.LayoutParams profileMoreParams = new LinearLayout.LayoutParams(itemWidth, itemWidth);
                profileMore.setLayoutParams(profileMoreParams);

                personHolder.addView(profileName);
                personHolder.addView(profileMore);
                friendHolder.addView(personHolder);
            }
        }
        cardHolder.addView(friendHolder);

        TextView birth_text_dismiss = new TextView(context);
        textOneParams.setMargins(0, Utils.getPixel(context, 8), 0, Utils.getPixel(context, 16));
        birth_text_dismiss.setLayoutParams(textOneParams);
        birth_text_dismiss.setText("Dismiss");
        birth_text_dismiss.setTextColor(context.getResources().getColor(R.color.dismiss_text_color));
        cardHolder.addView(birth_text_dismiss);
        return cardHolder;
    }
}
