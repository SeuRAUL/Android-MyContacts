package com.example.seuraul.mycontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seuraul on 13/10/16.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyContactsDb";

    public static final String TABLE_NAME = "Contacts";
    public static final String ID_FIELD = "id";
    public static final String FIRST_NAME_FIELD = "firstname";
    public static final String LAST_NAME_FIELD = "lastname";
    public static final String PHONE_FIELD = "phone";
    public static final String ADDRESS_FIELD = "address";
    public static final String CITY_FIELD = "city";
    public static final String STATE_FIELD = "state";
    public static final String ZIPCODE_FIELD = "zipcode";

    public DatabaseManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT," + FIRST_NAME_FIELD + " TEXT," + LAST_NAME_FIELD + " TEXT," + PHONE_FIELD + " TEXT," + ADDRESS_FIELD + " TEXT," + CITY_FIELD + " TEXT," + STATE_FIELD + " TEXT," + ZIPCODE_FIELD + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME_FIELD, contact.getFirstName());
        values.put(LAST_NAME_FIELD, contact.getLastName());
        values.put(PHONE_FIELD, contact.getPhone());
        values.put(ADDRESS_FIELD, contact.getAddress());
        values.put(CITY_FIELD, contact.getCity());
        values.put(STATE_FIELD, contact.getState());
        values.put(ZIPCODE_FIELD, contact.getZipcode());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Contact getContact (int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{ID_FIELD, FIRST_NAME_FIELD, LAST_NAME_FIELD, PHONE_FIELD, ADDRESS_FIELD, CITY_FIELD, STATE_FIELD, ZIPCODE_FIELD}, ID_FIELD + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor==null) {
            return null;
        }
        cursor.moveToNext();
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        cursor.close();
        return contact;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, ID_FIELD + "=?",new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_NAME_FIELD, contact.getFirstName());
        values.put(LAST_NAME_FIELD, contact.getLastName());
        values.put(PHONE_FIELD, contact.getPhone());
        values.put(ADDRESS_FIELD, contact.getAddress());
        values.put(CITY_FIELD, contact.getCity());
        values.put(STATE_FIELD, contact.getState());
        values.put(ZIPCODE_FIELD, contact.getZipcode());
        int result = db.update(TABLE_NAME, values, ID_FIELD + "=?", new String[]{String.valueOf(contact.getId())});
        db.close();
        return result;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor==null) return null;
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                contacts.add(contact);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return contacts;
    }
}
