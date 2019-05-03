package de.hs_kl.thomasjager.dailyroutine.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Routine.db";


    public static final String TABLE_NAME = "routine";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_COUNTER = "counter";
    public static final String COLUMN_NAME_DATE = "date";

    public static final String SQL_CREATE_ROUTINE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_DESCRIPTION + " TEXT," +
                    COLUMN_NAME_COUNTER + " INTEGER," +
                    COLUMN_NAME_DATE + " TEXT)";


    public static final String SQL_DELETE_ROUTINE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;




    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROUTINE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ROUTINE);
        onCreate(db);
    }


    public long addRoutine(String title, String description, int counter, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE, title);
        contentValues.put(COLUMN_NAME_DESCRIPTION, description);
        contentValues.put(COLUMN_NAME_COUNTER,counter);
        contentValues.put(COLUMN_NAME_DATE,date);

        long newRowID = db.insert(TABLE_NAME,null,contentValues);
        return newRowID;
    }




    public void deleteRoutine(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE "+_ID+"='"+id+"'");
        db.close();
    }



    //

    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+ TABLE_NAME;
        db.execSQL(clearDBQuery);
    }






    public Cursor selectAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        cursor.moveToFirst();
        return cursor;
    }




    public int getItemId(String name){
        int id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + _ID + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME_TITLE + " ='"+name+"'";


        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        }

        return id;
    }


    public int getCounter(int id){
        int counter = -1;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_NAME_COUNTER + " FROM " + TABLE_NAME +
                " WHERE " + _ID + " ='"+id+"'";


        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext()){
            counter = cursor.getInt(cursor.getColumnIndexOrThrow("counter"));
        }



        return counter;
    }


    public String getDate(int id){

        String datum = "";

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COLUMN_NAME_DATE + " FROM " + TABLE_NAME +
                " WHERE " + _ID + " ='"+id+"'";


        Cursor cursor = db.rawQuery(query,null);

        while(cursor.moveToNext()){
            datum = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        }



        return datum;



    }


    public int updateCounter(int id){

        int counter = getCounter(id);
        counter += 1;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_COUNTER,counter);

        String selection = _ID + " = ?";
        String[] selectionArgs = {Integer.toString(id)};

        int updateCounterErfolgreich = db.update(TABLE_NAME,values,selection,selectionArgs);

        return updateCounterErfolgreich;

    }

    public int updateRoutine(int id, String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TITLE,name);
        contentValues.put(COLUMN_NAME_DESCRIPTION,description);

        String selection = _ID + " =?";
        String[] selectionArgs = {Integer.toString(id)};

        int updateErfolgreich = db.update(TABLE_NAME,contentValues,selection,selectionArgs);

        return updateErfolgreich;

    }

    public int updateDate(int id, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_DATE,date);

        String selection = _ID + " =?";
        String[] selectionArgs = {Integer.toString(id)};

        int updateErfolgreich = db.update(TABLE_NAME,contentValues,selection,selectionArgs);


        return updateErfolgreich;
    }

    public boolean checkIfAlreadyInDB(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COLUMN_NAME_TITLE + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NAME_TITLE + " ='"+name+"'";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }



}
