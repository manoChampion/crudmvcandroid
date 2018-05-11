package com.example.luis.crudtrescamadas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DogDB extends SQLiteOpenHelper {

    private static String TAG = "databaseX";
    private static final String NAME_DB = "dogs.sqlite";
    private static final int VERSION = 1;
    private static DogDB dogDB = null;

    public DogDB(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    public static DogDB getInstance(Context context) {
        if (dogDB == null) {
            dogDB = new DogDB(context);
            return dogDB;
        } else {
            return dogDB;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS dogs (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, sex TEXT, breed TEXT, size TEXT, image blob);";
        Log.d(TAG, "Building table 'dogs'. Please, wait...");
        db.execSQL(sql);
        Log.d(TAG, "Table has been built successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Test", "Upgrade version " + oldVersion + " to " + newVersion + ", destructing old table");
        db.execSQL("DROP TABLE IF EXISTS dogs");
        onCreate(db);
        Log.i("Test", "The upgrade script has been executed");
    }

    public long create(Dog dog) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", dog.name);
            contentValues.put("breed", dog.breed);
            contentValues.put("sex", dog.sex);
            contentValues.put("size", dog.size);
            contentValues.put("image", dog.image);

            if (dog._id == null) {
                return db.insert("dogs", null, contentValues);
            } else {
                contentValues.put("_id", dog._id);
                return db.update("dogs", contentValues, "_id = " + dog._id, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return 0;
    }

    public long delete(Dog dog) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            return db.delete("dogs", "_id = ?", new String[]{String.valueOf(dog._id)});
        } finally {
            db.close();
        }
    }

    public List<Dog> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            return toList(db.rawQuery("SELECT * FROM dogs", null));
        } finally {
            db.close();
        }
    }

    private List<Dog> toList(Cursor cursor) {
        List<Dog> listActivityDogs = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Dog dog = new Dog();

                dog._id = cursor.getLong(cursor.getColumnIndex("_id"));
                dog.name = cursor.getString(cursor.getColumnIndex("name"));
                dog.sex = cursor.getString(cursor.getColumnIndex("sex"));
                dog.breed = cursor.getString(cursor.getColumnIndex("breed"));
                dog.size = cursor.getString(cursor.getColumnIndex("size"));
                dog.image = cursor.getBlob(cursor.getColumnIndex("image"));
                listActivityDogs.add(dog);
            } while (cursor.moveToNext());
        } return listActivityDogs;
    }


}

