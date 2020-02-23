package com.example.techchat;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messagestore";

    private static final String TABLE_Message = "messagetable";

    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TIME = "time";
    private static final String KEY_IN_OUT = "inout";

    private static final String TABLE_FRIEDNS = "friendtable";
    private static final String  FIRST_NAME= "firstname";
    private static final String  LAST_NAME= "lastname";
    //private static final String = "email";





    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE " + TABLE_Message + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_EMAIL + " TEXT,"+ KEY_MESSAGE + " TEXT,"
                + KEY_IN_OUT+ " TEXT," +  KEY_TIME+" TEXT )";

        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIEDNS + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"+FIRST_NAME
                +" TEXT,"+LAST_NAME+" TEXT)";
        db.execSQL(CREATE_MESSAGE_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Message);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEDNS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new message
    void addMessage(DatabaseMessage message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, message.getEmail()); //
        values.put(KEY_MESSAGE, message.getMessage()); //
        values.put(KEY_IN_OUT, message.getInOut()); //
        values.put(KEY_TIME, message.getTime()); //

        // Inserting Row
        db.insert(TABLE_Message, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
//    DatabaseMessage getMessage(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_Message, new String[] { KEY_ID,
//                        KEY_EMAIL,KEY_MESSAGE, KEY_IN_OUT,KEY_TIME }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        DatabaseMessage message = new DatabaseMessage(cursor.getString(0),cursor.getString(1),
//                Integer.parseInt(cursor.getString(2)), cursor.getString(3));
//        // return contact
//        return message;
//    }

    // code to get all contacts in a list view


    public void addFriend(Friend message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, message.getEmail()); //
        values.put(FIRST_NAME, message.getFirstName()); //
        values.put(LAST_NAME, message.getLastName()); //

        // Inserting Row
        db.insert(TABLE_FRIEDNS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }













    public List<DatabaseMessage> getAllMessages(String email) {
        List<DatabaseMessage> messageList = new ArrayList<DatabaseMessage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Message +" WHERE "+KEY_EMAIL +" LIKE '"+email+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DatabaseMessage message = new DatabaseMessage();
                message.setId(Integer.parseInt(cursor.getString(0)));
                message.setEmail(cursor.getString(1));
                message.setMessage(cursor.getString(2));
                message.setInOut(Integer.parseInt(cursor.getString(3)));
                message.setTime(cursor.getString(4));
                // Adding contact to list
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return messageList;
    }

    public List<Friend> getAllFriends() {
        List<Friend> messageList = new ArrayList<Friend>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FRIEDNS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Friend message = new Friend();
                message.setEmail(cursor.getString(0));
                message.setFirstName(cursor.getString(1));
                message.setLastName(cursor.getString(2));
                // Adding contact to list
                messageList.add(message);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return messageList;
    }


    public List<String> getAllEmails() {
        List<String> emailList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT "+KEY_EMAIL +" FROM " + TABLE_FRIEDNS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding contact to list
                emailList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return emailList;
    }





    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }

    // Getting contacts Count
    public int getMessageCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Message;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

}
