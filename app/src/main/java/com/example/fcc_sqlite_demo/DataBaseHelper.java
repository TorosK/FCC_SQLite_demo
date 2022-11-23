package com.example.fcc_sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "NAME";
    public static final String COLUMN_CUSTOMER_BIRTHYEAR = "BIRTHYEAR";
    public static final String COLUMN_CUSTOMER_ACTIVE = "ACTIVE";


    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_BIRTHYEAR + " INT, " + COLUMN_CUSTOMER_ACTIVE + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CUSTOMER_ID, customerModel.getId());
        contentValues.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        contentValues.put(COLUMN_CUSTOMER_BIRTHYEAR, customerModel.getBirthYear());

        // No need because ID is auto-increment in DB.
        // contentValues.put(COLUMN_CUSTOMER_ACTIVE, customerModel.isActive());

        long insert = sqLiteDatabase.insert(CUSTOMER_TABLE, null, contentValues);
        System.out.println(insert);

        return true;
    }

    public List<CustomerModel> getAll() {
        List<CustomerModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);

        if(cursor.moveToFirst()) {
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerBirthYear = cursor.getInt(2);
                boolean customerIsActive = cursor.getInt(3) == 1 ? true: false;

                CustomerModel customerModel = new CustomerModel(customerID, customerName, customerBirthYear, customerIsActive);
                returnList.add(customerModel);

            } while (cursor.moveToNext());

        } else {
            System.out.println("fail");
        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;

    }
}
