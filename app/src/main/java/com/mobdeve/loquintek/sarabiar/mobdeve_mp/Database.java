package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Database extends SQLiteOpenHelper{

    public static final String RECEIPTS_TABLE = "RECEIPTS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MERCHANT_NAME = "COLUMN_MERCHANT_NAME";
    public static final String COLUMN_MERCHANT_ADDRESS = "COLUMN_MERCHANT_ADDRESS";
    public static final String COLUMN_ITEMS = "COLUMN_ITEMS";
    public static final String COLUMN_UNIT_PRICES = "COLUMN_UNIT_PRICES";
    public static final String COLUMN_ITEM_QUANTITIES = "COLUMN_ITEM_QUANTITIES";
    public static final String COLUMN_VAT = "COLUMN_VAT";
    public static final String COLUMN_VATABLE = "COLUMN_VATABLE";
    public static final String COLUMN_DATE = "COLUMN_DATE";
    public static final String COLUMN_SERIAL_NUMBER = "COLUMN_SERIAL_NUMBER";

    public Database(@Nullable Context context) {
        super(context, "receipts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //creates new database

        String createTableStatement = "CREATE TABLE " + RECEIPTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MERCHANT_NAME + " TEXT, " + COLUMN_MERCHANT_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_UNIT_PRICES + " TEXT, " + COLUMN_ITEM_QUANTITIES + " TEXT, " + COLUMN_VAT + " REAL, " + COLUMN_VATABLE + " REAL, " + COLUMN_DATE + " TEXT, " + COLUMN_SERIAL_NUMBER + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //called when changing versions

    }

    public Boolean addReceipt(ReceiptModel receiptModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MERCHANT_NAME, receiptModel.getMerchantName());
        cv.put(COLUMN_MERCHANT_ADDRESS, receiptModel.getMerchantAddress());
        cv.put(COLUMN_ITEMS, receiptModel.getItems());
        cv.put(COLUMN_UNIT_PRICES, receiptModel.getUnitPrices());
        cv.put(COLUMN_ITEM_QUANTITIES, receiptModel.getItemQuantities());
        cv.put(COLUMN_VAT, receiptModel.getVatPrice());
        cv.put(COLUMN_VATABLE, receiptModel.getVatablePrice());

        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern, Locale.US);
        cv.put(COLUMN_DATE, df.format(receiptModel.getDate()));
        cv.put(COLUMN_SERIAL_NUMBER, receiptModel.getSerialNumber());

        long insert = db.insert(RECEIPTS_TABLE, null,cv);
        if (insert ==-1)
            return false;
        else
            return true;


    }
}
