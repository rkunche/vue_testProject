package com.test.vue.vuetest.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.R;
import com.test.vue.vuetest.domain.client.ClientProductImage;
import com.test.vue.vuetest.models.ImageLoaderTask;
import com.test.vue.vuetest.utils.BitmapLruCache;


public class AisleContentAdapter implements IAisleContentAdapter {
    static AisleContentAdapter sAisleContentAdapter;

    public AisleContentAdapter() {

    }

    public static AisleContentAdapter getInstance() {
        if (sAisleContentAdapter == null) {
            sAisleContentAdapter = new AisleContentAdapter();
        }
        return sAisleContentAdapter;
    }

    @Override
    public void setContentSource(String uniqueAisleId) {

    }

    @Override
    public void releaseContentSource() {

    }

    @Override
    public void setPivot(int index) {

    }

    @Override
    public int getAisleItemsCount() {

        return 0;
    }

    /**
     * Adds image to the horizontal flipper
     */
    @Override
    public boolean setAisleContent(AisleContentBrowser contentBrowser,
                                   int currentIndex, int wantedIndex, boolean shiftPivot,
                                   int imagesCount, Context context) {
        if (wantedIndex >= imagesCount)
            return false;
        if (0 >= currentIndex && wantedIndex < currentIndex)
            return false;

        ClientProductImage image = contentBrowser.getClientAisle().getProductList().get(wantedIndex).getProductImages().get(0);
        View view = ProductAdapterPool.getInstance(context).getProductLayout();
        contentBrowser.addView(view);
        loadBitmap(image.getExternalURL(), (ImageView) view.findViewById(R.id.product_image));
        return true;
    }


    @Override
    public String getAisleId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getImageId(int mCurrentIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    private void loadBitmap(String url, ImageView imageView) {
        imageView.setImageBitmap(null);
        Bitmap bitmap = BitmapLruCache.getInstance(AnchoredContext
                .getInstance()).getBitmap(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

        } else if (ImageLoaderTask.cancelPotentialWork(url, imageView)) {
            imageView.setImageBitmap(null);
            ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imageView, url);
            ((ProductCustomImageVeiw) imageView).setWorkerTaskObject(imageLoaderTask);
            imageLoaderTask.execute();
        }
    }
}
