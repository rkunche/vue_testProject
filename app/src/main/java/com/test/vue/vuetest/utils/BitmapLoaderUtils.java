package com.test.vue.vuetest.utils;

//android imports
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView.HitTestResult;

import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.utils.logs.Logging;

public class BitmapLoaderUtils {

	// private Context mContext;
	private static BitmapLoaderUtils sBitmapLoaderUtils;
	private FileCache mFileCache;
	// private VueMemoryCache<Bitmap> mAisleImagesCache;
	 private BitmapLruCache mAisleImagesCache;
    private final String TAG = "BitmapLoaderUtils";
    private boolean classLevelLogEnabled = true;

	// private int mScreenWidth;

	// private final boolean DEBUG = false;

	private BitmapLoaderUtils() {
		// mContext = context;
		mFileCache = AnchoredContext.getInstance().getFileCache();
	 
	 	mAisleImagesCache = BitmapLruCache.getInstance(AnchoredContext
				.getInstance());

		// DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		// mScreenWidth = metrics.widthPixels;

		// mExecutorService = Executors.newFixedThreadPool(5);
	}

	public static BitmapLoaderUtils getInstance() {
		if (null == sBitmapLoaderUtils) {
			sBitmapLoaderUtils = new BitmapLoaderUtils();
		}
		return sBitmapLoaderUtils;
	}

	/*
	 * This function is strictly for use by internal APIs. Not that we have
	 * anything external but there is some trickery here! The getBitmap function
	 * cannot be invoked from the UI thread. Having to deal with complexity of
	 * when & how to call this API is too much for those who just want to have
	 * the bitmap. This is a utility function and is public because it is to be
	 * shared by other components in the internal implementation.
	 */
	public Bitmap getBitmap(String serverUrl, boolean cacheBitmap,
			int bestWidth, int bestHeight, String source) {
		File f = mFileCache.getFile(serverUrl);
		// from SD cache
		Bitmap b = decodeFile(f, bestHeight, bestWidth, source);
		if (b != null) {
            Logging.i(TAG,"Image Available in SD card: ",false,classLevelLogEnabled);
			if (cacheBitmap)
				mAisleImagesCache.putBitmap(serverUrl, b);
			return b;
		}
		 
		// from web
		try {
			if (serverUrl == null || serverUrl.length() < 1) {

				return null;
			}
            Logging.i(TAG,"Image DownLoad Time starts At: "+System.currentTimeMillis(),false,classLevelLogEnabled);
			Bitmap bitmap = null;
			URL imageUrl = new URL(serverUrl);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			int hashCode = serverUrl.hashCode();
			String filename = String.valueOf(hashCode);
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
            Logging.i(TAG,"Image DownLoad Time Ends At: "+System.currentTimeMillis(),false,classLevelLogEnabled);
            Logging.i(TAG,"Bitmap Decode Time Starts At: "+System.currentTimeMillis(),false,classLevelLogEnabled);
			bitmap = decodeFile(f, bestHeight, bestWidth, source);
            Logging.i(TAG,"Bitmap Decode Time Ends At: "+System.currentTimeMillis(),false,classLevelLogEnabled);
			 if (cacheBitmap)
				mAisleImagesCache.putBitmap(serverUrl, bitmap);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError) {
				// mAisleImagesCache.evictAll();
			}
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	public Bitmap decodeFile(File f, int bestHeight, int bestWidth,
			String source) {


		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;


			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();
			// Find the correct scale value. It should be the power of 2.
			// final int REQUIRED_SIZE = mScreenWidth/2;
			int height = o.outHeight;
			int width = o.outWidth;
			int reqWidth = bestWidth;
			int scale = 1;

			if (height > bestHeight) {

				// Calculate ratios of height and width to requested height and
				// width
				final int heightRatio =  (int) ((float) height
						/ (float) bestHeight);
				final int widthRatio =(int)((float) width
						/ (float) reqWidth);
 
				// Choose the smallest ratio as inSampleSize value, this will
				// guarantee
				// a final image with both dimensions larger than or equal to
				// the
				// requested height and width.
				scale = heightRatio; // < widthRatio ? heightRatio : widthRatio;
				 
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);

			stream2.close();

		/*	if (source.equalsIgnoreCase(Utils.DETAILS_SCREEN)) {
				// scaling factor considers only integers may be some times
				// scaling factor becomes 1 even there is slight difference in
				// sizes
				// so to avoid croping in that cases in Detailsview screen
				// compare sizes after scaling.
				if (bitmap != null) {
					width = bitmap.getWidth();
					height = bitmap.getHeight();
					if (height > bestHeight) {
						float tempWidth = (width * bestHeight) / height;
						width = (int) tempWidth;
						bitmap = getModifiedBitmap(bitmap, width, bestHeight);
					}
				}
			}*/

			if (bitmap != null) {
				width = bitmap.getWidth();
				height = bitmap.getHeight();

				if (width > reqWidth) {
					float tempHeight = (height * reqWidth) / width;
					height = (int) tempHeight;
					bitmap = getModifiedBitmap(bitmap, reqWidth, height);
				}
			}
			return bitmap;
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			 
			e.printStackTrace();
		} catch (Throwable ex) {
			 
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError) {
				//mAisleImagesCache.evictAll();
				 
			}
			return null;
		}
		return null;
	}

	public Bitmap getBitmap(Bitmap bitmap, int bestWidth, int bestHeight) {
/*		int width, height;
		if (bitmap != null) {
			width = bitmap.getWidth();
			height = bitmap.getHeight();
			if (height >= bestHeight) {
				float tempWidth = (width * bestHeight) / height;
				width = (int) tempWidth;
				bitmap = getModifiedBitmap(bitmap, width, bestHeight);
			}

			if (bitmap != null) {
				width = bitmap.getWidth();
				height = bitmap.getHeight();

				if (width > bestWidth) {
					float tempHeight = (height * bestWidth) / width;
					height = (int) tempHeight;
					bitmap = getModifiedBitmap(bitmap, bestWidth, height);
				}
			}
		}*/
		return getModifiedBitmap(bitmap,bestWidth,bestHeight);
	}

	private Bitmap getModifiedBitmap(Bitmap originalImage, int width, int height) {
		// here width & height are the desired width & height values)
        Log.i("BitmapLoading", "BitmapLoading... getModifiedBitmap");
		// first lets create a new bitmap and a canvas to draw into it.
		Bitmap newBitmap = Bitmap.createBitmap((int) width, (int) height,
				Config.ARGB_8888);
		float originalWidth = originalImage.getWidth(), originalHeight = originalImage
				.getHeight();
		Canvas canvas = new Canvas(newBitmap);
		float scale = width / originalWidth;
		float xTranslation = 0.0f, yTranslation = (height - originalHeight
				* scale) / 2.0f;
		Matrix transformation = new Matrix();
		// now that we have the transformations, set that for our drawing ops
		transformation.postTranslate(xTranslation, yTranslation);
		transformation.preScale(scale, scale);
		// create a paint and draw into new canvas
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		canvas.drawBitmap(originalImage, transformation, paint);
		originalImage.recycle();
		return newBitmap;
	}

}
