package com.test.vue.vuetest.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.facebook.android.Util;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.models.ScreenDimensions;
import com.test.vue.vuetest.presenters.LandingPageActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class Utils {
	
   public static int sCardHeight = 0;
   public static int sCardWidth = 0;

    /**
     * returns the random number in the given range.
     */
    public static int randInt(int min, int max) {
        
        // Usually this should be a field rather than a method variable so
        // that it is not re-seeded every call.
        Random rand = new Random();
        
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    /**
     * returns the value pixel value equalent to given db.
     */
    public static int getPixel(Context context, int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, r.getDisplayMetrics());
        return px;
    }

    /**
     * gets the dimensions of the screen.
     */
    public static ScreenDimensions getScreenDimensions(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((LandingPageActivity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        ScreenDimensions dimensions = new ScreenDimensions();
        dimensions.mScreenWidth = metrics.widthPixels;
        dimensions.mScreenHeight = metrics.heightPixels;
        
        return dimensions;
        
    }

    /**
     * to make sure that the card height should not exceed the device height.
     */
	public static int getMaxCardHeight(Context context) {
		// 24 notification bar
		// 48 action bar
		// 64 card heading info
		// 48 card bottom text card_bottom_text_height

		if (sCardHeight == 0) {
			int cardHeadingInfo =   context.getResources().getInteger(
                    R.integer.top_card_height);
			int cardBottomInfo =   context.getResources().getInteger(
				R.integer.image_card_bottom_layout_height);
			int cardBottomText =   context.getResources().getInteger(
					 R.integer.card_bottom_text_height);
            int notificationBarHeight = 24;
            int actionBarHeight = 48;

			ScreenDimensions dimensions = getScreenDimensions(context);
            //top shadow height = 10
            //show divider = 10
			int deductValue = Utils.getPixel(context, (notificationBarHeight+actionBarHeight+10+cardHeadingInfo+cardBottomText+10));
			sCardHeight = dimensions.mScreenHeight - deductValue;
            int deductMarginValue = 16 + 16;
            sCardWidth =  dimensions.mScreenWidth - Utils.getPixel(context,deductMarginValue);
		}
		return sCardHeight;

	}

    /**
     *returns the current card height, if the current card height is more than the
     * device screen height then it returns the device height as the max card height.
     */
    public static int getCurrentCardHeight(int currentCardHeight,
            Context context) {
        int currentCardFinalHeight;
        int maxCardHeight = getMaxCardHeight(context);
        if (currentCardHeight > maxCardHeight) {
            currentCardFinalHeight = maxCardHeight;
        } else {
            currentCardFinalHeight = currentCardHeight;
        }
        return currentCardFinalHeight;
    }
    
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,
            final float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        
        return output;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }




}
