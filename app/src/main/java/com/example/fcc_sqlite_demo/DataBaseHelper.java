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

    public static final String REMINDER_TABLE = "REMINDER_TABLE";
    public static final String REMINDER_COLUMN_ID = "ID";
    public static final String REMINDER_COLUMN_TITLE = "TITLE";
    public static final String REMINDER_COLUMN_DATE = "DATE";
    public static final String REMINDER_COLUMN_TIME = "TIME";
    public static final String REMINDER_COLUMN_LEVEL = "LEVEL";
    public static final String REMINDER_COLUMN_SCANNED_CODE = "SCANNED_CODE";
    public static final String REMINDER_COLUMN_IS_IMPORTANT = "IS_IMPORTANT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "reminder.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + REMINDER_TABLE + " (" +
                REMINDER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                REMINDER_COLUMN_TITLE + " TEXT, " +
                REMINDER_COLUMN_DATE + " TEXT, " +
                REMINDER_COLUMN_TIME + " TEXT, " +
                REMINDER_COLUMN_LEVEL + " INT, " +
                REMINDER_COLUMN_SCANNED_CODE + " TEXT, " +
                REMINDER_COLUMN_IS_IMPORTANT + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(ReminderModel reminderModel) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        // No need because ID is auto-increment in DB.
        // contentValues.put(COLUMN_CUSTOMER_ID, customerModel.getId());

        contentValues.put(REMINDER_COLUMN_TITLE, reminderModel.getTitle());
        contentValues.put(REMINDER_COLUMN_DATE, reminderModel.getDate());
        contentValues.put(REMINDER_COLUMN_TIME, reminderModel.getTime());
        contentValues.put(REMINDER_COLUMN_LEVEL, reminderModel.getLevel());
        contentValues.put(REMINDER_COLUMN_SCANNED_CODE, reminderModel.getScannedCode());
        contentValues.put(REMINDER_COLUMN_IS_IMPORTANT, reminderModel.isImportant());

        long insert = sqLiteDatabase.insert(REMINDER_TABLE, null, contentValues);
        System.out.println("insert: " + insert);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<ReminderModel> getAll() {
        List<ReminderModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + REMINDER_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);
        if(cursor.moveToFirst()) {
            do {
                int reminder_ID = cursor.getInt(0);
                String reminder_title = cursor.getString(1);
                String reminder_date = cursor.getString(2);
                String reminder_time = cursor.getString(3);
                int reminder_level = cursor.getInt(4);
                String reminder_scanned_code = cursor.getString(5);
                boolean reminder_is_important = cursor.getInt(6) == 1 ? true: false;
                ReminderModel reminderModel = new ReminderModel(reminder_ID, reminder_title, reminder_date, reminder_time, reminder_level, reminder_scanned_code, reminder_is_important);
                returnList.add(reminderModel);
            } while (cursor.moveToNext());
        } else {
            System.out.println("fail");
        }
        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }

    public boolean deleteOne(ReminderModel reminderModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REMINDER_TABLE + " WHERE " + REMINDER_COLUMN_ID + " = " + reminderModel.getId();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
        // return false;
    }

    // Update method
    public void updateReminder(int id, String title, String date, String time, int level, String scanned_code, boolean important) {

        // calling a method to get writable database.
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(REMINDER_COLUMN_ID, id);
        values.put(REMINDER_COLUMN_TITLE, title);
        values.put(REMINDER_COLUMN_DATE, date);
        values.put(REMINDER_COLUMN_TIME, time);
        values.put(REMINDER_COLUMN_LEVEL, level);
        values.put(REMINDER_COLUMN_SCANNED_CODE, scanned_code);
        values.put(REMINDER_COLUMN_IS_IMPORTANT, important);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with title of our reminder which is stored in original title variable. // But why compare?
        sqLiteDatabase.update(REMINDER_TABLE, values, "ID=?", new String[]{id + ""});
        sqLiteDatabase.close();
    }
}