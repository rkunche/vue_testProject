package com.dboperations;

//android and java imports

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistentDatabase extends SQLiteOpenHelper {

    //db constants
    private static final String DATABASE_NAME = "vue_database";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase mDatabase;
    public static final String TABLE_AISLE_MODEL = "Aisle";
    public static final String TABLE_PRODUCT_MODEL = "Product";


    // Database aisle creation sql statement
    private final String DATABASE_CREATE_AISLE_TABLE = "create table "
            + TABLE_AISLE_MODEL + "(" + DbConstants.AISLE_ID
            + " long primary key, "+DbConstants.AISLE_OWNER_USER_ID+" text, " + DbConstants.AISLE_NAME
            + " text, " + DbConstants.AISLE_DESCRIPTION + " text, " + DbConstants.AISLE_CATEGORY
            + "text, " + DbConstants.AISLE_STATE + " text, " + DbConstants.AISLE_LOOKING_FOR + "text, " + DbConstants.AISLE_OCCASION + "text, " + DbConstants.AISLE_BOOKMARK_COUNT + " text);";

    // Database product creation sql statement
    private final String DATABASE_CREATE_PRODUCT_TABLE = "create table "
            + TABLE_PRODUCT_MODEL + "(" + DbConstants.PRODUCT_ID
            + " long primary key, " + DbConstants.PRODUCT_OWNER_AISLE_ID
            + " text, " + DbConstants.PRODUCT_OWNER_PRODUCT_LIST_ID + " text, " + DbConstants.PRODUCT_CREATOR_ID
            + "text, " + DbConstants.PRODUCT_CURATOR_ID + " text, " + DbConstants.PRODUCT_TITLE + "text, "
            + DbConstants.PRODUCT_DESCRIPTION + "text, " + DbConstants.PRODUCT_STATE + " text, "+DbConstants.PRODUCT_RELATED_PRODUCT_IDS+" text,"+DbConstants.PRODUCT_IMAGE_ID+" text,"+DbConstants.PRODUCT_OWNER_PRODUCT_ID
            +" text, "+DbConstants.PRODUCT_EXTERNAL_URL+ " text, "+DbConstants.PRODUCT_INTERNAL_URL+ " text, "
            +DbConstants.PRODUCT_ORIGINAL_HEIGHT+ " text, "+DbConstants.PRODUCT_ORIGINAL_WIDTH+ " text, "+DbConstants.PRODUCT_MODIFIED_HEIGHT+" text, "
            +DbConstants.PRODUCT_MODIFIED_WIDTH+ " text, "+DbConstants.PRODUCT_IMAGE_DESCRIPTION+ " text, "+DbConstants.PRODUCT_PROVIDER_ID+ " text, "
            +DbConstants.PRODUCT_PROVIDER_EXTERNAL_URL+ " text, "+DbConstants.PRODUCT_QUANTITY+ " text, "+DbConstants.PRODUCT_CURRENCY_CODE+" text, "+ DbConstants.PRODUCT_AVAILABILITY+" text, "
            +DbConstants.PRODUCT_PRICE+" text, "+DbConstants.PRODUCT_STORE+ " text, "+DbConstants.PRODUCT_ON_SALE+ " text, "+DbConstants.PRODUCT_SALE_PRICE+" text, "
            +DbConstants.PRODUCT_SALE_EXPIRY_DATE+" text, "+ DbConstants.PRODUCT_AVAILABLE_COLORS+ " text, "+ DbConstants.PRODUCT_AVAILABLE_SIZES+ " text, "+ DbConstants.PRODUCT_AVAILABLE_ZIP_CODES+ " text)";


    public PersistentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_AISLE_TABLE);
        database.execSQL(DATABASE_CREATE_PRODUCT_TABLE);
        mDatabase = database;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_AISLE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_PRODUCT_TABLE);
        onCreate(db);
    }

}