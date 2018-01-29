package com.example.ashutosh.mpiricmodule1;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gandhi on 02-Mar-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cartManager";
    private static final String TABLE_CART = "cart";
    private static final String KEY_ID = "prod_id";
    private static final String KEY_NAME = "u_name";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" +")";
        db.execSQL(CREATE_CART_TABLE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    /**
          * All CRUD(Create, Read, Update, Delete) Operations
          */
    void addContact(Cart_POJO cart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cart.getU_name());
        values.put(KEY_ID, cart.getProd_id());
        db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection
    }


    Cart_POJO getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART, new String[] { KEY_ID, KEY_NAME }, KEY_ID + "=?",
        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Cart_POJO cart = new Cart_POJO(Integer.parseInt(cursor.getString(0)),
        cursor.getString(1));
        return cart;
    }
    public List<Card> getAllContacts(String email) {
        List<Card> contactList = new ArrayList<Card>();
        // Select All Query
   //     String selectQuery = "SELECT * FROM " + TABLE_CART +"WHERE u_name = "+email;
        String selectQuery =  "SELECT * FROM cart WHERE TRIM(u_name) = '"+email.trim()+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Card cart = new Card();
                cart.setProd_id(Integer.parseInt(cursor.getString(0)));
                cart.setName(cursor.getString(1));
                // Adding contact to list
                contactList.add(cart);
                } while (cursor.moveToNext());
            }
        cursor.close();
        // return contact list
        return contactList;
        }

            // Updating single contact
            public int updateContact(Cart_POJO cart)
            {
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_NAME, cart.getU_name());
                values.put(KEY_ID, cart.getProd_id());
                // updating row
                return db.update(TABLE_CART, values, KEY_ID + " = ?", new String[] { String.valueOf(cart.getProd_id()) });
            }
    // Deleting single contact
        public void deleteContact(Cart_POJO cart) {
        SQLiteDatabase db = this.getWritableDatabase();
    //  db.delete(TABLE_NAME, " title = 'haiyang'", null);
        db.delete(TABLE_CART, KEY_ID + " = ?", new String[] { String.valueOf(cart.getProd_id()) });
      //  db.delete(TABLE_CART, KEY_ID + " = ", cart.getProd_id()) ;
        db.close();
        }

        public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
        }

    public List<Card> getMyData(String email) {
        List<Card> contactList = new ArrayList<Card>();
        final String TABLE_NAME = "name of table";

        String selectQuery =  "SELECT * FROM cart WHERE TRIM(u_name) = '"+email.trim()+"'";
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;

        Log.e("COUNT ", String.valueOf(cursor.getCount()));
      //  if (cursor!=null && cursor.getCount()>0) {
        if (cursor.moveToFirst()) {
            do {

                Card cart = new Card();
                cart.setProd_id(Integer.parseInt(cursor.getString(0)));
                cart.setName(cursor.getString(1));
                // Adding contact to list
                contactList.add(cart);

                // get the data into array, or class variable
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    public void deleteMyData(int pid)
    {
        String Que="DELETE FROM cart WHERE TRIM(prod_id) = '"+pid+"'";

        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(Que, null);
        Log.e("After DEL", String.valueOf(cursor.getCount()));
        cursor.close();
    }
}
