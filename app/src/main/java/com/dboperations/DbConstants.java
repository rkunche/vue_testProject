package com.dboperations;

/**
 * Created by vazeer on 25/9/14.
 */
public class DbConstants {


    //Aisle columns
    public static final String AISLE_ID = "id";
    public static final String AISLE_OWNER_USER_ID = "ownerUserId";
    public static final String AISLE_DESCRIPTION = "description";
    public static final String AISLE_CATEGORY = "category";
    public static final String AISLE_STATE = "currentAisleState";
    public static final String AISLE_OCCASION = "occassion";
    public static final String AISLE_NAME = "name";
    public static final String AISLE_LOOKING_FOR = "lookingFor";
    public static final String AISLE_BOOKMARK_COUNT = "bookmarkCount";

    //product columns
    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_OWNER_AISLE_ID = "ownerAisleId";
    public static final String PRODUCT_OWNER_PRODUCT_LIST_ID = "ownerProductListId";
    public static final String PRODUCT_CREATOR_ID = "creatorId";
    public static final String PRODUCT_CURATOR_ID = "curatorId";
    public static final String PRODUCT_TITLE = "title";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_STATE = "currentProductState";
    public static final String PRODUCT_RELATED_PRODUCT_IDS = "relatedProductIds";
    //product column for image
    public static final String PRODUCT_IMAGE_ID = "image_id";
    public static final String PRODUCT_OWNER_PRODUCT_ID = "ownerProductId";
    public static final String PRODUCT_EXTERNAL_URL  = "externalURL";
    public static final String PRODUCT_INTERNAL_URL = "internalURL";
    public static final String PRODUCT_ORIGINAL_HEIGHT = "orignalHeight";
    public static final String PRODUCT_ORIGINAL_WIDTH = "orignalWidth";
    public static final String PRODUCT_MODIFIED_HEIGHT = "modifiedHeight";
    public static final String PRODUCT_MODIFIED_WIDTH = "modifiedWidth";
    public static final String PRODUCT_IMAGE_DESCRIPTION = "image_description";
    //product column for provider
    public static final String PRODUCT_PROVIDER_ID = "provider_id";
    public static final String PRODUCT_PROVIDER_EXTERNAL_URL = "provider_externalURL";
    public static final String PRODUCT_QUANTITY = "quantity";
    public static final String PRODUCT_CURRENCY_CODE = "currencyCode";
    public static final String PRODUCT_AVAILABILITY = "availability";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_STORE = "store";
    public static final String PRODUCT_ON_SALE = "onSale";
    public static final String PRODUCT_SALE_PRICE = "salePrice";
    public static final String PRODUCT_SALE_EXPIRY_DATE = "saleExpirtyDate";
    public static final String PRODUCT_AVAILABLE_COLORS = "availableColors";
    public static final String PRODUCT_AVAILABLE_SIZES = "availableSizes";
    public static final String PRODUCT_AVAILABLE_ZIP_CODES = "availableAtZipCodes";


}
