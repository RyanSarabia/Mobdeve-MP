package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class Database extends SQLiteOpenHelper{

    public static final String RECEIPTS_TABLE = "RECEIPTS_TABLE";
    public static final String TAGS_TABLE = "TAGS_TABLE";
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
    public static final String COLUMN_TAGS = "COLUMN_TAGS";
    public static final String COLUMN_TAG_NAME = "COLUMN_TAGS_NAMES";

    public Database(@Nullable Context context) {
        super(context, "receipts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //creates new database

        String createTableStatement = "CREATE TABLE " + RECEIPTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MERCHANT_NAME + " TEXT, " + COLUMN_MERCHANT_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_UNIT_PRICES + " TEXT, " + COLUMN_ITEM_QUANTITIES + " TEXT, " + COLUMN_VAT + " REAL, " + COLUMN_VATABLE + " REAL, " + COLUMN_DATE + " TEXT, " + COLUMN_SERIAL_NUMBER + " TEXT, " + COLUMN_TAGS +" TEXT)";
        String createTableStatement2 = "CREATE TABLE " + TAGS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TAG_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //called when changing versions

    }

    public Boolean addReceipt(ReceiptModel receiptModel){
        //For adding new receipt into database

        SQLiteDatabase db = this.getWritableDatabase();

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + RECEIPTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MERCHANT_NAME + " TEXT, " + COLUMN_MERCHANT_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_UNIT_PRICES + " TEXT, " + COLUMN_ITEM_QUANTITIES + " TEXT, " + COLUMN_VAT + " REAL, " + COLUMN_VATABLE + " REAL, " + COLUMN_DATE + " TEXT, " + COLUMN_SERIAL_NUMBER + " TEXT, " + COLUMN_TAGS +" TEXT)";
        db.execSQL(createTableStatement);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MERCHANT_NAME, receiptModel.getMerchantName());
        cv.put(COLUMN_MERCHANT_ADDRESS, receiptModel.getMerchantAddress());
        cv.put(COLUMN_ITEMS, receiptModel.getItems());
        cv.put(COLUMN_UNIT_PRICES, receiptModel.getUnitPrices());
        cv.put(COLUMN_ITEM_QUANTITIES, receiptModel.getItemQuantities());
        cv.put(COLUMN_VAT, receiptModel.getVatPrice());
        cv.put(COLUMN_VATABLE, receiptModel.getVatablePrice());
        cv.put(COLUMN_TAGS, receiptModel.getTagAsString());

        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern, Locale.US);
        cv.put(COLUMN_DATE, df.format(receiptModel.getDate()));
        cv.put(COLUMN_SERIAL_NUMBER, receiptModel.getSerialNumber());

        long insert = db.insert(RECEIPTS_TABLE, null,cv);
        if (insert ==-1){
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }

    }

    public Boolean addTag(String tagName){
        //Call function along with updateReceiptTag

        SQLiteDatabase db = this.getWritableDatabase();

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TAG_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TAG_NAME, tagName);
        long insert = db.insert(TAGS_TABLE, null,cv);
        if (insert ==-1){
            db.close();
            return false;
        }
        else{
            db.close();
            return true;
        }

    }

    public void updateTagName(String newTagName, String oldTagName){
        SQLiteDatabase db = this.getWritableDatabase();

        String updateString = "UPDATE " + TAGS_TABLE + " SET " + COLUMN_TAG_NAME + " = '" + newTagName+ "'" + " WHERE " + COLUMN_TAG_NAME + " = '" + oldTagName + "'";
        db.execSQL(updateString);
        db.close();
    }

    public void updateReceiptsTagName(String newTagName, String oldTagName){
        SQLiteDatabase db = this.getWritableDatabase();

        String updateString = "UPDATE " + RECEIPTS_TABLE + " SET " + COLUMN_TAGS + " = '" + newTagName+ "'" + " WHERE " + COLUMN_TAGS + " = '" + oldTagName + "'";
        db.execSQL(updateString);
        db.close();
    }

    public void addReceiptTag(String updateSerialNumber, String tagName){
        //Call function along with addTag

        SQLiteDatabase db = this.getWritableDatabase();

        String updateString = "UPDATE " + RECEIPTS_TABLE + " SET " + COLUMN_TAGS + " = '" + tagName+ "'" +  " WHERE " + COLUMN_SERIAL_NUMBER + " = " + updateSerialNumber;

        db.execSQL(updateString);
        db.close();

    }

    public void deleteTag(String tagName){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryStringTag = "DELETE FROM " + TAGS_TABLE + " WHERE " + COLUMN_TAG_NAME + " = '" + tagName +"'";
        Cursor cursor = db.rawQuery(queryStringTag, null);
        cursor.moveToFirst();
        cursor.close();
        String emptyString = "''";
        String queryStringReceipt = "UPDATE " + RECEIPTS_TABLE + " SET " + COLUMN_TAGS +  " = " + emptyString + " WHERE " + COLUMN_TAGS + " = '" + tagName + "'";
        db.execSQL(queryStringReceipt);
        db.close();

    }

    public Boolean addDummyReceipt(){
        //For adding new receipt into database

        SQLiteDatabase db = this.getWritableDatabase();

        String createTableStatementTags = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TAG_NAME + " TEXT)";

        db.execSQL(createTableStatementTags);

        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + RECEIPTS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_MERCHANT_NAME + " TEXT, " + COLUMN_MERCHANT_ADDRESS + " TEXT, " + COLUMN_ITEMS + " TEXT, " + COLUMN_UNIT_PRICES + " TEXT, " + COLUMN_ITEM_QUANTITIES + " TEXT, " + COLUMN_VAT + " REAL, " + COLUMN_VATABLE + " REAL, " + COLUMN_DATE + " TEXT, " + COLUMN_SERIAL_NUMBER + " TEXT, " + COLUMN_TAGS +" TEXT)";
        String createTableStatementTags = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TAG_NAME + " TEXT)";
        db.execSQL(createTableStatement);
        db.execSQL(createTableStatementTags);
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();

        cv1.put(COLUMN_MERCHANT_NAME, "Rustan's");
        cv1.put(COLUMN_MERCHANT_ADDRESS, "KungSaanMan, 2nd street, Las Vegas, Toronto");
        cv1.put(COLUMN_ITEMS, "Catether,Breastplate,Paintball");
        cv1.put(COLUMN_UNIT_PRICES, "100.5,20.2,10000");
        cv1.put(COLUMN_ITEM_QUANTITIES, "1,1,200");
        cv1.put(COLUMN_VAT, "10.5");
        cv1.put(COLUMN_VATABLE, "90");
        cv1.put(COLUMN_TAGS, "");

        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern, Locale.US);
        cv1.put(COLUMN_DATE, "2020-09-23");
        cv1.put(COLUMN_SERIAL_NUMBER, "12345678911234");

        db.insert(RECEIPTS_TABLE, null,cv1);

        cv2.put(COLUMN_MERCHANT_NAME, "Shakey's");
        cv2.put(COLUMN_MERCHANT_ADDRESS, "Here, 2nd street, Los Angeles, Manila");
        cv2.put(COLUMN_ITEMS, "Klean Kanteen,Golden Rings");
        cv2.put(COLUMN_UNIT_PRICES, "1500,500000");
        cv2.put(COLUMN_ITEM_QUANTITIES, "1,5");
        cv2.put(COLUMN_VAT, "150");
        cv2.put(COLUMN_VATABLE, "1350");
        cv2.put(COLUMN_TAGS, "");

        cv2.put(COLUMN_DATE, "2021-03-15");
        cv2.put(COLUMN_SERIAL_NUMBER, "98765432112345");

        long insert = db.insert(RECEIPTS_TABLE, null,cv2);
        if (insert ==-1){
            db.close();
            return false;
        }

        else{
            db.close();
            return true;
        }

    }

    public List<ReceiptModel> getAll(){
        //Gets all receipts based on when it was added
        List<ReceiptModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int receiptID = cursor.getInt(0);
                String merchantName = cursor.getString(1);
                String merchantAddress = cursor.getString(2);
                String items = cursor.getString(3);
                String unitPrices = cursor.getString(4);
                String itemQuantities = cursor.getString(5);
                Float vatPrice = cursor.getFloat(6);
                Float vatablePrice = cursor.getFloat(7);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date();
                try{
                     date = dateFormat.parse(cursor.getString(8));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                String serialNumber = cursor.getString(9);

                ReceiptModel newReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);
                String tagString = cursor.getString(10);

                if (!tagString.equals(""))
                    newReceipt.setTag(new Tag(tagString));

                returnList.add(newReceipt);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<Tag> getAllTags(){
        List<Tag> returnTagList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String createTableStatement = "CREATE TABLE IF NOT EXISTS " + TAGS_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TAG_NAME + " TEXT)";
        db.execSQL(createTableStatement);

        String queryString = "SELECT * FROM " + TAGS_TABLE;


        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                String tagName = cursor.getString(1);
                Tag newTag = new Tag(tagName);

                returnTagList.add(newTag);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnTagList;
    }

    public List<ReceiptModel> getAllByDateDescending(){
        //Gets all receipts based on when it was added
        List<ReceiptModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE + " ORDER BY COLUMN_DATE DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int receiptID = cursor.getInt(0);
                String merchantName = cursor.getString(1);
                String merchantAddress = cursor.getString(2);
                String items = cursor.getString(3);
                String unitPrices = cursor.getString(4);
                String itemQuantities = cursor.getString(5);
                Float vatPrice = cursor.getFloat(6);
                Float vatablePrice = cursor.getFloat(7);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date();
                try{
                    date = dateFormat.parse(cursor.getString(8));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                String serialNumber = cursor.getString(9);

                ReceiptModel newReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);
                String tagString = cursor.getString(10);

                if (!tagString.equals(""))
                    newReceipt.setTag(new Tag(tagString));

                returnList.add(newReceipt);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ReceiptModel> getAllByDateAscending(){
        //Gets all receipts based on when it was added
        List<ReceiptModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE + " ORDER BY COLUMN_DATE ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int receiptID = cursor.getInt(0);
                String merchantName = cursor.getString(1);
                String merchantAddress = cursor.getString(2);
                String items = cursor.getString(3);
                String unitPrices = cursor.getString(4);
                String itemQuantities = cursor.getString(5);
                Float vatPrice = cursor.getFloat(6);
                Float vatablePrice = cursor.getFloat(7);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date();
                try{
                    date = dateFormat.parse(cursor.getString(8));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                String serialNumber = cursor.getString(9);

                ReceiptModel newReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);
                String tagString = cursor.getString(10);

                if (!tagString.equals(""))
                    newReceipt.setTag(new Tag(tagString));
                returnList.add(newReceipt);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ReceiptModel> getAllByMerchantAscending(){
        //Gets all receipts based on when it was added
        List<ReceiptModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE + " ORDER BY COLUMN_MERCHANT_NAME ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int receiptID = cursor.getInt(0);
                String merchantName = cursor.getString(1);
                String merchantAddress = cursor.getString(2);
                String items = cursor.getString(3);
                String unitPrices = cursor.getString(4);
                String itemQuantities = cursor.getString(5);
                Float vatPrice = cursor.getFloat(6);
                Float vatablePrice = cursor.getFloat(7);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date();
                try{
                    date = dateFormat.parse(cursor.getString(8));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                String serialNumber = cursor.getString(9);

                ReceiptModel newReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);
                String tagString = cursor.getString(10);

                if (!tagString.equals(""))
                    newReceipt.setTag(new Tag(tagString));
                returnList.add(newReceipt);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<ReceiptModel> getAllByMerchantDescending(){
        //Gets all receipts based on when it was added
        List<ReceiptModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE + " ORDER BY COLUMN_MERCHANT_NAME DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int receiptID = cursor.getInt(0);
                String merchantName = cursor.getString(1);
                String merchantAddress = cursor.getString(2);
                String items = cursor.getString(3);
                String unitPrices = cursor.getString(4);
                String itemQuantities = cursor.getString(5);
                Float vatPrice = cursor.getFloat(6);
                Float vatablePrice = cursor.getFloat(7);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date date = new Date();
                try{
                    date = dateFormat.parse(cursor.getString(8));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                String serialNumber = cursor.getString(9);

                ReceiptModel newReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);
                String tagString = cursor.getString(10);

                if (!tagString.equals(""))
                    newReceipt.setTag(new Tag(tagString));
                returnList.add(newReceipt);
            }while(cursor.moveToNext());
        }
        else{
            //Nothing in db, do not add anything to returnList
        }
        cursor.close();
        db.close();
        return returnList;
    }


    public ReceiptModel getOne(String getSerialNumber){

        ReceiptModel returnReceipt;

        String queryString = "SELECT * FROM " + RECEIPTS_TABLE + " WHERE " + COLUMN_SERIAL_NUMBER + " = " + getSerialNumber;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        cursor.moveToFirst();

        int receiptID = cursor.getInt(  0);
        String merchantName = cursor.getString(1);
        String merchantAddress = cursor.getString(2);
        String items = cursor.getString(3);
        String unitPrices = cursor.getString(4);
        String itemQuantities = cursor.getString(5);
        Float vatPrice = cursor.getFloat(6);
        Float vatablePrice = cursor.getFloat(7);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = new Date();
        try{
            date = dateFormat.parse(cursor.getString(8));
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        String serialNumber = cursor.getString(9);

        returnReceipt = new ReceiptModel(receiptID, merchantName, merchantAddress, items, unitPrices, itemQuantities, vatPrice, vatablePrice, date, serialNumber);

        String tagString = cursor.getString(10);

        if (!tagString.equals(""))
            returnReceipt.setTag(new Tag(tagString));

        db.close();
        cursor.close();
        db.close();
        return returnReceipt;
    }


    public boolean deleteOne (String deleteSerialNumber){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + RECEIPTS_TABLE + " WHERE " + COLUMN_SERIAL_NUMBER + " = " + deleteSerialNumber;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            db.close();
            cursor.close();
            db.close();
            return true;
        }
        else{
            db.close();
            cursor.close();
            db.close();
            return false;
        }
    }



    public boolean deleteAll(){

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DROP TABLE " + RECEIPTS_TABLE;
        String queryStringTags = "DROP TABLE " + TAGS_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);
        cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            db.close();
            cursor.close();
            db.close();
            return true;
        }
        else{
            db.close();
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean deleteAllTags(){

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DROP TABLE " + TAGS_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){

            cursor.close();
            db.close();
            return true;
        }
        else{

            cursor.close();
            db.close();
            return false;
        }
    }

    private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.mobdeve.loquintek.sarabiar.mobdeve_mp" +"/databases/receipts.db";
        String backupDBPath = "receipts.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }





}
