package com.androidaura.p2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "emp.db";
    public static final String TABLE_NAME = "employee";
    public static final String EMPID = "empno";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");

        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + EMPID + " INTEGER," + FNAME + " TEXT,"
                + LNAME + " TEXT" + ")";
        db.execSQL(CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public void deleteRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);

    }

    // code to add the new contact
    void addEmployee(Employee emp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMPID, emp.getEMPID());
        values.put(FNAME, emp.getFname());
        values.put(LNAME, emp.getLname());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all contacts in a list view
    public ArrayList<Employee> getAllEmployee() {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Employee student = new Employee();
                student.setEMPID(cursor.getString(0));
                student.setFName(cursor.getString(1));
                student.setLName(cursor.getString(2));

                // Adding contact to list
                employeeList.add(student);
            } while (cursor.moveToNext());
        }

        // return contact list
        return employeeList;
    }


    public int getRecordsCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);


        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public void getJsonfromDB() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        JSONArray j = convertCursor2JsonObjects(cursor);

        Log.d("vt","Output "+ j);

        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            cursor.close();
        }


    }

    public JSONArray convertCursor2JsonObjects(Cursor c) {

        JSONArray resultSet = new JSONArray();
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            int totalColumn = c.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (c.getColumnName(i) != null) {
                    try {
                        rowObject.put(c.getColumnName(i),
                                c.getString(i));
                    } catch (Exception e) {
                        Log.d("vt", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            c.moveToNext();
        }

        c.close();
        return resultSet;

    }
}