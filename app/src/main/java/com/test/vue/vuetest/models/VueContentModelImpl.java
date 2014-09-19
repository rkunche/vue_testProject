package com.test.vue.vuetest.models;


//vue internal imports

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.vue.vuetest.AnchoredContext;
import com.test.vue.vuetest.domain.AisleBase;
import com.test.vue.vuetest.domain.client.ClientAisle;
import com.test.vue.vuetest.presenters.DataContainer;
import com.test.vue.vuetest.utils.Logger;


import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

//android and java imports

public class VueContentModelImpl implements VueContentModel {

    //messages that we will handle from VueContentModelCore
    private static final int INITIALIZE = 0;
    private static final int REDUCE_PRIORITY = 1;
    private static final int RESUME_PRIORITY = 2;
    public static final int AISLE_DATA_GET = 3;
    public static final int AISLE_DATA_NOTIFY = 4;

    private static VueContentModelImpl sVueContentModelImpl;
    private static final String CONTENT_MODEL_STATE_PREFS = "VueContentModelPrefs";

    private static final String HANDLER_THREAD_NAME = "ContentModelCoreThread";

    private static WeakReference<NetworkContentAccessor> fetcher;
    private static Handler sContentModelHandler;

    private PersistentDatabase mStoredContent;
    private int mFetcherOffset = 0;

    private RequestQueue mRequestQueue;

    private long mTimeWhenGetAislesWasStarted;
    private long mTimeTakenToFetch100Aisles;
    private long mTimeTakenToFetch500Aisles;
    private long mTimeTakenToFetch1000Aisles;
    private long mTimeTakenToFetch10Aisles;

    private int mOffset;
    private int mLimit = 10;


    //All these need to move elsewhere
    public static final String AISLE_ID = "id";
    static final String AISLE_CATEGORY = "category";
    public static final String AISLE_LOOKINGFOR = "lookingFor";
    public static final String AISLE_NAME = "name";
    public static final String AISLE_OCCASSION = "occassion";
    public static final String AISLE_OWNER_USER_ID = "ownerUserId";
    public static final String AISLE_DESCRIPTION = "description";
    public static final String AISLE_OWNER_FIRSTNAME = "aisleOwnerFirstName";
    public static final String AISLE_OWNER_LASTNAME = "aisleOwnerLastName";
    public static final String AISLE_OWNER_IMAGE_URL = "aisleOwnerImageURL";
    public static final String AISLE_BOOKMARK_COUNT = "bookmarkCount";

    private DataContainer mDataContainerListener;

    private VueContentModelImpl() {
        synchronized (VueContentModelImpl.class) {
            if (sContentModelHandler == null) {
                // Create a global thread and start it.
                Thread t = new Thread(new VueContentModelCoreThread());
                t.setName(HANDLER_THREAD_NAME);
                t.start();
                try {
                    VueContentModelImpl.class.wait();
                    sendMessage(Message.obtain(sContentModelHandler, INITIALIZE, this));
                    mRequestQueue = Volley.newRequestQueue(AnchoredContext.getInstance().getApplicationContext());
                    mTimeTakenToFetch1000Aisles = -1;
                    mTimeTakenToFetch500Aisles = -1;
                    mTimeTakenToFetch100Aisles = -1;
                    mTimeTakenToFetch10Aisles = -1;
                    mOffset = 0;
                } catch (InterruptedException e) {
                    //TODO: log this exception
                }
            }
        }
    }

    public static void sendMessage(Message msg) {
        if (sContentModelHandler == null) {
            getContentModel();
        } else {
            sContentModelHandler.sendMessage(msg);
        }
    }

    public static VueContentModel getContentModel() {
        if (null == sVueContentModelImpl) {
            sVueContentModelImpl = new VueContentModelImpl();
        }
        return sVueContentModelImpl;
    }

    @Override
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void getCardsMetaData(int offset, int limit) {
    }

    //set listener to notify the cards adapter when data is available
    public void setDataRegisterListener(DataContainer dataContainerListener) {
        mDataContainerListener = dataContainerListener;
    }

    public DataContainer getDataRegisterListener() {
        return mDataContainerListener;
    }

    @Override
    public void onPause() {
        //deinitContinuousFetcher

    }

    @Override
    public void onResume() {
        //Determine our status first. Is this a first time launch for the app on this device?
        SharedPreferences contentModelPrefs =
                AnchoredContext.getInstance().getApplicationContext().getSharedPreferences(CONTENT_MODEL_STATE_PREFS, 0);

        boolean newInstall = contentModelPrefs.getBoolean("newInstall", true);
        boolean updatedInstall = contentModelPrefs.getBoolean("updatedInstall", false);
        mFetcherOffset = contentModelPrefs.getInt("lastFetchedItemIndex", 0);

        mTimeWhenGetAislesWasStarted = System.currentTimeMillis();
        if (newInstall) {
            //Lets make sure we don't have bugs here
            Assert.assertFalse("Status indicates this is a new install but somehow " +
                    "updatedInstall is set to true as well!", updatedInstall == true);

            //TODO: lets log this so can look at what happened

            //TODO: we need to initialize the DB first
            mStoredContent = new PersistentDatabase(AnchoredContext.getInstance().getApplicationContext());
            mStoredContent.getReadableDatabase();

            initializeContinuousFetcher();
        } else if (updatedInstall) {

        } else {
            mStoredContent = new PersistentDatabase(AnchoredContext.getInstance().getApplicationContext());
            mStoredContent.getReadableDatabase();
            initializeContinuousFetcher();
        }
    }

