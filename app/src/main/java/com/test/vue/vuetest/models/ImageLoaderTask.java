package com.test.vue.vuetest.models;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.test.vue.vuetest.presenters.ProductCustomImageVeiw;
import com.test.vue.vuetest.utils.BitmapLoaderUtils;
import com.test.vue.vuetest.utils.Utils;

import java.lang.ref.WeakReference;


public class ImageLoaderTask extends AsyncTask<Void, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    public String url;

    public ImageLoaderTask(ImageView imageView, String url) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = BitmapLoaderUtils.getInstance().getBitmap(url, true, Utils.sCardWidth, Utils.sCardHeight, "Trending");
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        super.onPostExecute(bmp);
        if (imageViewReference != null && bmp != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bmp);

            }
        }

    }

    public static boolean cancelPotentialWork(String currentUrl, ImageView imageView) {
        final ImageLoaderTask imageLoaderTask = getBitmapWorkerTask(imageView);

        if (imageLoaderTask != null) {
            final String url = imageLoaderTask.url;
            // If bitmapData is not yet set or it differs from the new data
            if (url == null || (!currentUrl.equals(url))) {
                // Cancel previous task
                imageLoaderTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static ImageLoaderTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final ImageLoaderTask imageLoaderTask = ((ProductCustomImageVeiw) imageView).getTask();
            if (imageLoaderTask != null) {
                if (imageLoaderTask instanceof ImageLoaderTask) {

                    return imageLoaderTask;
                }
            }
        }
        return null;
    }
}