    @Override
    public int getTotalNumberOfCards() {
        return mStoredContent.getNumItems();
    }

    private void initializeContinuousFetcher() {
        sendMessage(Message.obtain(sContentModelHandler, AISLE_DATA_GET));
    }

    //internal APIs
    private void notifyMoreAislesLoaded(JSONArray jsonArray, int offset, int limit) {
        //ArrayList<AisleWindowContent> aisleWindowContentList = new ArrayList<AisleWindowContent>();
        mOffset = offset + limit;
        if (mOffset >= 1000) {
            if (mTimeTakenToFetch1000Aisles == -1) {
                mTimeTakenToFetch1000Aisles = System.currentTimeMillis();
                Logger.analytics("AisleMetaDataLoadPerformance", "Time taken to fetch the first " +
                        "1000 aisles = " + String.valueOf(mTimeTakenToFetch1000Aisles - mTimeWhenGetAislesWasStarted) +
                        " current offset = " + String.valueOf(mOffset));
            }
        } else if (mOffset >= 500) {
            if (mTimeTakenToFetch500Aisles == -1) {
                mTimeTakenToFetch500Aisles = System.currentTimeMillis();
                Logger.analytics("AisleMetaDataLoadPerformance", "Time taken to fetch the first " +
                        "500 aisles = " + String.valueOf(mTimeTakenToFetch500Aisles - mTimeWhenGetAislesWasStarted) +
                        " current offset = " + String.valueOf(mOffset));
            }
        } else if (mOffset >= 100) {
            if (mTimeTakenToFetch100Aisles == -1) {
                mTimeTakenToFetch100Aisles = System.currentTimeMillis();
                Logger.analytics("AisleMetaDataLoadPerformance", "Time taken to fetch the first " +
                        "100 aisles = " + String.valueOf(mTimeTakenToFetch100Aisles - mTimeWhenGetAislesWasStarted) +
                        " current offset = " + String.valueOf(mOffset));
            }
        } else if (mOffset >= 10) {
            if (mTimeTakenToFetch10Aisles == -1) {
                mTimeTakenToFetch10Aisles = System.currentTimeMillis();
                Logger.analytics("AisleMetaDataLoadPerformance", "Time taken to fetch the first " +
                        "10 aisles = " + String.valueOf(mTimeTakenToFetch10Aisles - mTimeWhenGetAislesWasStarted) +
                        " current offset = " + String.valueOf(mOffset));
            }
        }
        Log.i("item", "item " + jsonArray.length());
        // for (int i = 0; i < jsonArray.length(); i++) {
        try {
            Log.i("item", "item ");

            ArrayList<ClientAisle> aisles = (new ObjectMapper()).readValue(jsonArray.toString(), new TypeReference<List<ClientAisle>>() {
            });
            WeakReference<ArrayList<ClientAisle>> weakReferenceClientAisleList = new WeakReference<ArrayList<ClientAisle>>(aisles);
            ArrayList<ClientAisle> CuratedAisles = new ArrayList<ClientAisle>();
            Log.i("item", "item aisle id: " + aisles.size());
            for (int i = 0; i < aisles.size(); i++) {
                if (aisles.get(i).getProductList() != null && aisles.get(i).getCurrentAisleState() != ClientAisle.AisleStateEnum.DELETED /*&& (aisles.get(i).getProductList() != null && aisles.get(i).getProductList().get(0).getProductImages() != null)*/) {
                   if(aisles.get(i).getProductList().size() != 0 && aisles.get(i).getProductList().get(0).getProductImages().size() != 0)
                    CuratedAisles.add(aisles.get(i));
                }
            }
            aisles.clear();
            aisles = null;
            mDataContainerListener.addMoreData(CuratedAisles);

        } catch (MalformedURLException e) {
            // handle invalid URL
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            // handle timeout
            e.printStackTrace();
        } catch (IOException e) {
            // handle I/0
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

            /*AisleContext aisleContext = parseAisleData(ailseItem);
            ArrayList<AisleImageDetails> aisleImageDetailsList = new ArrayList<AisleImageDetails>();
            try {
                JSONObject imageObject = ailseItem
                        .getJSONObject(VueConstants.AISLE_IMAGE_OBJECT);
                JSONArray ImageListJson = imageObject
                        .getJSONArray(VueConstants.AISLE_IMAGE_LIST);
                for (int index = 0; index < ImageListJson.length(); index++) {
                    AisleImageDetails aisleImageDetails = parseAisleImageData(ImageListJson
                            .getJSONObject(index));
                    if (aisleImageDetails.mImageUrl != null
                            && (!aisleImageDetails.mImageUrl
                            .contains("randomurl.com"))
                            && aisleImageDetails.mImageUrl.trim().length() > 0
                            && aisleImageDetails.mAvailableHeight != 0
                            && aisleImageDetails.mAvailableWidth != 0) {
                        aisleImageDetailsList.add(aisleImageDetails);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            /*if (aisleImageDetailsList != null
                    && aisleImageDetailsList.size() > 0) {
                AisleWindowContent aisleWindowContent = VueTrendingAislesDataModel
                        .getInstance(VueApplication.getInstance())
                        .getAisleItem(aisleContext.mAisleId);
                aisleWindowContent.addAisleContent(aisleContext,
                        aisleImageDetailsList);
                aisleWindowContentList.add(aisleWindowContent);
            } else if (isEmptyAilseCached) {
                // TODO: UNCOMMENT THIS CODE WHEN NO IMAGE AISLE FEATURE
                // ENABLED.
            }*/
        //  }
    }

    public void parseAisleData(JSONObject jsonObject) {
        try {
            String aisleId = jsonObject.getString(AISLE_ID);
            String aisleOwnerImageUrl = jsonObject
                    .optString(AISLE_OWNER_IMAGE_URL);
            if (aisleOwnerImageUrl == null || aisleOwnerImageUrl.equals("null")) {
                //aisleContext.mAisleOwnerImageURL = null;
            } else {
                //aisleContext.mAisleOwnerImageURL = aisleOwnerImageUrl;
            }
            String lastName = jsonObject
                    .getString(AISLE_OWNER_LASTNAME);
            /*aisleContext.mCategory = jsonObject
                    .getString(AISLE_CATEGORY);
            aisleContext.mBookmarkCount = jsonObject
                    .getInt(AISLE_BOOKMARK_COUNT);
            String description = jsonObject
                    .getString(AISLE_DESCRIPTION);
            if (description == null || description.equals("null")) {
                aisleContext.mDescription = "";
            } else {
                aisleContext.mDescription = description;
            }
            String occasion = jsonObject
                    .getString(AISLE_OCCASSION);
            if (occasion == null || occasion.equals("null")) {
                aisleContext.mOccasion = "";
            } else {
                aisleContext.mOccasion = occasion;
            }
            aisleContext.mName = jsonObject.getString(AISLE_NAME);
            String firstName = jsonObject
                    .getString(AISLE_OWNER_FIRSTNAME);
            aisleContext.mUserId = jsonObject
                    .getString(AISLE_OWNER_USER_ID);
            aisleContext.mLookingForItem = jsonObject
                    .getString(AISLE_LOOKINGFOR);

            if (firstName == null || firstName.equals("null")) {
                aisleContext.mFirstName = " ";
                firstName = null;
            } else {
                aisleContext.mFirstName = firstName;
            }
            if (lastName == null || lastName.equals("null")) {
                aisleContext.mLastName = " ";
                lastName = null;
            } else {
                aisleContext.mLastName = lastName;
            }
            if (firstName == null && lastName == null) {
                aisleContext.mFirstName = "Anonymous";
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class VueContentModelCoreThread implements Runnable {
        public void run() {
            Looper.prepare();
            Assert.assertNull(sContentModelHandler);
            synchronized (VueContentModelImpl.class) {
                sContentModelHandler = new Handler() {
                    private VueContentModelImpl core;

                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case INITIALIZE:
                                core = (VueContentModelImpl) msg.obj;
                                break;

                            case REDUCE_PRIORITY:
                                // 3 is an adjustable number.
                                Process.setThreadPriority(
                                        Process.THREAD_PRIORITY_DEFAULT + 3 *
                                                Process.THREAD_PRIORITY_LESS_FAVORABLE
                                );
                                break;

                            case RESUME_PRIORITY:
                                Process.setThreadPriority(
                                        Process.THREAD_PRIORITY_DEFAULT);
                                break;

                            case AISLE_DATA_NOTIFY:
                                //more aisle data has become available
                                core.notifyMoreAislesLoaded((JSONArray) msg.obj, msg.arg1, msg.arg2);

                                //based on some conditions we can go get more aisles. For now, just keep getting more
                                //  sendMessage(Message.obtain(sContentModelHandler, AISLE_DATA_GET));
                                break;

                            case AISLE_DATA_GET:
                                fetcher = new WeakReference<NetworkContentAccessor>(NetworkContentAccessor.getInstance());
                                if (null != fetcher.get()) {
                                    fetcher.get().getAislesInRange(core.mOffset, core.mLimit);
                                }
                                break;
                        }
                    }
                };
                VueContentModelImpl.class.notify();
            }
            Looper.loop();
        }
    }

}
